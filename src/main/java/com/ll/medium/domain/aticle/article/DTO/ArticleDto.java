package com.ll.medium.domain.aticle.article.DTO;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.entity.Member;
import lombok.Data;

@Data
public class ArticleDto {
    private String title;
    private String body;
    private Member author;
    private boolean published;

    public Article toEntity(Article entity){
        return Article.builder()
                .title(entity.getTitle())
                .body(entity.getBody())
                .author(entity.getAuthor())
                .published(entity.isPublished())
                .build();
    }
}
