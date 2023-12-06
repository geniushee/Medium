package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinMember(MemberDto memberDto) {
        Member member = Member.dtoToEntity(memberDto);
        memberRepository.save(member);
    }

    public MemberDto findByMemberName(String memberName) {
        Optional<Member> opMember = memberRepository.findByMemberName(memberName);
        return new MemberDto(opMember.get());
    }
}
