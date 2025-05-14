package com.grepp.team08.app.model.member.repository;

import com.grepp.team08.app.model.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<Member> findByUserId(String userId);
}
