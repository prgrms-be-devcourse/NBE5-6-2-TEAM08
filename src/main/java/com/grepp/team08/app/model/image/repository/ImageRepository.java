package com.grepp.team08.app.model.image.repository;

import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.image.entity.Image;
import com.grepp.team08.app.model.place.entity.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

  Optional<Image> findFirstByEditorCourseId(EditorCourse course);

  Optional<Image> findFirstByRecommendCourseId(RecommendCourse recommendCourseId);

  List<Image> findAllByRecommendCourseId(RecommendCourse recommendCourse);

  List<Image> findAllByEditorCourseId(EditorCourse places);
}
