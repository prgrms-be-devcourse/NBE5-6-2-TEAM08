package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.ApprovedCourses;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter

public class CourseDto {
  private int courseId;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private String creatorNickname;

  public CourseDto(ApprovedCourses course) {
    this.courseId = course.getRecommendId();
    this.title = course.getMyCoursesId().getTitle();
    this.description = course.getMyCoursesId().getDescription();
    this.createdAt = course.getMyCoursesId().getCreatedAt();
    this.creatorNickname = course.getId().getNickname();
  }

}

