package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.EditorCourses;
import lombok.Getter;

@Getter

public class EditorCourseDto {
  private Integer courseId;
  private String title;
  private String description;
  private String editorNickname;

  public EditorCourseDto(EditorCourses course) {
    this.courseId = course.getEditorNum();
    this.title = course.getTitle();
    this.description = course.getDescription();
    this.editorNickname = course.getId().getNickname();
  }




}
