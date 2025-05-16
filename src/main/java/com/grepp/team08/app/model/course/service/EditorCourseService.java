package com.grepp.team08.app.model.course.service;

import com.grepp.team08.app.model.course.dto.EditorCourseSaveDto;
import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.repository.EditorCourseRepository;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.image.repository.ImageRepository;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import com.grepp.team08.app.model.place.entity.Place;
import com.grepp.team08.app.model.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditorCourseService {

    private final EditorCourseRepository editorCourseRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;

    public void save(EditorCourseSaveDto dto, Member member) {
        // 1. 에디터 코스 저장
        EditorCourse course = new EditorCourse();
        course.setTitle(dto.title());
        course.setDescription(dto.description());
        course.setActivated(true);
        course.setMember(member);  // member 필드에 직접 설정
        editorCourseRepository.save(course);

        // 2. 장소 저장
        for (PlaceSaveDto placeDto : dto.places()) {
            Place place = new Place();
            place.setEditorCourseId(course);
            place.setPlaceName(placeDto.placeName());
            place.setAddress(placeDto.address());
            place.setLatitude(placeDto.latitude());
            place.setLongitude(placeDto.longitude());
            placeRepository.save(place);
        }

        // 3. 이미지 정보 저장 (이미 업로드된 경로를 이용함)
        for (String imagePath : dto.imageUrls()) {
            Image image = new Image();
            image.setEditorCourseId(course);
            image.setOriginFileName(extractFileName(imagePath));
            image.setRenameFileName(extractFileName(imagePath));
            image.setSavePath(imagePath);
            image.setType("image"); // 필요 시 MIME 타입 저장 가능
            imageRepository.save(image);
        }
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
}