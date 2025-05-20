package com.grepp.team08.app.controller.web.course;

import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.grepp.team08.app.model.course.dto.CourseDetailDto;
import com.grepp.team08.app.model.auth.domain.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistMyCourseController {

    private final CourseService courseService;
    private final MemberService memberService;

    // 나의 데이트 코스를 추천 코스 등록하는 페이지 이동 매핑
    // 실제 요청을 보내는 POST 매핑은 api 컨트롤러에 있습니다.
    @GetMapping("/recommend-course-regist")
    public String recommend_course_regist(
            @RequestParam(required = false) Long courseId,
            @AuthenticationPrincipal Principal principal,
            Model model) {

        if (courseId == null) {
            return "redirect:/my-course";  // courseId가 없으면 코스 목록으로 리다이렉트
        }
        try {
            CourseDetailDto courseDetail = courseService.getCourseDetail(courseId);
            String userId = principal.getUsername();
            Member member = memberService.findByUserId(userId);
            model.addAttribute("member", member);
            model.addAttribute("courseId", courseId);
            model.addAttribute("course", courseDetail);
            return "recommend_course_register";
        } catch (Exception e) {
            log.error("Error rendering template", e);
            return "redirect:/error";
        }
    }
}
