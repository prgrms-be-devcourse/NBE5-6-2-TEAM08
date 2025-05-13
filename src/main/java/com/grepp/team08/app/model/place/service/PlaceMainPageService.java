package com.grepp.team08.app.model.place.service;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
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
  private final ImageRepository imageRepository;

  @Transactional
  public AdminUserTopListDto mainPagelist() {

      List<EditorCourse> adminplace = adminCourseRepository.findTop4ByOrderByCreatedAtDesc();
      List<RecommendCourse> userplace = courseRepository.findTop4ByOrderByCreatedAtDesc();


    List<EditorCourseDto> adminDto = adminplace.stream()
        .map(course -> {
          Image img = imageRepository.findFirstByEditorCourseId(course)
              .orElse(null);
          if(img !=null){
            String imageUrl = "/image/" + img.getRenameFileName();
            return new EditorCourseDto(course, imageUrl);
          }
          else{
            return new EditorCourseDto(course,"/image/bg_night.jpg");
          }

        })
        .toList();


    List<CourseDto> userDto = userplace.stream()
        .map(course -> {
          Image img = imageRepository.findFirstByRecommendCourseId(course)
              .orElse(null);
          if(img !=null){
            String imageUrl = "/image/" + img.getRenameFileName();
            return new CourseDto(course, imageUrl);
          }
          else{
            return new CourseDto(course,"/image/bg_night.jpg");
          }

        })
        .toList();

    return new AdminUserTopListDto(adminDto,userDto);




  }
  @Transactional
  public List<EditorCourseDto> adminPageList() {
    List<EditorCourse> adminPlace = adminCourseRepository.findAll();
    List<EditorCourseDto> adminDto = adminPlace.stream()
        .map(course -> {
          Image img = imageRepository.findFirstByEditorCourseId(course)
              .orElse(null);
          if(img !=null){
            String imageUrl = "/image/" + img.getRenameFileName();
            return new EditorCourseDto(course, imageUrl);
          }
          else{
            return new EditorCourseDto(course,"/image/bg_night.jpg");
          }

        })
        .toList();
    return adminDto;

  }
  @Transactional
  public List<CourseDto> userPageList() {
    List<RecommendCourse> userPlace = courseRepository.findAll();
    List<CourseDto> courseDto = userPlace.stream()
        .map(course -> {
          Image img = imageRepository.findFirstByRecommendCourseId(course)
              .orElse(null);
          if(img !=null){
            String imageUrl = "/image/" + img.getRenameFileName();
            return new CourseDto(course, imageUrl);
          }
          else{
            return new CourseDto(course,"/image/bg_night.jpg");
          }

        })
        .toList();
    return courseDto;

  }
}
