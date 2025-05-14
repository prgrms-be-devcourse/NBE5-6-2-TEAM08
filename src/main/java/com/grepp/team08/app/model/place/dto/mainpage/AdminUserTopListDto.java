package com.grepp.team08.app.model.place.dto.mainpage;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import java.util.List;
import lombok.Getter;

@Getter
public class AdminUserTopListDto {

  private List<CourseDto> userlist;
  private List<EditorCourseDto> adminlist;

  public AdminUserTopListDto(List<EditorCourseDto> adminlist,
      List<CourseDto> userlist) {
    this.adminlist = adminlist;
    this.userlist = userlist;
  }


}
