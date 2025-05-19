package com.grepp.team08.app.model.like.repository;

import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.like.entity.FavoriteCourse;
import com.grepp.team08.app.model.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteCourse,Long> {

  Optional<FavoriteCourse> findByMemberAndRecommendCourse(Member member, RecommendCourse recommendCourse);

  Optional<FavoriteCourse> findByMemberAndEditorCourse(Member member, EditorCourse editorCourse);

  boolean existsByMemberAndRecommendCourseAndActivatedTrue(Member member, RecommendCourse recommendCourse);

  boolean existsByMemberAndEditorCourseAndActivatedTrue(Member member, EditorCourse editorCourse);
}
