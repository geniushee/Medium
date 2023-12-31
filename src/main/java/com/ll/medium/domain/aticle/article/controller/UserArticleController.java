package com.ll.medium.domain.aticle.article.controller;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/b")
@RequiredArgsConstructor
public class UserArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    // TODO 수정 여지 있음.
    @GetMapping("/{memberName}")
    public String showArticlesOfMember(@PathVariable("memberName") String memberName,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       Model model) {

        Member member = Member.dtoToEntity(memberService.findByMemberName(memberName));

        Page<Article> paging = articleService.findAllByAuthor(member, page);
        model.addAttribute("paging", paging);
        model.addAttribute("memberName", memberName);
        return "domain/article/article/articlesOfMember";
    }

    @GetMapping("/{memberName}/{id}")
    public String showArticleDetails(@PathVariable("id") long id,
                                     Model model) {
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("article", dto);
        return "domain/article/article/articleDetails";
    }
}
