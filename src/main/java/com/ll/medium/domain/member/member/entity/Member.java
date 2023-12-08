package com.ll.medium.domain.member.member.entity;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
        if (memberDto.getMemberName().contains("admin")){
            memberDto.getMemberAuthorities().add("ROLE_ADMIN");
        }
        memberDto.getMemberAuthorities().add("ROLE_MEMBER");

    }
}
