package com.junit.test.repository;

import com.junit.test.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository; // 필수

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}