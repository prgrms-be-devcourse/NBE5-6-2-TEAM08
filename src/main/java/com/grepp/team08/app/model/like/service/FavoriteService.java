package com.grepp.team08.app.model.like.service;

import com.grepp.team08.app.model.course.entity.EditorCourse;
import com.grepp.team08.app.model.course.entity.RecommendCourse;
import com.grepp.team08.app.model.course.repository.AdminCourseRepository;
import com.grepp.team08.app.model.course.repository.CourseRepository;
import com.grepp.team08.app.model.like.entity.FavoriteCourse;
import com.grepp.team08.app.model.like.repository.FavoriteRepository;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final MemberRepository memberRepository;
  private final CourseRepository courseRepository;
  private final AdminCourseRepository adminCourseRepository;

  @Transactional
  public void userFavoriteAdd(Long recommendId, String userId) {

    Member member = memberRepository.findByUserId(userId)
        .orElseThrow(()-> new EntityNotFoundException("로그인을 해주세요"));

    RecommendCourse recommendCourse = courseRepository.findById(recommendId)
        .orElseThrow(()-> new EntityNotFoundException("해당 코스가 존재하지 않습니다"));


    Optional<FavoriteCourse> favorite = favoriteRepository.findByMemberAndRecommendCourse(member, recommendCourse);

    if(favorite.isPresent()){
      FavoriteCourse favoriteCourse = favorite.get();
      favoriteCourse.setActivated(!favoriteCourse.getActivated());
      favoriteCourse.setModifiedAt(LocalDateTime.now());
    }
    else{
      FavoriteCourse favoriteCourse = FavoriteCourse.builder()
          .member(member)
          .recommendCourse(recommendCourse)
          .build();

      favoriteRepository.save(favoriteCourse);
    }



  }

  @Transactional
  public void adminFavoriteAdd(Long editorCourseId, String userId) {

    Member member = memberRepository.findByUserId(userId)
        .orElseThrow(()-> new EntityNotFoundException("로그인을 해주세요"));

    EditorCourse editorCourse = adminCourseRepository.findById(editorCourseId)
        .orElseThrow(()-> new EntityNotFoundException("해당 관리자 코스가 없습니다"));

    Optional<FavoriteCourse> favoriteeditor = favoriteRepository.findByMemberAndEditorCourse(member, editorCourse);

    if(favoriteeditor.isPresent()){
      FavoriteCourse favoriteCourse = favoriteeditor.get();
      favoriteCourse.setActivated(!favoriteCourse.getActivated());
      favoriteCourse.setModifiedAt(LocalDateTime.now());
    }
    else{
      FavoriteCourse favoriteCourse = FavoriteCourse.builder()
          .editorCourse(editorCourse)
          .member(member)
          .build();

      favoriteRepository.save(favoriteCourse);

    }



  }

  public boolean isLiked(String userId, Long recommendId) {
    Member member = memberRepository.findByUserId(userId)
        .orElseThrow(()-> new EntityNotFoundException("로그인을 해주세요"));

    RecommendCourse recommendCourse = courseRepository.findById(recommendId)
        .orElseThrow(()-> new EntityNotFoundException("해당 코스가 존재하지 않습니다"));

    return favoriteRepository.existsByMemberAndRecommendCourseAndActivatedTrue(member,recommendCourse);
  }

  public boolean isEditorLiked(String userId, Long recommendId) {
    Member member = memberRepository.findByUserId(userId)
        .orElseThrow(()-> new EntityNotFoundException("로그인을 해주세요"));

    EditorCourse editorCourse = adminCourseRepository.findById(recommendId)
        .orElseThrow(()-> new EntityNotFoundException("해당 관리자 코스가 없습니다"));

    return favoriteRepository.existsByMemberAndEditorCourseAndActivatedTrue(member,editorCourse);
  }
}
