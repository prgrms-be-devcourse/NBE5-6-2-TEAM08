package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.infra.response.ApiResponse;
import com.grepp.team08.infra.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class RegistMyCourseApiController {

    private final CourseService courseService;

    @PostMapping(value = "/recommend-course-regist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recommendCourseRegist(
        @RequestParam("images") List<MultipartFile> images,
        @RequestParam("courseId") Long courseId,
        @AuthenticationPrincipal Member member
    ) {
        
        try {
            // 이미지 유효성 검사
            if (images == null || images.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "최소 1장의 이미지가 필요합니다."));
            }

            courseService.registToRecommendCourse(courseId, images);
            
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.success("코스가 성공적으로 등록되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST, e.getMessage()));
        }
    }
} 