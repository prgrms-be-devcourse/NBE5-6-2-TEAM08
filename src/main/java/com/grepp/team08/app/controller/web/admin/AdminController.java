package com.grepp.team08.app.controller.web.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

  @GetMapping("/admin/users")
  public String adminUserSearch(){
    return "admin_userManager";
  }

  @GetMapping("/admin/recommand-courses")
  public String adminCourseSearch(){
    return "adminPageCourse";
  }

  @Value("${kakao.api.key}")
  private String kakaoApiKey;

  @GetMapping("/admin/editor-course")
  public String editorCourse(Model model){
    model.addAttribute("kakaoApiKey", kakaoApiKey);
    return "editor_course";
  }

}
