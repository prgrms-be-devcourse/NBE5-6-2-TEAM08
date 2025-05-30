package com.grepp.team08.app.controller.web.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // 공통 엔드포인트
public class AdminController {

  // 회원 관리 페이지
  @GetMapping("/users")
  public String adminUserSearch(){
    return "admin_userManager";
  }

  // 에디터픽 코스 목록 (관리자용)
  // 엔드포인트 수정 요망 > editor-courses
  @GetMapping("/recommend-courses")
  public String adminCourseSearch(){
    return "adminPageCourse"; // 페이지 이름도 에디터픽 코스라는 것을 명시해야 함
    // 그냥 추천 코스인지 구분 불가
  }

  // 에디터 픽 코스 만들기 페이지 + 카카오맵 API key
  // Api Controller 에 json 으로 보내는 건 위험하다고 해서 일단 보류
  @Value("${kakao.api.key}")
  private String kakaoApiKey;

  @GetMapping("/editor-course")
  public String editorCourse(Model model){
    model.addAttribute("kakaoApiKey", kakaoApiKey);
    return "editor_course";
  }
}
