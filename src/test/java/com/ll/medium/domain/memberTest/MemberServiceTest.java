package com.ll.medium.domain.memberTest;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("test 실행")
    public void t1() {
    }

    @Test
    @DisplayName("member join 확인")
    @Rollback(false)
    public void t2() {
        Member member = Member.builder()
                .memberName("user1")
                .memberPassword("1234")
                .memberEmail("user1@user.com")
                .build();
        MemberDto memberDto = new MemberDto(member);
        memberService.joinMember(memberDto);

        MemberDto user1 = memberService.findByMemberName("user1");

        assertThat(user1.getMemberName()).isEqualTo("user1");
        assertThat(user1.getMemberEmail()).contains("user1");

    }
}
