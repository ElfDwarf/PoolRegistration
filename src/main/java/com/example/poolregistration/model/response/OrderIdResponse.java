package com.example.poolregistration.model.response;

public class OrderIdResponse {
    private final String orderId;
    public OrderIdResponse(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
