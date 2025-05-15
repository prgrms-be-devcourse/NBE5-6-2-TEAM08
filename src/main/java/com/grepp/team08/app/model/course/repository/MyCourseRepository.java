package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCourseRepository extends JpaRepository<Course, Long> {

}
