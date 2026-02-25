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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.junit.test.entity.Order;
import com.junit.test.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("성공 : API를 통한 주문 취소 (200 OK)")
    void cancelOrder_Api_Success() throws Exception {
        // given
        Long orderId = 1L;
        String userEmail = "user@test.com";
        
        Order cancelledOrder = new Order(userEmail, "CANCELLED");
        // 엔티티에 id 생성자가 없으므로 Reflection을 사용하여 ID 주입 (테스트 목적)
        ReflectionTestUtils.setField(cancelledOrder, "id", orderId);

        given(orderService.cancelOrder(orderId, userEmail)).willReturn(cancelledOrder);

        // when & then
        mockMvc.perform(post("/api/orders/" + orderId + "/cancel")
                .param("userEmail", userEmail))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("주문이 취소되었습니다."));
    }

    @Test
    @DisplayName("실패 : 본인 주문이 아닌 경우 (400 Bad Request)")
    void cancelOrder_Api_Fail_NotOwner() throws Exception {
        // given
        Long orderId = 1L;
        String otherEmail = "other@test.com";

        given(orderService.cancelOrder(orderId, otherEmail))
                .willThrow(new IllegalStateException("본인 주문만 취소 가능합니다."));

        // when & then
        mockMvc.perform(post("/api/orders/" + orderId + "/cancel")
                .param("userEmail", otherEmail))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("본인 주문만 취소 가능합니다."));
    }
}