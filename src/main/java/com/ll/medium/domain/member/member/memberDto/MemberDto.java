package com.ll.medium.domain.member.member.memberDto;

import com.ll.medium.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private long memberId;
    private String memberName;
    private String memberPassword;
    private String memberEmail;
    private List<SimpleGrantedAuthority> memberAuthorities;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.memberPassword = member.getMemberPassword();
        this.memberEmail = member.getMemberEmail();
        this.memberAuthorities = member.getMemberAuthorities();
    }
}
