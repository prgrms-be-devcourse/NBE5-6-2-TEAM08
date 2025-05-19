package com.grepp.team08.app.model.review.repository;

import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

  @Query("SELECT r FROM Review r JOIN FETCH r.id WHERE r.recommendCourseId = :recommendCourse")
  List<Review> findAllByRecommendCourseId(RecommendCourse recommendCourse);
}
