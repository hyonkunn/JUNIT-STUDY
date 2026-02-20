package com.junit.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.test.entity.Order;
import com.junit.test.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    @DisplayName("成功 : 注文キャンセル正常完了")
    void cancelOrder_Success() {
        // given
        Order order = new Order(1L, "user@test.com", "CREATED");
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));
        given(orderRepository.save(any(Order.class))).willAnswer(i -> i.getArgument(0));

        // when
        Order result = orderService.cancelOrder(1L, "user@test.com");

        // then
        assertEquals("CANCELLED", result.getStatus());
        then(orderRepository).should().save(any(Order.class));
    }

    @Test
    @DisplayName("失敗 : 本人の注文のみキャンセル可能")
    void cancelOrder_Fail_NotOwner() {
        Order order = new Order(1L, "user@test.com", "CREATED");
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> orderService.cancelOrder(1L, "otheruser@test.com"));

        assertThat(exception).hasMessage("본인 주문만 취소 가능");
        then(orderRepository).should().findById(1L);
        then(orderRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("失敗 : 存在しない注文")
    void cancelOrder_Fail_OrderNotFound() {
        given(orderRepository.findById(1L)).willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> orderService.cancelOrder(1L, "user@test.com"));

        assertThat(exception).hasMessage("주문 없음");
        then(orderRepository).should().findById(1L);
        then(orderRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("失敗 : 既にキャンセルされた注文")
    void cancelOrder_Fail_AlreadyCancelled() {
        Order order = new Order(1L, "user@test.com", "CANCELLED");
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> orderService.cancelOrder(1L, "user@test.com"));

        assertThat(exception).hasMessage("이미 취소된 주문");
        then(orderRepository).should().findById(1L);
        then(orderRepository).should(never()).save(any());
    }
}