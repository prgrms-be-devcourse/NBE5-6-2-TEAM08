package com.grepp.team08.app.model.member.service;

import com.grepp.team08.app.model.auth.code.Role;
import com.grepp.team08.app.model.member.dto.MemberDto;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.repository.MemberRepository;
import com.grepp.team08.infra.error.CommonException;
import com.grepp.team08.infra.response.ResponseCode;
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


}
