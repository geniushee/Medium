package com.ll.medium.domain.aticle.article.entity;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class Article extends BaseEntity {
    private String title;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member author;
    private boolean published;

}
