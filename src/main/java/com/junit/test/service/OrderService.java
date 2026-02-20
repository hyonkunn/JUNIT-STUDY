package com.junit.test.service;

import com.junit.test.entity.Order;
import com.junit.test.repository.OrderRepository;
import org.springframework.stereotype.Service; 

@Service

public class OrderService {
      private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order cancelOrder(Long orderId, String requestUserEmail) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));

        if (!order.getUserEmail().equals(requestUserEmail)) {
            throw new IllegalStateException("본인 주문만 취소 가능");
        }

        if ("CANCELLED".equals(order.getStatus())) {
            throw new IllegalStateException("이미 취소된 주문");
        }

        order.cancel();

        return repository.save(order);

    
    }
}
