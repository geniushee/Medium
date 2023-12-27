package com.ll.medium.global.init;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.aticle.article.service.ArticleService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class InitDevData {
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initData1() {
        return args -> {
            // 유료(홀수), 무료회원(짝수) 생성
            IntStream.range(0,200)
                    .mapToObj(i -> MemberDto.builder()
                            .memberName("user" + i)
                            .memberPassword("1234")
                            .memberEmail("user" + i + "@user.com")
                            .isPaid(i % 2 == 1)
                            .build())
                    .forEach(memberService::joinMember);

            // 관리자 생성
            MemberDto admin = MemberDto.builder()
                    .memberName("admin")
                    .memberPassword("1234")
                    .memberEmail("admin@user.com")
                    .isPaid(true)
                    .build();
            memberService.joinMember(admin);


            for (int i = 1; i < 4; i++) {
                Member member = Member.dtoToEntity(memberService.findById((long) i));
                // article maker
                IntStream.range(0,75)
                        .mapToObj(j -> {
                            Article article = Article.builder()
                                    .title("제목" + j)
                                    .body("내용" + j)
                                    .published(j % 2 == 0)
                                    .isPaid(j % 2 == 0)
                                    .build();
                            return new ArticleDto(article);
                        })
                        .forEach(dto -> articleService.writeArticle(dto, member));
            }
        };
    }
}
