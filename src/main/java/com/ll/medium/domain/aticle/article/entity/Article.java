package com.ll.medium.domain.aticle.article.entity;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Article extends BaseEntity {
    private String title;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;
    private boolean published;
    private boolean isPaid;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private long hit;

    public static Article dtoToEntity(ArticleDto dto){
        return Article.builder()
                .title(dto.getTitle())
                .body(dto.getBody())
                .author(dto.getAuthor())
                .published(dto.isPublished())
                .isPaid(dto.isPaid())
                .hit(dto.getHit())
                .build();
    }

    public Article increaseHit(){
        this.hit++;
        return this;
    }
}
