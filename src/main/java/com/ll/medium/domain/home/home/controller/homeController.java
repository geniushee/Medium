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

    // TODO 23.12.15 true가 아닌것도 보인다;;
    @GetMapping("/")
    public String showMain(Model model){
//        int pageNum = 0;
//        int pageSize = 30;
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sorts));
//        Page<Article> page = articleService.findAllByPageable(pageable);
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
