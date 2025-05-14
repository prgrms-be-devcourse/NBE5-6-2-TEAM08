package com.grepp.team08.app.model.course.dto;

import com.grepp.team08.app.model.course.entity.EditorCourse;
import lombok.Getter;

@Getter

public class EditorCourseDto {
  private Long courseId;
  private String title;
  private String description;
  private String editorNickname;
  private String imageurl;

  public EditorCourseDto(EditorCourse course,String imageurl) {
    this.courseId = course.getEditorCourseId();
    this.title = course.getTitle();
    this.description = course.getDescription();
    this.editorNickname = course.getId().getNickname();
    this.imageurl = imageurl;
  }




}
