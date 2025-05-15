package com.grepp.team08.app.model.course.service;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.repository.MyCourseRepository;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.place.dto.PlaceSaveDto;
import com.grepp.team08.app.model.place.entity.Place;
import com.grepp.team08.app.model.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final MyCourseRepository myCourseRepository;
    private final PlaceRepository placeRepository;

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
}
