package com.grepp.team08.app.model.place.service;

import com.grepp.team08.app.model.course.dto.CourseDetailDto;
import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.dto.EditorDetailCourseDto;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
import com.grepp.team08.app.model.place.dto.PlaceDetailDto;
import com.grepp.team08.app.model.place.dto.mainpage.AdminUserTopListDto;
import com.grepp.team08.app.model.place.entity.Place;
import com.grepp.team08.app.model.place.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
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
  private final PlaceRepository placeRepository;


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

  @Transactional
  public CourseDetailDto userDetailPlace(Long recommendId) {

    RecommendCourse recommendCourse = courseRepository.findById(recommendId)
        .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));
    CourseDetailDto placeDetail = new CourseDetailDto();
    placeDetail.setTitle(recommendCourse.getCourseId().getTitle());
    placeDetail.setNickname(recommendCourse.getCourseId().getId().getNickname());
    placeDetail.setCreateAt(recommendCourse.getCreatedAt());
    placeDetail.setDescription(recommendCourse.getCourseId().getDescription());
    Course course = recommendCourse.getCourseId();
    List<Place> places = placeRepository.findAllByCourseId(course);
    List<PlaceDetailDto> placeDetailDtos = places.stream()
            .map(PlaceDetailDto::new)
                .toList();

    placeDetail.setPlaces(placeDetailDtos);
    List<Image> image = imageRepository.findAllByRecommendCourseId(recommendCourse);
    List<String> imageUrl = image.stream()
        .map(img -> {
          if(img != null){
            return "/image/"+img.getRenameFileName();
          }
          else {
            return  "/image/bg_night.jpg";
          }
        })
            .toList();

    placeDetail.setImageUrl(imageUrl);
    return placeDetail;



  }

  @Transactional
  public EditorDetailCourseDto editorDetailPlace(Long editorId) {
    EditorCourse editorCourse = adminCourseRepository.findById(editorId)
        .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));
    EditorDetailCourseDto placeDetail = new EditorDetailCourseDto();
    placeDetail.setTitle(editorCourse.getTitle());
    placeDetail.setNickname(editorCourse.getMember().getNickname());
    placeDetail.setCreateAt(editorCourse.getCreatedAt());
    placeDetail.setDescription(editorCourse.getDescription());

    List<Place> places = placeRepository.findAllByEditorCourseId(editorCourse);
    List<PlaceDetailDto> placeDetailDtos = places.stream()
        .map(PlaceDetailDto::new)
        .toList();

    placeDetail.setPlaces(placeDetailDtos);
    List<Image> image = imageRepository.findAllByEditorCourseId(editorCourse);
    List<String> imageUrl = image.stream()
        .map(img -> {
          if(img != null){
            return "/image/"+img.getRenameFileName();
          }
          else {
            return  "/image/bg_night.jpg";
          }
        })
        .toList();

    placeDetail.setImageUrl(imageUrl);
    return placeDetail;

  }
}
