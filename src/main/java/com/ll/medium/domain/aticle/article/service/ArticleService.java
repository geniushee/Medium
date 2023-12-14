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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    public void writeArticle(ArticleDto dto, Member member) {
        Article article = Article.builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .author(member)
                .published(dto.isPublished())
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
}
