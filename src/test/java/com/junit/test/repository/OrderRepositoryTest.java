package com.junit.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List; // 반드시 java.util.List 확인!
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat; // 이게 없어서 assertThat에 빨간 줄이 생깁니다.

import com.junit.test.entity.Order;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 저장 후 ID로 조회")
    void save_and_findById() { 
        
        Order order = new Order(null, "user@test.com", "ORDERED");
        Order savedOrder = orderRepository.save(order);

      
        Optional<Order> found = orderRepository.findById(savedOrder.getId());

        assertTrue(found.isPresent());
        assertEquals("user@test.com", found.get().getUserEmail());
        assertEquals("ORDERED", found.get().getStatus());
    }

    @Test
    @DisplayName("이메일로 주문 조회 - 동일 이메일 2건 저장 후 검증")
    void find_by_user_email() {
        String targetEmail = "pjh@test.com";
        orderRepository.save(new Order(null, targetEmail, "ORDERED"));
        orderRepository.save(new Order(null, targetEmail, "CANCELLED"));

        List<Order> results = orderRepository.findByUserEmail(targetEmail);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(o -> o.getUserEmail().equals(targetEmail)));
    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    void exists_By_User_email() {
        String targetEmail = "check@test.com";
        orderRepository.save(new Order(null, targetEmail, "ORDERED"));

        boolean exists = orderRepository.existsByUserEmail(targetEmail);
        boolean notExists = orderRepository.existsByUserEmail("none@test.com");

        assertTrue(exists);
        assertFalse(notExists);
    }
}