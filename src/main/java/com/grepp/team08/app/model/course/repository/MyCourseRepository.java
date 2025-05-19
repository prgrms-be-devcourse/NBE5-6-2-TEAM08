package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCourseRepository extends JpaRepository<Course, Long> {

    List<Course> findById(Member id);

}
