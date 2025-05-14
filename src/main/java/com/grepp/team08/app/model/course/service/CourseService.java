package com.grepp.team08.app.model.course.service;

import com.grepp.team08.app.model.course.dto.CourseDto;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void registCourses(CourseDto dto) {
        try {
            Course course = new Course();
            course.setTitle("nana");  // 확정 아님
            courseRepository.save(course);  // 저장
        } catch (Exception e) {
            throw new RuntimeException("코스 저장 중 오류 발생", e);
        }
    }
}
