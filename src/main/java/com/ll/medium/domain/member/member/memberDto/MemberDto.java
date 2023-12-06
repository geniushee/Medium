package com.ll.medium.domain.member.member.memberDto;

import com.ll.medium.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private long memberId;
    private String memberName;
    private String memberPassword;
    private String memberEmail;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.memberPassword = member.getMemberPassword();
        this.memberEmail = member.getMemberEmail();
    }
}
