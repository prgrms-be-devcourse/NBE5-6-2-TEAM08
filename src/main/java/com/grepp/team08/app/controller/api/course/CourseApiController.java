package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.course.dto.CourseDetailDto;
import com.grepp.team08.app.model.course.dto.MyCourseResponse;
import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.dto.RecommendCourseRegistRequestDto;
import com.grepp.team08.app.model.course.service.CourseService;
import com.grepp.team08.app.model.image.service.ImageService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import com.grepp.team08.infra.response.ApiResponse;
import com.grepp.team08.infra.response.ResponseCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Slf4j
public class CourseApiController {

    private final CourseService courseService;
    private final MemberService memberService;
    private final ImageService imageService;

    // 내가 만든 데이트 코스 등록
    // 메서드명 > saveMyCourse 정도로 해도 충분
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

    // 내가 만든 데이트 코스 목록 조회 (마이페이지)
    // 엔드포인트 이름 수정 좀 하자
    // 연결되는 웹페이지가 어디지?
    @GetMapping("/mycourse")
    public ResponseEntity<ApiResponse<List<MyCourseResponse>>> getMyCourses(
        @AuthenticationPrincipal Principal principal
    ) {
        String userId = principal.getUsername();

        Member member = memberService.findByUserId(userId);

        List<MyCourseResponse> courses = courseService.findMyCourses(member);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    // 내가 만든 데이트 코스 상세정보 조회 (마이페이지)
    // 너도 엔드포인트 수정 좀 하자
    @GetMapping("/mycourse/{id}")
    public CourseDetailDto getCourseDetail(@PathVariable("id") Long courseId) {
        return courseService.getCourseDetail(courseId);
    }

    // 내가 만든 데이트 코스 추천 코스 등록 시 사진 업로드
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

    // 내가 만든 데이트 코스 추천 코스로 등록 (사진 제외)
    // 예외처리 굿
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