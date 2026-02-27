package com.junit.test.domain;

public class Purchase {

    private final String buyerEmail;
    private final int quantity;
    private final int unitPrice;


    public Purchase(String buyerEmail, int quantity, int unitPrice){
        this.buyerEmail = buyerEmail;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getBuyerEmail(){
        return buyerEmail;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getUnitPrice(){
        return unitPrice;
        
    }

    public int getSubtotal(){
        return quantity*unitPrice;
    }

}
