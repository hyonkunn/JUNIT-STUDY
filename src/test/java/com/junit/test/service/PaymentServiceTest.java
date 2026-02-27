package com.junit.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.junit.test.domain.Purchase;
import com.junit.test.entity.Coupon;

public class PaymentServiceTest {

    private final PaymentService paymentService = new PaymentService();

    // クーポンないと割引０％
    @Test
    void couponNullTest() {
        Purchase purchase = new Purchase("pjh@weavus.com", 1, 10000);
        Coupon noCoupon = null;

        PaymentService.Result result = paymentService.calculateFinalPrice(purchase, noCoupon);

        assertEquals(0.0, result.getDiscountRate()); 
        assertEquals(10000, result.getFinalAmount());
    }

    // クーポンがあるとクーポン適用
    @Test
    void couponApplyTest() {
        Purchase purchase = new Purchase("pjh@weavus.com", 1, 10000);
        Coupon activeCoupon = new Coupon("WELCOME10", 0.1, true);

        PaymentService.Result result = paymentService.calculateFinalPrice(purchase, activeCoupon);

        assertEquals(1000.0, result.getDiscountRate());
        assertEquals(9000, result.getFinalAmount());
    }
    
    ///비활성 쿠폰은 적용 안됌
    @Test
    void inactiveCouponTest() {
        Purchase purchase = new Purchase("pjh@weavus.com", 1, 20000);
        Coupon inactiveCoupon = new Coupon("SALE50", 0.5, false);

        PaymentService.Result result = paymentService.calculateFinalPrice(purchase, inactiveCoupon);

        assertEquals(0.0, result.getDiscountRate());
        assertEquals(20000, result.getFinalAmount());
    }

}