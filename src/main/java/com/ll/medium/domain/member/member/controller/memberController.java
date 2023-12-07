package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
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
    public String joinMember(String memberName, String memberPassword, String memberEmail) {
//        Member member = Member.builder()
//                .memberName("user1")
//                .memberPassword("1234")
//                .memberEmail("user1@user.com")
//                .build();
        MemberDto memberDto = MemberDto.builder()
                .memberName(memberName)
                .memberPassword(memberPassword)
                .memberEmail(memberEmail)
                .build();
        memberService.joinMember(memberDto);
        return "redirect:/";
    }
}
