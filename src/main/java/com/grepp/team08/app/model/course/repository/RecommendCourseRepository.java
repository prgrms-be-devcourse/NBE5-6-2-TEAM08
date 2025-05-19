package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendCourseRepository extends JpaRepository<RecommendCourse, Long> {
    boolean existsByCourseId(Course course);
    Optional<RecommendCourse> findByCourseId(Course course);
}
