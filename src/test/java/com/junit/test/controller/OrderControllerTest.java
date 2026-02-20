package com.junit.test.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; 
import org.springframework.boot.test.mock.mockito.MockBean; 
import org.springframework.test.web.servlet.MockMvc;

import com.junit.test.entity.Order;
import com.junit.test.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc; 

    @MockBean
    OrderService orderService; 

    @Test
    @DisplayName("成功 : APIを通じた注文キャンセル (200 OK)")
    void cancelOrder_Api_Success() throws Exception {
        Order cancelledOrder = new Order(1L, "user@test.com", "CANCELLED");
        given(orderService.cancelOrder(1L, "user@test.com")).willReturn(cancelledOrder);

        mockMvc.perform(post("/api/orders/1/cancel")
                .param("userEmail", "user@test.com"))
                .andDo(print()) 
                .andExpect(status().isOk()) 
                .andExpect(content().string("注文がキャンセルされました。")); 
    }

    @Test
    @DisplayName("失敗 : 権限なし (400 Bad Request)")
    void cancelOrder_Api_Fail_NotOwner() throws Exception {
       
        given(orderService.cancelOrder(1L, "other@test.com"))
                .willThrow(new IllegalStateException("본인 주문만 취소 가능"));

        mockMvc.perform(post("/api/orders/1/cancel")
                .param("userEmail", "other@test.com"))
                .andDo(print())
                .andExpect(status().isBadRequest()) 
                .andExpect(content().string("본인 주문만 취소 가능"));
    }
}