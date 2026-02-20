package com.junit.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.junit.test.entity.Order;

import lombok.RequiredArgsConstructor;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {
    
    @Autowired
    OrderRepository orderRepository;
  
    @Test
    @DisplayName("주문 저장 후 ID로 조회")
    void find_by_user_email(){
  
        Order order = new Order(1L, "user@test.com", "ORDERED");
        Order saveOrder = orderRepository.save(order);

        Optional<Order> found = orderRepository.findById(save(order));

        assertTrue(found.isPresent());
        assertEquals("user@test.com",found.get().getUserEmail());
        assertEquals("ORDERED",found.get().getStatus());
    }

     @Test
    @DisplayName("이메일로 주문 조회")

    void find_By_user_email(){
        orderRepository.save(new Order(1L, "pjh@test.com", "ORDERED"));
        orderRepository.save(new Order(2L,"lth@test.com","CANCELLED"));

        List<Order> results = orderRepository.findByUserEmail("pjh@test.com");

        assertEquals(2  , results.size());
        assertTrue(results.stream().allMatch(o -> o.getUserEmail().equals("pjh@test.com")));

    }

     @Test
    @DisplayName("이메일 존재 여부 확인")

    void exists_By_User_email(){

        String targetEmail = "check@test.com";
        orderRepository.save(new Order(null, targetEmail, "ORDERED"));

        boolean exists = orderRepository.existsByUserEmail(targetEmail);
        boolean notExists = orderRepository.existsByUserEmail("none@test.com");

        assertTrue(exists);
        assertFalse(notExists);
    }
    
}
