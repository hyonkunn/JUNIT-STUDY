package com.junit.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.junit.test.entity.Member;


@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @Test
    @DisplayName("회원 저장 후 조회")
    void save_and_find() {

        // given
        Member member = new Member(
                null,
                "태훈",
                "test@test.com",
                "1234",
                "USER"
        );

        // when
        Member saved = repository.save(member);

        Optional<Member> found =
                repository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("test@test.com",
                found.get().getEmail());
    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    void exists_by_email() {

        Member member = new Member(
                null,
                "태훈",
                "exist@test.com",
                "1234",
                "USER"
        );

        repository.save(member);

        boolean exists =
                repository.existsByEmail("exist@test.com");

        assertTrue(exists);
        assertFalse(repository.existsByEmail("no@test.com"));
    }
}
