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

    // TODO 글 목록 조회: 전체 글 리스트 (공개된 글만 노출) get - '/post/list'

    // TODO 내 글 목록 조회: 내 글 리스트 조회 get - '/post/myList'

    // TODO 글 상세내용 조회: ?번 글 상세보기 get - '/post/1''

    // TODO 글 작성 : 글 작성 폼 get - '/post/write'

    // TODO 글 작성 : 글 작성 처리 post - '/post/write'

    // TODO 글 수정 : 글 수정 폼 get - '/post/1/modify'

    // TODO 글 수정 : 글 수정 처리 post - '/post/1/modify'

    // TODO 글 삭제 : ?번 글 삭제 delete - '/post/1/delete'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 전체 글 리스트 get - '/b/user1'

    // TODO 특정 회원의 글 모아보기 : 회원 user1의 글 중에서 3번 글 상세보기 get - '/b/user1/3'
}
