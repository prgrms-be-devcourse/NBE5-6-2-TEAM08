package com.grepp.team08.app.model.member.dto;

import com.grepp.team08.app.model.auth.code.Role;
import com.grepp.team08.app.model.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberDto {
  private String userId;
  private String password;
  private String email;
  private String name;
  private String nickname;
  private String phone;
  private String birth;
  private Role role;
  private LocalDateTime leavedAt;
}
