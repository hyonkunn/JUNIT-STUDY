package com.junit.test.service;

import com.junit.test.domain.Purchase;
import com.junit.test.entity.Coupon;

public class PaymentService {
    
    public static class Result {

        private final int subTotal;
        private final double discountRate;
        private final int finalAmount;
        private final String grade;
        
        public Result(int subTotal, double discountRate, int finalAmount, String grade) {
            this.subTotal = subTotal;
            this.discountRate = discountRate;
            this.finalAmount = finalAmount;
            this.grade = grade;
        }

        public int getSubTotal() {
            return subTotal;
        }

        public double getDiscountRate() {
            return discountRate;
        }

        public int getFinalAmount() {
            return finalAmount;
        }

        public String getGrade() {
            return grade;
        }
    }

    // 최종 결제 금액 계산 메서드
    public Result calculateFinalPrice(Purchase purchase, Coupon coupon) {
        // 소계 계산
        int subtotal = purchase.getSubtotal();
        
        // 기본 할인율이 0%
        double discount = 0.0;

        // 쿠폰이 존재하고, 활성화 상태면 할인율 적용
        if (coupon != null && coupon.isActive()) {
            discount = subtotal * coupon.getDiscountRate();
        }
        
        // 금액 계산
        int finalAmount = (int) (subtotal - discount);
        
        String grade = finalAmount >= 100000 ? "VIP" : "Regular";

        return new Result(subtotal, discount, finalAmount, grade);
    } 

}
