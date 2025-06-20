package com.grepp.team08.app.controller.api.course;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.course.dto.EditorCourseSaveDto;
import com.grepp.team08.app.model.course.service.EditorCourseService;
import com.grepp.team08.app.model.image.service.ImageService;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/editor-course")
@RequiredArgsConstructor
@Slf4j
public class EditorCourseApiController {

    private final EditorCourseService editorCourseService;
    private final MemberService memberService;
    private final ImageService imageService;

    // 코스 등록 로직
    @PostMapping("/save")
    public ResponseEntity<?> saveCourse(
        @RequestBody EditorCourseSaveDto dto,
        @AuthenticationPrincipal Principal principal) {

        String userId = principal.getUsername(); // 또는 getUsername()
        Member member = memberService.findByUserId(userId); // 명확하게 조회

        editorCourseService.save(dto, member);
        return ResponseEntity.ok("저장 성공");
    }

    // 코스 이미지 업로드 로직(코스와 따로 업로드)
    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadImages(
        @RequestParam("images") List<MultipartFile> images) {

        List<String> urls = imageService.upload(images);
        return ResponseEntity.ok(urls);
    }
}
