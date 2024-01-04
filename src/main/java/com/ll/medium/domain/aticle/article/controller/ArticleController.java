package com.ll.medium.domain.aticle.article.controller;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.Data.ArticleForm;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.global.Rq.Rq;
import com.ll.medium.global.rsdata.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    @GetMapping("/list")
    public String showPublicArticle(@RequestParam(value = "page", defaultValue = "1") int page
            ,Model model) {
        Page<Article> list = articleService.findAllByPublished(page);
        model.addAttribute("publicList", list);
        return "domain/article/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myList")
    public String showMyArticle(@RequestParam(value = "page", defaultValue = "0") int page,
                                Model model){
        // get member
        Member member = rq.getMember();

        Page<Article> paging = articleService.findAllByAuthor(member, page);
        model.addAttribute("paging", paging);
        return "domain/article/article/myList";
    }

    @GetMapping("/{id}")
    public String showArticleDetails(@PathVariable("id") long id,
                                     Model model) {
        Member member = rq.getMember();

        RsData<ArticleDto> rs = articleService.showArticleDetails(id, member);
        // 비공개글 처리
        if (rs.getError().equals("비공개")){
            return rq.redirect(rq.previousURL(), rs.getMsg());
        }

        ArticleDto dto = rs.getObj();
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
        return rq.redirect("/post/list", "글 작성이 성공적으로 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModifyForm(@PathVariable("id") long id,
                                 Model model){

        if (!articleService.canModify(id, rq.getMember())){
            return rq.redirect(rq.previousURL(), "작성자가 아닙니다.");
        }
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("article", dto);
        return "domain/article/article/modifyForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modifyArticle(@PathVariable("id") long id,
                                @Valid ArticleForm articleForm,
                                BindingResult bindingResult,
                                Model model){
        if (bindingResult.hasErrors()){
            throw new RuntimeException("잘못된 입력입니다.");
        }
        ArticleDto dto = articleForm.toDto();
        ArticleDto result = articleService.modifyArticle(id, dto);
        model.addAttribute("article", result);
        return rq.redirect("/post/%d".formatted(id),"수정이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/delete")
    public String deleteArticle(@PathVariable("id") long id){
        articleService.deleteArticle(id);
        return rq.redirect("/post/list", "%d번 글이 삭제되었습니다.".formatted(id));
    }

    // TODO 조회수 증가 완료
    // TODO 1. /post/5/increaseHit 엔드포인트 설정 완료
    // TODO 2. 작성자가 아니여야 한다.
    // TODO 3. redirect를 할지 post로 조회를 할지 결정 완료
    // TODO 4. article 조회수 필드 추가 완료
    // TODO 5.1 cookie를 사용하여 최근 조회 제외
    // TODO 5.2 유료글 제외
    // TODO 6. 강사님과 비교
    @GetMapping("/{id}/increaseHit")
    public String increaseHitOfArticle(@PathVariable("id") long id,
                                       Model model){
        // 작성자 본인 제외
        if (articleService.canModify(id, rq.getMember())){
            ArticleDto dto = articleService.findById(id);
            model.addAttribute("article", dto);
            return "domain/article/article/articleDetails";
        }
        articleService.increaseHit(id);
        return showArticleDetails(id, model);
    }
}
