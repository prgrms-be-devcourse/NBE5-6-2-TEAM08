package com.grepp.team08.app.model.course.repository;

import com.grepp.team08.app.model.course.entity.FavoriteCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<RecommendCourse,Long> {

  List<RecommendCourse> findTop4ByOrderByCreatedAtDesc();
    // 코스 저장
    static void save(){}
}
