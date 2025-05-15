package com.grepp.team08.app.controller.web.course;

import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.course.entity.Course;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    @GetMapping("/recommend-course-regist")
    public String recommend_course_regist(
            @RequestParam(required = false) Long courseId,
            @AuthenticationPrincipal Member member,
            Model model) {
        log.info("Accessing recommend-course-regist endpoint");
        log.info("courseId: {}", courseId);
        log.info("member: {}", member);
        
        // 로그인 체크 임시 주석처리
        /*if (member == null) {
            return "redirect:/member/signin";  // 로그인 페이지로 리다이렉트
        }*/

        if (courseId == null) {
            return "redirect:/my-course";  // courseId가 없으면 코스 목록으로 리다이렉트
        }
        
        try {
            Course course = courseService.getCourseById(courseId);
            // 본인의 코스인지 확인 임시 주석처리
            /*if (!course.getId().equals(member)) {
                return "redirect:/error";
            }*/
            
            model.addAttribute("member", member);
            model.addAttribute("courseId", courseId);
            model.addAttribute("course", course);
            
            return "recommend_course_register";
        } catch (Exception e) {
            log.error("Error rendering template", e);
            return "redirect:/error";
        }
    }

    @PostMapping("/recommend-course-regist")
    public ResponseEntity<?> recommendCourseRegist(
        @RequestParam("images") List<MultipartFile> images,
        @RequestParam("courseId") Long courseId,
        @AuthenticationPrincipal Member member
    ) {
        try {
            // 이미지 유효성 검사
            if (images == null || images.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "최소 1장의 이미지가 필요합니다."));
            }

            // 코스 소유자 확인 - 테스트를 위해 임시 주석처리
            Course course = courseService.getCourseById(courseId);
            /*if (!course.getId().equals(member)) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "본인의 코스만 추천 코스로 등록할 수 있습니다."));
            }*/

            courseService.registToRecommendCourse(courseId, images);
            return ResponseEntity.ok()
                .body(Map.of("message", "코스가 성공적으로 등록되었습니다."));
        } catch (Exception e) {
            log.error("Error registering recommend course", e);
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}
