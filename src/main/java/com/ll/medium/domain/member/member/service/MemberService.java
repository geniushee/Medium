package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.memberDto.MemberDto;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void joinMember(MemberDto memberDto) {
        passwordEncording(memberDto);
        Member member = Member.dtoToEntity(memberDto);
        memberRepository.save(member);
    }

    public MemberDto findByMemberName(String memberName) {
        Optional<Member> opMember = memberRepository.findByMemberName(memberName);
        return new MemberDto(opMember.get());
    }

    private void passwordEncording(MemberDto memberDto){
        String encodedPassword = passwordEncoder.encode(memberDto.getMemberPassword());
        memberDto.setMemberPassword(encodedPassword);
    }
}
