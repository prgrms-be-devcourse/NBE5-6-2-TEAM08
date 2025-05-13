package com.grepp.team08.app.model.member.dto;


import com.grepp.team08.app.model.auth.code.Role;
import com.grepp.team08.app.model.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberDto {
  private int id;
  private String userid;
  private String email;
  private String name;
  private String nickname;
  private String phone;
  private Role role;
  private Boolean activated;


  public MemberDto(Member member) {
    this.id = member.getId();
    this.userid = member.getUserid();
    this.email = member.getEmail();
    this.name = member.getName();
    this.nickname = member.getNickname();
    this.phone = member.getPhone();
    this.role = member.getRole();
    this.activated = member.getActivated();
  }


}
