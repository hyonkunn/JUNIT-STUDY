package  com.junit.test.integration;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.junit.test.entity.Member;
import com.junit.test.repository.MemberRepository;

import com.junit.test.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // 애플리케이션에 진짜로 올려서 Bean/DB/JPA 설정까지 포함한 테스트
@ActiveProfiles("test") // 테스트 실행시 application-test.propertis
@Transactional //각 테스트 메서드가 끝나면 자동 롤백(db에 데이터가 저장되지 않음)
public class memberServiceMySqlIT {

@Autowired
MemberService service;

@Autowired
MemberRepository repository;

@Autowired
PasswordEncoder encoder;

@Test
@DisplayName("회원 등록 성공 + 암호화 검증")
void register_success_realDB(){

Member saved = service.register("joohyun", "test&test.com", "1234");    assertNotNull(saved.getId()); // 저장 성공시 DB에 it가 생성 되므로 null체크
    assertEquals("test&test.com",saved.getEmail());
    assertEquals("USER", saved.getRole());

    //암호화 되었는지 검증
    assertTrue(encoder.matches("1234",saved.getPassword()));


    assertTrue(repository.existsByEmail("test&test.com"));
}

@Test
@DisplayName("이메일 중복 예외")
void register_duplicate_emailrealdb(){

    service.register("joohyun", "test2＠test.com", "1234"); //이미 등록되어 있는 이메일이 세팅이 되어 있어야 검증 가능
    
    assertThrows(IllegalStateException.class,() ->service.register("다른 유저", "test2＠test.com", "9999"));
    //같은 이메일로 다시 register 호출 -> existsByEmail=true - > 예외 발생 기대
}
}