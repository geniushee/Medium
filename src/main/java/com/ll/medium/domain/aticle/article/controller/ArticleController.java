package com.ll.medium.domain.aticle.article.controller;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.Data.ArticleForm;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.global.Rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    @GetMapping("/list")
    public String showPublicArticle(Model model) {
        List<Article> list = articleService.findAllByPublished();
        model.addAttribute("publicList", list);
        return "domain/article/article/list";
    }

    // TODO 내 글 목록 조회: 내 글 리스트 조회 get - '/post/myList'

    // TODO 글 상세내용 조회: ?번 글 상세보기 get - '/post/1''
    @GetMapping("/{id}")
    public String showArticleDetails(@PathVariable("id") long id,
                                     Model model) {
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("article", dto);
        return "domain/article/article/articleDetails";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWriteForm() {
        return "domain/article/article/writeForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writeArticle(@Valid ArticleDto articleDto,
                               BindingResult bindingResult) {
        Member member = rq.getMember();
        articleService.writeArticle(articleDto, member);
        return rq.redirect("domain/article/article/list", "글 작성이 성공적으로 완료되었습니다.");
    }

    // TODO 글 수정 : 글 수정 폼 get - '/post/1/modify'
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModifyForm(@PathVariable("id") long id,
                                 Model model){
        // TODO 유저 확인 필요
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("article", dto);
        return "domain/article/article/modifyForm";
    }

    // TODO 글 수정 : 글 수정 처리 post - '/post/1/modify'
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modifyArticle(@PathVariable("id") long id,
                                @Valid ArticleForm articleForm,
                                BindingResult bindingResult,
                                Model model){
        // TODO 유저 확인 필요
        if (bindingResult.hasErrors()){
            throw new RuntimeException("잘못된 입력입니다.");
        }
        ArticleDto dto = articleForm.toDto();
        ArticleDto result = articleService.modifyArticle(id, dto);
        model.addAttribute("article", result);
        return rq.redirect("/post/%d".formatted(id),"수정이 완료되었습니다.");
    }

    // TODO 글 삭제 : ?번 글 삭제 delete - '/post/1/delete'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 전체 글 리스트 get - '/b/user1'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 글 중에서 3번 글 상세보기 get - '/b/user1/3'
}
