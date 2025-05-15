package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
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

    @PostMapping("/save")
    public ResponseEntity<String> saveCourseWithPlaces(
        @RequestBody MyDateCourseDto dto,
        @AuthenticationPrincipal Member member // 현재 로그인 사용자 정보
    ) {
        courseService.saveCourse(dto, member);
        return ResponseEntity.ok("코스 저장 성공");
    }
}
