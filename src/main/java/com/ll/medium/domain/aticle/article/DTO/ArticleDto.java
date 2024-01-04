package com.ll.medium.domain.aticle.article.DTO;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String title;
    private String body;
    private Member author;
    private boolean published;
    private boolean isPaid;
    private long hit;

    public ArticleDto(Article article){
        this.id = article.getId();
        this.createDate = article.getCreateDate();
        this.modifiedDate = article.getModifyDate();
        this.title = article.getTitle();
        this.body = article.getBody();
        this.author = article.getAuthor();
        this.published = article.isPublished();
        this.isPaid = article.isPaid();
        this.hit = article.getHit();
    }

}
