package com.ll.medium.domain.aticle.article.DTO;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String title;
    private String body;
    private Member author;
    private boolean published;

    public ArticleDto(Article article){
        this.title = article.getTitle();
        this.body = article.getBody();
        this.author = article.getAuthor();
        this.published = article.isPublished();
    }
}
