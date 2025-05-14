package com.grepp.team08.app.controller.web.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {

    @GetMapping("/course_composition")
    public String courseComposition() {
        return "course_composition";
    }
    @GetMapping("/editor-recommand-courses")
    public String editorCourse(){ return "course_list";}

    @GetMapping("/recommend-courses")
    public String usercourse(){return "course_list_user";}
}
