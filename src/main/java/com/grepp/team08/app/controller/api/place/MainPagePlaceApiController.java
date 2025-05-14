package com.grepp.team08.app.controller.api.place;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.place.dto.mainpage.AdminUserTopListDto;
import com.grepp.team08.app.model.place.service.PlaceMainPageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainPagePlaceApiController {

  private final PlaceMainPageService placeMainPageService;

  @GetMapping
  public ResponseEntity<?> mainPageList(){

    AdminUserTopListDto mainlist = placeMainPageService.mainPagelist();

    return ResponseEntity.ok(mainlist);
  }
  @GetMapping("/editor-recommand-courses")
  public ResponseEntity<?> adminPageList(){

    List<EditorCourseDto> adminList = placeMainPageService.adminPageList();

    return ResponseEntity.ok(adminList);

  }
  @GetMapping("/recommend-courses")
  public ResponseEntity<?> userPageList(){

    List<CourseDto> adminList = placeMainPageService.userPageList();

    return ResponseEntity.ok(adminList);

  }
  @GetMapping("/recommend-courses/{recommend_id}")
  public ResponseEntity<?> recommendCourse(@PathVariable int recommend_id){


    return ResponseEntity.ok("d");
  }


}
