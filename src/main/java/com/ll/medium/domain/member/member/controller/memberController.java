package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.domain.member.member.Data.JoinForm;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.Rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class memberController {

    private final MemberService memberService;
    private final ArticleService articleService;
    private final Rq rq;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showMemberJoin() {
        return "domain/member/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String joinMember(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "domain/member/member/join";
        }
        if (joinForm.getMemberPassword() != joinForm.getMemberPasswordConfirm()){
            return rq.direct("/member/join","비밀번호를 재확인해주세요.");
        }

        String joinName = joinForm.getMemberName();
        if (memberService.findByMemberName(joinName) != null) {
            return rq.direct("/member/join", "이미 존재하는 ID입니다.");
        }

        MemberDto memberDto = joinForm.toDto();
        memberService.joinMember(memberDto);
        return "redirect:/";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signin")
    public String showSignIn(){
        return "domain/member/member/signin";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(@RequestParam(name = "page", defaultValue = "0") int page,
            Model model){

        Member member = rq.getMember();
        Page<Article> pages = articleService.findAllByAuthor(member,page);
        model.addAttribute("myInfo", member);
        model.addAttribute("articles", pages);
        return "domain/member/member/profile";
    }


}
