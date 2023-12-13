package com.ll.medium.global.Rq;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequestScope
@Component
@Getter
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private User user;
    private Member member;

    @PostConstruct
    public void init(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof User){
            this.user = (User) authentication.getPrincipal();
        }
    }

    public String direct(String page, String msg){
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        return page + "?msg=" + encodedMsg;
    }

    public String redirect(String page, String msg){
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        return "redirect:" + page + "?msg=" + encodedMsg;
    }

public Member getMember(){
        if (user == null){
            return null;
        }
        String username = user.getUsername();
        MemberDto memberDto = memberService.findByMemberName(username);
        return Member.dtoToEntity(memberDto);
}

    public boolean isLogined(){
        return user != null;
    }
}
