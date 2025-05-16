package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class MyCourseController {

    private final CourseService courseService;
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCourseWithPlaces(
        @RequestBody MyDateCourseDto dto,
        @AuthenticationPrincipal Principal principal
    ) {
        String userId = principal.getUsername(); // 또는 getUsername()
        Member member = memberService.findByUserId(userId); // 명확하게 조회

        courseService.saveCourse(dto, member);
        return ResponseEntity.ok("코스 저장 성공");
    }
}
