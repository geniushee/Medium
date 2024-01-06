package com.ll.medium.global.security;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        MemberDto memberDto = memberService.findByMemberName(username);

        // String으로 저장된 authorities를 simpleGrantedAuthorities로 변경
        List<SimpleGrantedAuthority> authorities = memberDto.getMemberAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(memberDto.getMemberName(), memberDto.getMemberPassword(), authorities);
    }


}
