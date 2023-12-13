package com.ll.medium.domain.aticle.article.controller;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    // TODO 글 목록 조회: 전체 글 리스트 (공개된 글만 노출) get - '/post/list'
    @PreAuthorize("isAnonymous()")
    @GetMapping("/list")
    public String showPublicArticle(Model model){
        List<Article> list = articleService.findAllByPublished();
        model.addAttribute("publicList", list);
        return "domain/article/article/list";
    }

    // TODO 내 글 목록 조회: 내 글 리스트 조회 get - '/post/myList'

    // TODO 글 상세내용 조회: ?번 글 상세보기 get - '/post/1''

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWriteForm(){
        return "domain/article/article/writeForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writeArticle(@Valid ArticleDto articleDto,
                               BindingResult bindingResult){
        Member member = rq.getMember();
        articleService.writeArticle(articleDto, member);
        // TODO redirect 위치 변경 필요 / -> list (23.12.11)
        return rq.redirect("/", "글 작성이 성공적으로 완료되었습니다.");
    }

    // TODO 글 수정 : 글 수정 폼 get - '/post/1/modify'

    // TODO 글 수정 : 글 수정 처리 post - '/post/1/modify'

    // TODO 글 삭제 : ?번 글 삭제 delete - '/post/1/delete'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 전체 글 리스트 get - '/b/user1'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 글 중에서 3번 글 상세보기 get - '/b/user1/3'
}
