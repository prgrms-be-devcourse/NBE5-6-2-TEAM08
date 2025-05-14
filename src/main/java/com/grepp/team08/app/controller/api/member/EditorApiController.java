package com.grepp.team08.app.controller.api.member;

import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.member.dto.AdminSearchUserDto;
import com.grepp.team08.app.model.member.service.AdminService;
import com.grepp.team08.infra.response.ApiResponse;
import com.grepp.team08.infra.response.ResponseCode;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EditorApiController {

  private final AdminService adminService;

  @GetMapping("/admin/users") //회원조회 하는거임
  public ResponseEntity<?> userSearchAll(){

    List<AdminSearchUserDto> userAll = adminService.userAllSearch();

    return ResponseEntity.ok(userAll);

  }

  @GetMapping("/admin/recommand-courses")//코스 목록
  public ResponseEntity<?> adminCourseAll(){

    List<EditorCourseDto> editorcourses = adminService.adminAllCourse();

    return ResponseEntity.ok(editorcourses);
  }

  //코스 삭제
  //소프트 delete
  @PostMapping("/admin/recommand-courses/{recommand_id}")
  public ResponseEntity<?> deleteCourse(@PathVariable Long recommand_id){

    adminService.adminRecommandDelete(recommand_id);

    return ResponseEntity.ok().body(Map.of("message","성공적으로 처리되었습니다"));
  }




}
