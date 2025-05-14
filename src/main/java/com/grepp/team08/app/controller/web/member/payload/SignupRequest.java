package com.grepp.team08.app.controller.web.member.payload;

import com.grepp.team08.app.model.member.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    
    @NotBlank
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 1, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력하세요.")
    private String password;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일 형식으로 입력하세요.")
    private String email;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotBlank(message = "생년월일을 입력하세요.")
    private String birth;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String phone;

    public MemberDto toDto(){
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(this.userId);
        memberDto.setPassword(this.password);
        memberDto.setEmail(this.email);
        memberDto.setName(this.name);
        memberDto.setNickname(this.nickname);
        memberDto.setBirth(this.birth);
        memberDto.setPhone(this.phone);
        return memberDto;
    }
}
