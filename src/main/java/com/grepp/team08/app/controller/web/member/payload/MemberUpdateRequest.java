package com.grepp.team08.app.controller.web.member.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest {
    private String password;
    private String phone;
    private String email;
    private String nickname;
}