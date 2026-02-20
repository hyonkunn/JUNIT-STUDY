package com.junit.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.junit.test.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(
            @PathVariable(name = "orderId") Long orderId, // name 명시 필수
            @RequestParam(name = "userEmail") String userEmail) { // name 명시 필수

        orderService.cancelOrder(orderId, userEmail);
        return ResponseEntity.ok("注文がキャンセルされました。");
    }
}