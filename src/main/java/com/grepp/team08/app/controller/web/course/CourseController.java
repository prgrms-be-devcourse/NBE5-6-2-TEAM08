package com.grepp.team08.app.controller.web.course;

import com.grepp.team08.app.model.like.entity.FavoriteCourse;
import com.grepp.team08.app.model.like.service.FavoriteService;
import com.grepp.team08.app.model.member.service.MemberService;
import java.security.Principal;
import org.bouncycastle.math.raw.Mod;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.repository.CourseRepository;

import com.grepp.team08.app.model.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final FavoriteService favoriteService;
    private final MemberService memberService;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    // 코스 구성 페이지 이동 + 카카오 api key 전달
    @GetMapping("/course-composition")
    public String courseComposition(@RequestParam(required = false) String mood, Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        return "course_composition";
    }

    // 에디터픽 코스 목록 페이지 (일반 사용자용)
    // html 이름 직관적으로 수정 필요 > editor_courses
    @GetMapping("/editor-recommend-courses")
    public String editorCourse(){ return "course_list";}

    // 추천(사용자픽) 코스 목록 페이지 > html 이름 직관적으로 수정 (recommend_courses ?)
    @GetMapping("/recommend-courses")
    public String usercourse(){return "course_list_user";}

    // 추천 코스 상세정보 페이지
    // html 이름 직관적, 통일성 있게 수정 > recommend_course_detail ?
    @GetMapping("/recommend-courses/{recommend_id}")
    public String detailUserCourse(@PathVariable Long recommend_id, Principal principal, Model model) {
        model.addAttribute("recommendId", recommend_id);
        boolean isLiked = false;
        if (principal != null) {
            String userId = principal.getName();
            isLiked = favoriteService.isLiked(userId, recommend_id); // 찜 여부 확인
        }
        String nickname = memberService.getNicknameByUserId(principal.getName());
        model.addAttribute("loginNickname", nickname);
        model.addAttribute("isLiked", isLiked);
        return "course_detail";
    }

    // 에디터픽 코스 상세정보 페이지
    // html 이름 통일성을 위해 editor_course_detail 로 수정
    // 엔드포인트도 너무 길어 그냥 editor-courses/ 로 ㄱㄱ
    @GetMapping("/editor-recommend-courses/{recommendId}")
    public String detailEditorCourse(@PathVariable Long recommendId,Principal principal, Model model){
        model.addAttribute("recommendId",recommendId);
        boolean isLiked = false;
        if(principal != null){
            String userId = principal.getName();
            isLiked = favoriteService.isEditorLiked(userId,recommendId);
        }

        model.addAttribute("isLiked",isLiked);
        return"editor_pick_detail";
    }

// 이거 뭐냐? 지워도 되는거 아녀?
//    // 나의 데이트 코스 저장 페이지 이동
//    @GetMapping("/make-mycourses")
//    public String makeMyCourses() {
//        return "make_mycourses";
//    }
//
//    // 내 데이트 코스에 저장 post 요청
//    @PostMapping("/make-mycourses")
//    public String regist(Course course) {
////        courseService.registCourses(course.getTitle());
//        return null;
//    }
}
