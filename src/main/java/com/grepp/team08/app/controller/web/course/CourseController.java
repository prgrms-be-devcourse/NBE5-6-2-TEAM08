package com.grepp.team08.app.controller.web.course;

import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/recommend-courses/{recommend_id}")
    public String detailUserCourse(@PathVariable Long recommend_id, Model model) {
        model.addAttribute("recommendId", recommend_id);
        return "course_detail";
    }
    @GetMapping("/editor-recommand-courses/{recommand_id}")
    public String detailEditorCourse(@PathVariable Long recommand_id, Model model){
        model.addAttribute("recommendId",recommand_id);
        return"editor_pick_detail";
    }
}
