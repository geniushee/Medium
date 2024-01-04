package com.ll.medium.domain.aticle.article.service;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.repository.ArticleRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.rsdata.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    @Transactional
    public void writeArticle(ArticleDto dto, Member member) {
        // todo 아래의 코드를 변경하지 않도록 개선, 새로운 항목이 추가 될 때마다 Builder를 추적함;;
        Article article = Article.builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .author(member)
                .published(dto.isPublished())
                .isPaid(dto.isPaid())
                .build();
        articleRepository.save(article);
    }

    public Page<Article> findAllByPublished(int page) {
        //pagination
        //page -1, view는 1부터 시작하지만 Page는 0부터 시작
        page--;
        int pagesize = 10;
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));

        return articleRepository.findByPublished(true, pageable);
    }

    public ArticleDto findById(long id) {
        Optional<Article> opArticle = articleRepository.findById(id);
        return new ArticleDto(opArticle
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다.")));
    }

    @Transactional
    public ArticleDto modifyArticle(long id, ArticleDto dto) {
        Optional<Article> opArticle = articleRepository.findById(id);
        if (opArticle.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 글입니다.");
        }
        Article article = opArticle.get();
        article.setTitle(dto.getTitle());
        article.setBody(dto.getBody());
        article.setPublished(dto.isPublished());
        Article result = articleRepository.save(article);
        return new ArticleDto(result);
    }

    @Transactional
    public void deleteArticle(long id) {
        Optional<Article> opArticle = articleRepository.findById(id);
        if (opArticle.isEmpty()){
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        articleRepository.delete(opArticle.get());
    }

    public Page<Article> findAllByPageable(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> findAllByAuthor(Member member, int page) {
        // pagination
        int pageSize = 10;
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, pageSize,Sort.by(sorts));


        return articleRepository.findByAuthor(member, pageable);

    }

    public RsData<ArticleDto> showArticleDetails(long id, Member member) {
        // 로그인 여부에 따른 에러 처리
        boolean memberPaid;
        if (member == null){
            memberPaid = false;
        } else{
            memberPaid = member.isPaid();
        }

        ArticleDto dto = findById(id);
        // RsData
        String msg = "";
        String error = "";

        // 작성자 - 본인 확인
        if (dto.getAuthor() == member){
            return new RsData<ArticleDto>(dto);
        }

        // published 여부 처리
        if (dto.isPublished()){
            // 유료 컨텐츠 처리
            if (!memberPaid && dto.isPaid()){
                // 글이 유료, 무료 멤버인 경우 body 변경(미로그인 포함)
                dto.setBody("이 글은 유료멤버십전용입니다.");
            }
        }else{
            dto = null;
            msg = "비공개 글입니다.";
            error = "비공개";
        }
        return new RsData<>(dto,msg,error);
    }

    public ArrayList<Article> showMain() {
        return articleRepository.findTop30ByPublishedTrueOrderByCreateDateDesc();
    }

    @Transactional
    public void increaseHit(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        article.increaseHit();
        articleRepository.save(article);
    }

    public boolean canModify(long id, Member member) {
        return getAuthor(id).equals(member);
    }

    public Member getAuthor(long id){
        Article article = articleRepository.findById(id).orElseThrow(RuntimeException::new);
        return article.getAuthor();
    }
}
