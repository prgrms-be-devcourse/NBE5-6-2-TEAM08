package com.grepp.team08.app.model.review.repository;

import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

  int countByRecommendCourseIdAndActivatedTrue(RecommendCourse course);

  List<Review> findAllByRecommendCourseIdAndActivatedTrue(RecommendCourse recommendCourse);
}
