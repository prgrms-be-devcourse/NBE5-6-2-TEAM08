package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.course.dto.RecommendCourseRegistRequestDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.image.service.ImageService;
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
    private final ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadImages(
        @RequestParam("images") List<MultipartFile> images) {
        try {
            if (images == null || images.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // 파일 타입 및 크기 로깅
            for (MultipartFile file : images) {

                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest().build();
                }
            }

            List<String> urls = imageService.upload(images);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(urls);
        } catch (Exception e) {
            log.error("이미지 업로드 실패", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/recommend-course-regist")
    public ResponseEntity<?> recommendCourseRegist(
        @RequestBody RecommendCourseRegistRequestDto request,
        @AuthenticationPrincipal Member member
    ) {
        try {
            log.info("추천 코스 등록 시작 - courseId: {}, 이미지 개수: {}", request.getCourseId(), request.getImageUrls().size());
            
            if (request.getImageUrls() == null || request.getImageUrls().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "최소 1장의 이미지가 필요합니다."));
            }

            courseService.registToRecommendCourse(request.getCourseId(), request.getImageUrls());

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