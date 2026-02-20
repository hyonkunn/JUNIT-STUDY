package com.junit.test.service;

import com.junit.test.entity.Member;
import com.junit.test.repository.MemberRepository;
import com.junit.test.repository.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    public Member register(String name, String email, String password) {
        // 1. 이름 공백 검증
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("名前が空입니다.");
        }
        
        // 2. 이메일 중복 검증
        if (repository.existsByEmail(email)) {
            throw new IllegalStateException("重複したメールです.");
        }

        // 3. 비밀번호 암호화 및 저장
        String encodedPassword = encoder.encode(password);
        
        // ID는 DB에서 자동 생성되도록 null을 전달합니다.
        Member member = new Member(null, name, email, encodedPassword, "USER");
        
        // (Member) 형변환은 필요 없습니다. 리포지토리가 올바르게 설정되면 자동으로 Member를 반환합니다.
        return repository.save(member);
    }
}