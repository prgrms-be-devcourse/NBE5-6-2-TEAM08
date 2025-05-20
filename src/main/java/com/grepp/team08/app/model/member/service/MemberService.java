package com.grepp.team08.app.model.member.service;

import com.grepp.team08.app.controller.web.member.payload.MemberUpdateRequest;
import com.grepp.team08.app.model.auth.code.Role;
import com.grepp.team08.app.model.course.dto.MyCourseResponse;
import com.grepp.team08.app.model.course.entity.Course;
import com.grepp.team08.app.model.course.repository.MyCourseRepository;
import com.grepp.team08.app.model.member.dto.MemberDto;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.repository.MemberRepository;
import com.grepp.team08.infra.error.CommonException;
import com.grepp.team08.infra.response.ResponseCode;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MyCourseRepository myCourseRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberDto dto, Role role) {
        log.info("회원가입 DTO: {}", dto);

        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new CommonException(ResponseCode.BAD_REQUEST, "이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new CommonException(ResponseCode.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new CommonException(ResponseCode.BAD_REQUEST, "이미 사용 중인 닉네임입니다.");
        }

        Member member = mapper.map(dto, Member.class);

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        member.setPassword(encodedPassword);
        member.setRole(role);

        memberRepository.save(member);

    }

    @Transactional
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }

    @Transactional
    public void updateMember(String userId, MemberUpdateRequest request) {
        Member member = findByUserId(userId);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        member.setPhone(request.getPhone());
        member.setEmail(request.getEmail());
        member.setNickname(request.getNickname());

        log.info("변경된 회원 정보: {}", member);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<MyCourseResponse> findMyCourses(Member member) {
        log.info("🛠 [CourseService] member id: {}", member.getId());

        List<Course> courses = myCourseRepository.findById(member);
        log.info("🛠 [CourseService] Course 조회 결과: {}개", courses.size());

        return courses.stream()
            .map(c -> new MyCourseResponse(c.getCoursesId(), c.getTitle()))
            .collect(Collectors.toList());
    }

    @Transactional
  public String getNicknameByUserId(String name) {
      return memberRepository.findByUserId(name)
          .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."))
          .getNickname();
  }

    @Transactional
    public void deactivateMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        member.unActivated();
        member.setLeaved(true);
        member.setLeavedAt(LocalDateTime.now());
        memberRepository.save(member);
    }
}
