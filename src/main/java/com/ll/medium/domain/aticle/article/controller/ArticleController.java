package com.ll.medium.domain.aticle.article.controller;

import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.global.Rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;
}
