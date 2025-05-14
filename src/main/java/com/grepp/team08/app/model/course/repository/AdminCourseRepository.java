package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.EditorCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCourseRepository extends JpaRepository<EditorCourse,Long> {


  List<EditorCourse> findTop4ByOrderByCreatedAtDesc();

}
