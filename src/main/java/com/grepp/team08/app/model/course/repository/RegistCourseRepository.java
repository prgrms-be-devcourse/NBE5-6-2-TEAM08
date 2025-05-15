package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistCourseRepository extends JpaRepository<Course, Long> {

}
