package com.grepp.team08.app.controller.web.course;

import com.grepp.team08.app.model.course.dto.RegistMyCourseDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.course.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistMyCourseController {

    private final CourseService courseService;

    // 나의 데이트 코스 추천 코스 등록 페이지 이동
    @GetMapping("/make-mycourses")
    public String makeMyCourses(
            @RequestParam(required = false) Long courseId, 
            @AuthenticationPrincipal Member member,
            Model model) {
        
        // courseId가 없으면 사용자의 코스 목록을 보여줌
        if (courseId == null) {
            List<Course> userCourses = courseService.getCoursesByMember(member);
            model.addAttribute("courses", userCourses);
            return "course_list_user"; // 사용자 코스 목록을 보여주는 페이지로 이동
        }

        // courseId가 있으면 해당 코스 상세 정보를 보여줌
        try {
            Course course = courseService.getCourseById(courseId);
            // 본인의 코스인지 확인
            if (!course.getId().equals(member)) {
                return "redirect:/error";
            }
            model.addAttribute("course", course);
            return "make_mycourses";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    // 내 데이트 코스를 추천 코스로 등록
    @PostMapping("/make-mycourses")
    public String regist(
            @RequestParam Long courseId,
            @AuthenticationPrincipal Member member) {
        try {
            Course course = courseService.getCourseById(courseId);
            // 본인의 코스인지 확인
            if (!course.getId().equals(member)) {
                return "redirect:/error";
            }
            courseService.registToRecommendCourse(courseId);
            return "redirect:/recommend-courses";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
}
