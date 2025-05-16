package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.course.dto.MyCourseResponse;
import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import com.grepp.team08.infra.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Slf4j
public class MyCourseController {

    private final CourseService courseService;
    private final MemberService memberService;

    @GetMapping("/mycourse")
    public ResponseEntity<ApiResponse<List<MyCourseResponse>>> getMyCourses(
        @AuthenticationPrincipal Principal principal
    ) {
        String userId = principal.getUsername();

        log.info("로그인한 사용자 userId: {}", userId);
        Member member = memberService.findByUserId(userId);

        List<MyCourseResponse> courses = courseService.findMyCourses(member);
        log.info("조회된 코스 수: {}", courses.size());
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCourseWithPlaces(
        @RequestBody MyDateCourseDto dto,
        @AuthenticationPrincipal Principal principal
    ) {
        String userId = principal.getUsername();
        Member member = memberService.findByUserId(userId);

        courseService.saveCourse(dto, member);

        return ResponseEntity.ok("코스 저장 성공");
    }
}
