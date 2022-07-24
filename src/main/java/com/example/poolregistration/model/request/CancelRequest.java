package com.example.poolregistration.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class CancelRequest {
    @Schema(description = "ID клиента", defaultValue = "1", required = true)
    private long clientId;
    @Schema(description = "ID заказа(гуид)", required = true)
    private String orderId;

    public CancelRequest() {
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
