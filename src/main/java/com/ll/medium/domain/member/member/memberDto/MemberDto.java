package com.ll.medium.domain.member.member.memberDto;


import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private long memberId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String memberName;
    private String memberPassword;
    private String memberEmail;
    private List<String> memberAuthorities;
    private List<Article> memberArticleList;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.createDate = member.getCreateDate();
        this.modifiedDate = member.getModifyDate();
        this.memberName = member.getMemberName();
        this.memberPassword = member.getMemberPassword();
        this.memberEmail = member.getMemberEmail();
        this.memberAuthorities = member.getMemberAuthorities();
        this.memberArticleList = member.getArticleList();
    }
}
