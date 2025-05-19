package com.grepp.team08.app.model.like.dto;

import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteRecommendRequestDto {

  private Long recommendId;
  private String userId;


}
