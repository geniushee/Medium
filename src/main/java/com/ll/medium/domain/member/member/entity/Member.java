package com.ll.medium.domain.member.member.entity;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    private String memberName;

    private String memberPassword;

    @Email
    private String memberEmail;

    @ElementCollection
    @CollectionTable(name = "member_authorities", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "authority")
    private List<String> memberAuthorities;

    public static Member dtoToEntity(MemberDto entity){
        initMemberAuthorities(entity);
        return Member.builder()
                .memberName(entity.getMemberName())
                .memberPassword(entity.getMemberPassword())
                .memberEmail(entity.getMemberEmail())
                .memberAuthorities(entity.getMemberAuthorities())
                .build();
    }

    private static void initMemberAuthorities(MemberDto memberDto) {
        List<String> authorities = new ArrayList<>();

        if (memberDto.getMemberName().contains("admin")){
            authorities.add("ROLE_ADMIN");
        }
        authorities.add("ROLE_MEMBER");

        memberDto.setMemberAuthorities(authorities);

    }
}
