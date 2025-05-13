package com.grepp.team08.app.model.place.service;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.entity.ApprovedCourses;
import com.grepp.team08.app.model.course.entity.EditorCourses;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import com.grepp.team08.app.model.place.dto.mainpage.AdminUserTopListDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceMainPageService {

  private final AdminCourseRepository adminCourseRepository;
  private final CourseRepository courseRepository;

  @Transactional
  public AdminUserTopListDto mainPagelist() {

      List<EditorCourses> adminplace = adminCourseRepository.findRandom4();
      List<ApprovedCourses> userplace = courseRepository.findRandom4();

    List<EditorCourseDto> adminDto = adminplace.stream()
        .map(EditorCourseDto::new)
        .toList();

    List<CourseDto> userDto = userplace.stream()
        .map(CourseDto::new)
        .toList();

    return new AdminUserTopListDto(adminDto,userDto);




  }

  public List<EditorCourseDto> adminPageList() {
    List<EditorCourses> adminPlace = adminCourseRepository.findAll();
    List<EditorCourseDto> adminDto = adminPlace.stream()
        .map(EditorCourseDto::new)
        .toList();
    return adminDto;

  }
//
  public List<CourseDto> userPageList() {
    List<ApprovedCourses> userPlace = courseRepository.findAll();
    List<CourseDto> courseDto = userPlace.stream()
        .map(CourseDto::new)
        .toList();
    return courseDto;

  }
}
