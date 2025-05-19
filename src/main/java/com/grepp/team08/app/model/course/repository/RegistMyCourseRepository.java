package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistMyCourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIdOrderByCreatedAtDesc(Member member);
}