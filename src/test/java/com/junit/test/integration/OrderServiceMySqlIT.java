package com.junit.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.junit.test.entity.Order;
import com.junit.test.repository.OrderRepository;
import com.junit.test.service.OrderService;

    @SpringBootTest
    @ActiveProfiles("test")
    @Transactional
    

public class OrderServiceMySqlIT {

 @Autowired
 private OrderService orderService;

 @Autowired
 private OrderRepository orderRepository;

 @Test
 @DisplayName("成功 : 注文キャンセル時ステータスがCANCELLEDで変更されるべき")
 void cancelOrder_Success() {
    
    String email = "pjh@test.com";
    Order order = orderRepository.save(new Order(null, email, "ORDERED"));

    Order cancelledOrder = orderService.cancelOrder(order.getId(), email);

    assertEquals("CANCELLED", cancelledOrder.getStatus());
    Order founOrder = orderRepository.findById(order.getId()).get();
    assertEquals("CANCELLED", founOrder.getStatus());  
 }

 @Test
 @DisplayName("失敗 : 他人の注文をキャンセルしようとしたら例外発生")
 void cancelOrder_Fail_NotOwner(){

    Order order = orderRepository.save(new Order(null, "other@test.com", "ORDERED"));
    assertThrows(IllegalStateException.class, () -> {
        orderService.cancelOrder(order.getId(), "pjh@test.com");
    });
 }
}