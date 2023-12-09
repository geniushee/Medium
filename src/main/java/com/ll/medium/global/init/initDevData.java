package com.ll.medium.global.init;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class initDevData {

    @Bean
    public ApplicationRunner initData(MemberService memberService){
        return args -> {
            for (int i = 1; i < 4; i++){
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
        };
    }
}
