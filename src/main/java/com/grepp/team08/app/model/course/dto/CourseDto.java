package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.RecommendCourse;
import lombok.Getter;

@Getter

public class CourseDto {
  private Long courseId;
  private String title;
  private String description;
  private String creatorNickname;
  private String imageurl;

  public CourseDto(RecommendCourse course,String imageurl) {
    this.courseId = course.getRecommendCourseId();
    this.title = course.getCourseId().getTitle();
    this.description = course.getCourseId().getDescription();
    this.creatorNickname = course.getCourseId().getId().getNickname();
    this.imageurl = imageurl;
  }

}

