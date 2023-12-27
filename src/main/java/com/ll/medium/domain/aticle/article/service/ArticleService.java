package com.ll.medium.domain.aticle.article.service;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.repository.ArticleRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    public List<Article> findAllByPublished() {
        return articleRepository.findByPublished(true);
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

    public Page<Article> findAllByAuthor(Member member, Pageable pageable) {
        return articleRepository.findByAuthor(member, pageable);

    }

    public ArticleDto showArticleDetails(long id, Member member) {
        ArticleDto dto =  findById(id);
        boolean memberPaid = member.isPaid();
        if (!memberPaid && dto.isPaid()){
            // 글이 유료, 무료 멤버인 경우 body 변경
            dto.setBody("이 글은 유료멤버십전용입니다.");
        }
        return dto;
    }
}
