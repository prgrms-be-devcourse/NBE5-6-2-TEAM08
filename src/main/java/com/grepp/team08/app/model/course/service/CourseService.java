package com.grepp.team08.app.model.course.service;

import com.grepp.team08.app.model.course.dto.MyDateCourseDto;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.course.repository.RecommendCourseRepository;
import com.grepp.team08.app.model.course.repository.RegistMyCourseRepository;
import com.grepp.team08.app.model.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.grepp.team08.app.model.course.repository.MyCourseRepository;
import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import com.grepp.team08.app.model.place.entity.Place;
import com.grepp.team08.app.model.place.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CourseService {

    private final MyCourseRepository myCourseRepository;
    private final PlaceRepository placeRepository;
    private final RegistMyCourseRepository courseRepository;
    private final RecommendCourseRepository recommendCourseRepository;

    public void saveCourse(MyDateCourseDto dto, Member member) {
        // Course 저장
        Course course = new Course();
        course.setTitle(dto.title());
        course.setDescription(dto.description());
        course.setId(member);
        myCourseRepository.save(course);

        // Place 저장
        for (PlaceSaveDto placeDto : dto.places()) {
            Place place = new Place();
            place.setCourseId(course);
            place.setPlaceName(placeDto.placeName());
            place.setAddress(placeDto.address());
            place.setPlaceUrl("");
            place.setLatitude(0);
            place.setLongitude(0);

            placeRepository.save(place);
        }
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
    }

    public List<Course> getCoursesByMember(Member member) {
        return courseRepository.findAllByIdOrderByCreatedAtDesc(member);
    }

    public void registToRecommendCourse(Long courseId) {
        Course course = getCourseById(courseId);

        // 이미 추천 코스로 등록되어 있는지 확인
        if (recommendCourseRepository.existsByCourseId(course)) {
            throw new IllegalStateException("이미 추천 코스로 등록되어 있습니다.");
        }

        RecommendCourse recommendCourse = new RecommendCourse();
        recommendCourse.setCourseId(course);
        recommendCourseRepository.save(recommendCourse);
    }
}