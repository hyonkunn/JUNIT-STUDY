package com.junit.test.service;

import com.junit.test.entity.Member;
import com.junit.test.repository.MemberRepository;
import com.junit.test.repository.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    
    @Mock MemberRepository repository;
    @Mock PasswordEncoder encoder;
    @InjectMocks MemberService service;

    @Test
    @DisplayName("회원가입 완료")
    void register_success(){
        given(repository.existsByEmail("test@example.com")).willReturn(false);
        given(encoder.encode("1234")).willReturn("encoded1234");
        given(repository.save(any())).willAnswer(inv -> inv.getArgument(0));
        
        Member result = service.register("jh", "test@example.com", "1234");
    
        assertEquals("USER", result.getRole());
        assertEquals("encoded1234", result.getPassword()); // s 세 개 오타 수정
        then(repository).should().save(any());
    }

    @Test
    @DisplayName("저장 객체 값 검증")
    void register_argument_captor(){
        given(repository.existsByEmail(any())).willReturn(false);
        given(encoder.encode(any())).willReturn("encoded");
        given(repository.save(any())).willAnswer(inv->inv.getArgument(0));
        
        service.register("jh","test@example.com", "1234");
        
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        then(repository).should().save(any());
        

        Member captured = captor.getValue();
        assertEquals("USER", captured.getRole());
        assertEquals("encoded", captured.getPassword()); // 오타 수정
        assertEquals("test@example.com", captured.getEmail());
    }

    @Test
    @DisplayName("이메일 중복 예외")
    void register_duplicate_email(){
        given(repository.existsByEmail("test@example.com")).willReturn(true);

        assertThrows(IllegalStateException.class, () -> 
            service.register("jh", "test@example.com", "1234")
        );
        then(repository).should(never()).save(any()); 
    }
}