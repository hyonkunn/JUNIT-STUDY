package com.junit.test.entity;

public class Coupon {

    private final String code;
    private final double discountRate;
    private final boolean active;

    // 생성자에서 파라미터를 받도록 수정해야 합니다.
    public Coupon(String code, double discountRate, boolean active) {
        this.code = code;
        this.discountRate = discountRate;
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public boolean isActive() {
        return active;
    }
}