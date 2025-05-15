package com.grepp.team08.app.controller.web.course;

import org.bouncycastle.math.raw.Mod;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import com.grepp.team08.app.model.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // 코스 구성 페이지 이동
    @GetMapping("/course-composition")
    public String courseComposition(@RequestParam(required = false) String mood, Model model) {
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
    // 나의 데이트 코스 저장 페이지 이동
    @GetMapping("/make-mycourses")
    public String makeMyCourses() {
        return "make_mycourses";
    }

    // 내 데이트 코스에 저장 post 요청
    @PostMapping("/make-mycourses")
    public String regist(Course course) {
//        courseService.registCourses(course.getTitle());
        return null;
    }

}
