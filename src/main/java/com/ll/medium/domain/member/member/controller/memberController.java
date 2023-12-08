package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.Data.JoinForm;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.Rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class memberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    public String showMemberJoin() {
        return "domain/member/member/join";
    }

    @PostMapping("/join")
    public String joinMember(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "domain/member/member/join";
        }
        if (joinForm.getMemberPassword() != joinForm.getMemberPasswordConfirm()){
            return rq.direct("domain/member/member/join","비밀번호를 재확인해주세요.");
        }

        String joinName = joinForm.getMemberName();
        if (memberService.findByMemberName(joinName) != null) {
            return rq.direct("domain/member/member/join", "이미 존재하는 ID입니다.");
        }

        MemberDto memberDto = joinForm.toDto();
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
