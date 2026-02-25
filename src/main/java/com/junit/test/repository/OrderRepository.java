package com.junit.test.repository;

import com.junit.test.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}