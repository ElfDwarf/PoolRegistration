package com.example.poolregistration.model.request;

public class CancelRequest {
    private final long clientId;
    private final String orderId;

    public CancelRequest(long clientId, String orderId) {
        this.clientId = clientId;
        this.orderId = orderId;
    }

    public long getClientId() {
        return clientId;
    }

    public String getOrderId() {
        return orderId;
    }
}
