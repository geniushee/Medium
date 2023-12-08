package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
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
    public String joinMember(@Valid MemberDto memberDto) {
        memberService.joinMember(memberDto);
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String showSignIn(){
        return "domain/member/member/signin";
    }

//    @PostMapping("/signin")  // security의 formLogin 사용
//    public String signIn(@Valid MemberDto memberDto){
//        memberService.signIn(memberDto);
//        return "redirect:/";
//    }
}
