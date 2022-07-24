package com.example.poolregistration.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderIdResponse {

    @Schema(description = "ID заказа")
    private final String orderId;
    public OrderIdResponse(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
