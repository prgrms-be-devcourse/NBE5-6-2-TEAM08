package com.grepp.team08.app.model.member.service;

import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
import com.grepp.team08.app.model.member.dto.AdminSearchUserDto;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;
  private final AdminCourseRepository adminCourseRepository;
  private final ImageRepository imageRepository;

  @Transactional
  public List<AdminSearchUserDto> userAllSearch() {

    List<Member> userAll = adminRepository.findAll();
     List<AdminSearchUserDto> userDtos = userAll.stream()
        .map(AdminSearchUserDto::new)
        .toList();

     return userDtos;
  }

  @Transactional
  public List<EditorCourseDto> adminAllCourse() {
    List<EditorCourse> adminPlace = adminCourseRepository.findAllByActivatedTrue();
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
  public void adminRecommandDelete(Long recommandId) {

    EditorCourse editorCourse = adminCourseRepository.findById(recommandId)
        .orElseThrow(()->new EntityNotFoundException("해당 코스를 찾을수 없습니다"));
    editorCourse.setActivated(false);

  }
}
