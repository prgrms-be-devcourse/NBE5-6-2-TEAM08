package com.grepp.team08.app.model.member.service;

import com.grepp.team08.app.model.course.dto.EditorCourseDto;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
import com.grepp.team08.app.model.member.dto.AdminSearchUserDto;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminCourseRepository adminCourseRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public List<AdminSearchUserDto> userAllSearch() {

        List<Member> userAll = adminRepository.findAll();
        List<AdminSearchUserDto> userDtos = userAll.stream()
            .map(AdminSearchUserDto::new)
            .toList();

        return userDtos;
    }

    @Value("${upload.path}")
    private String imageAccessPath;

    @Transactional
    public List<EditorCourseDto> adminAllCourse() {
        List<EditorCourse> adminPlace = adminCourseRepository.findAllByActivatedTrue();

        return adminPlace.stream()
            .map(course -> {
                Image img = imageRepository.findFirstByEditorCourseId(course).orElse(null);

                String imageUrl = (img != null)
                    ? imageAccessPath + img.getRenameFileName() // ✅ 설정된 웹 경로 + 파일명
                    : imageAccessPath + "bg_night.jpg";         // 기본 이미지도 동일하게

                return new EditorCourseDto(course, imageUrl);
            })
            .toList();
    }

    @Transactional
    public void adminRecommandDelete(Long recommandId) {

        EditorCourse editorCourse = adminCourseRepository.findById(recommandId)
            .orElseThrow(()->new EntityNotFoundException("해당 코스를 찾을수 없습니다"));
        editorCourse.setActivated(false);

    }
}
