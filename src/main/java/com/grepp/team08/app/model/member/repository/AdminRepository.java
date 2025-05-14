package com.grepp.team08.app.model.member.repository;

import com.grepp.team08.app.model.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Member,Integer> {

}
