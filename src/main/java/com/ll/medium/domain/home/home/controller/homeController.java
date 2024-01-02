package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class homeController {
    private final ArticleService articleService;

    @GetMapping("/")
    public String showMain(Model model){
        // pageabel -> Top30로 변경
        ArrayList<Article> list = articleService.showMain();
        model.addAttribute("list", list);
        return "domain/home/home/main";
    }

    @GetMapping("/session")
    @ResponseBody
    public Map<String, Object> showSesseion(HttpSession httpSession){
        return Collections.list(httpSession.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> httpSession.getAttribute(key)
                        )
                );
    }
}
