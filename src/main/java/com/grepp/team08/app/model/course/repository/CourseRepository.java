package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.ApprovedCourses;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<ApprovedCourses,String> {

  List<ApprovedCourses> findRandom4();
}
