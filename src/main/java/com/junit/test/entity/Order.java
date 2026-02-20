package com.junit.test.entity;

public class Order {
    
    private Long id;
    private String userEmail;
    private String status; // CREATED, CANCELLED

    public Order(Long orderId, String userEmail, String status) {
        this.id = orderId;
        this.userEmail = userEmail;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void cancel() {
        this.status = "CANCELLED";
    }
}
