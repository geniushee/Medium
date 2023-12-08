package com.ll.medium.domain.member.member.Data;

import com.ll.medium.domain.member.member.memberDto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JoinForm {

    @NotEmpty(message = "ID는 필수항목입니다.")
    private final String memberName;

    @NotEmpty(message = "Password는 필수항목입니다.")
    private final String memberPassword;

    @NotEmpty
    private final String memberPasswordConfirm;

    @NotEmpty(message = "Email은 필수항목입니다.")
    @Email
    private final String memberEmail;

    public MemberDto toDto(){
        return MemberDto.builder()
                .memberName(memberName)
                .memberPassword(memberPassword)
                .memberEmail(memberEmail)
                .build();
    }
}
