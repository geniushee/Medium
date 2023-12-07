package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class memberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String showMemberJoin() {
        return "domain/member/member/join";
    }

    @PostMapping("/join")
    public String joinMember() {
//        memberService.joinMember(memberDto);
        return "redirect:/";
    }

    @Data
    public static class MemberJoinForm{
        private String memberName;
        private String memberPassword;
        private String memberEmail;
    }
}
