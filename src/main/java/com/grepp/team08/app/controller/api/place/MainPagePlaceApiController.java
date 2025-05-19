package com.grepp.team08.app.controller.api.place;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.course.dto.CourseDetailDto;
import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.dto.EditorDetailCourseDto;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.like.service.FavoriteService;
import com.grepp.team08.app.model.place.dto.mainpage.AdminUserTopListDto;
import com.grepp.team08.app.model.place.service.PlaceMainPageService;
import com.grepp.team08.app.model.review.dto.RequestReviewDto;
import com.grepp.team08.app.model.review.dto.ResponseReviewDto;
import com.grepp.team08.app.model.review.service.ReviewService;
import com.grepp.team08.infra.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainPagePlaceApiController {

  private final PlaceMainPageService placeMainPageService;
  private final ReviewService reviewService;
  private final FavoriteService favoriteService;


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
  public ResponseEntity<?> recommendCourse(@PathVariable Long recommend_id){

    CourseDetailDto  course = placeMainPageService.userDetailPlace(recommend_id);

    return ResponseEntity.ok(course);
  }
  @GetMapping("/editor-recommand-courses/{recommand_id}")
  public ResponseEntity<?> editorDetailCourse(@PathVariable Long recommand_id){

    EditorDetailCourseDto course = placeMainPageService.editorDetailPlace(recommand_id);

    return ResponseEntity.ok(course);
  }

  //리뷰 보여주기
  @GetMapping("/recommend-courses/{recommendId}/reviews")
  public ResponseEntity<?> reviewAll(@PathVariable Long recommendId){

    List<ResponseReviewDto> reviewlist = reviewService.reviewlist(recommendId);

    return ResponseEntity.ok(reviewlist);

  }
  @PostMapping("/recommend-courses/{recommend_id}")
  public ResponseEntity<?> reviewUpload(@PathVariable Long recommend_id,@RequestBody RequestReviewDto dto,@AuthenticationPrincipal
      Principal principal){


    String userNum = principal.getUsername();

    reviewService.reviewUpload(recommend_id,dto,userNum);

    return ResponseEntity.ok(ApiResponse.success("리뷰 등록이 되었습니다"));

  }

  //찜하기 사용자
  @PostMapping("/users/{recommend_id}/likes")
  public ResponseEntity<?> likeCourse(@PathVariable Long recommend_id,@AuthenticationPrincipal Principal principal){

    String userId = principal.getUsername();

    favoriteService.userFavoriteAdd(recommend_id, userId);

    return ResponseEntity.ok(ApiResponse.success("유저 코스 찜하기"));
  }
  //찜하기 관리자꺼
  @PostMapping("/admin/{recommend_id}/likes")
  public ResponseEntity<?> likeAdminCourse(@PathVariable Long recommend_id,@AuthenticationPrincipal Principal principal){

    String userId = principal.getUsername();

    favoriteService.adminFavoriteAdd(recommend_id, userId);

    return ResponseEntity.ok(ApiResponse.success("관리자 코스 찜하기"));


  }





}
