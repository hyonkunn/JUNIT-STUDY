package com.junit.test.repository;

import com.junit.test.entity.Order;

public interface OrderRepository {


    java.util.Optional<Order> findById(Long id);

    Order save(Order order);
}
