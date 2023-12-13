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

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class InitDevData {
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initData1() {
        return args -> {
            for (int i = 1; i < 4; i++) {
                MemberDto member = MemberDto.builder()
                        .memberName("user" + i)
                        .memberPassword("1234")
                        .memberEmail("user" + i + "@user.com")
                        .build();
                memberService.joinMember(member);
            }
            MemberDto admin = MemberDto.builder()
                    .memberName("admin")
                    .memberPassword("1234")
                    .memberEmail("admin@user.com")
                    .build();
            memberService.joinMember(admin);
            for (int i = 1; i < 4; i++) {
                Member member = Member.dtoToEntity(memberService.findById((long) i));
                for (int j = 0; j < i * 50; j++) {
                    Article article = Article.builder()
                            .title("제목" + j)
                            .body("내용" + j)
                            .published(true)
                            .build();
                    ArticleDto dto = new ArticleDto(article);
                    articleService.writeArticle(dto, member);
                }
            }
        };
    }
}
