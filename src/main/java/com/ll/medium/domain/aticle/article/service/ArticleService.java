package com.ll.medium.domain.aticle.article.service;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.repository.ArticleRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
