package com.grepp.team08.app.controller.web.admin;

import org.springframework.stereotype.Controller;
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

}
