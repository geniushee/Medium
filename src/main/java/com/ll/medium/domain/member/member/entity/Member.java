package com.ll.medium.domain.member.member.entity;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    private String memberName;

    private String memberPassword;

    @Email
    private String memberEmail;

    public static Member dtoToEntity(MemberDto entity){
        return Member.builder()
                .memberName(entity.getMemberName())
                .memberPassword(entity.getMemberPassword())
                .memberEmail(entity.getMemberEmail())
                .build();
    }

}
