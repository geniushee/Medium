package com.ll.medium.domain.member.member.entity;


import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity(name = "Member")
@Getter
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    @NotNull
    @Column(unique = true)
    private String memberName;

    private String memberPassword;

    @Email
    private String memberEmail;

    @ElementCollection(fetch = FetchType.EAGER) // Todo eager말고 다른 방안은 없을까?? 간단해서 eager를 쓰긴했으나;
    @CollectionTable(name = "member_authorities", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "authority")
    private List<String> memberAuthorities;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Article> articleList;

    private boolean isPaid;

    public static Member dtoToEntity(MemberDto dto){
        initMemberAuthorities(dto);
        return Member.builder()
                .id(dto.getMemberId())
                .createDate(dto.getCreateDate())
                .modifyDate(dto.getModifiedDate())
                .memberName(dto.getMemberName())
                .memberPassword(dto.getMemberPassword())
                .memberEmail(dto.getMemberEmail())
                .memberAuthorities(dto.getMemberAuthorities())
                .build();
    }

    private static void initMemberAuthorities(MemberDto memberDto) {
        List<String> authorities = new ArrayList<>();

        if (memberDto.getMemberName().contains("admin")){
            authorities.add("ROLE_ADMIN");
        }
        if (memberDto.isPaid()){
            authorities.add("ROLE_PAID");
        }
        authorities.add("ROLE_MEMBER");


        memberDto.setMemberAuthorities(authorities);

    }
}
