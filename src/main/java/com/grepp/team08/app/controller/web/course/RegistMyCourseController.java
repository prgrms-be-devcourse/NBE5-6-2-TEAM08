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

    // 내가 만든 데이트 코스를 추천 코스로 등록하는 페이지 (마이페이지)
    // js - html 이름 매칭이 안됨. 같은 이름으로 묶어야 한다. 아니면 script 로 합치던지.
    // Course Controller 에 합쳐도 될 거 같은데? 나눌 이유가 없음.
    // view 제외하고 나머지 조회요청은 전부 Api Controller 로 옮겨
    // ResponseEntity 로 수정
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
