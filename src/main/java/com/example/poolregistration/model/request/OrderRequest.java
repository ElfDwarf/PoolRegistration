package com.example.poolregistration.model.request;


public class OrderRequest {
    private final long clientId;
    private final String datetime;

    public OrderRequest(long clientId, String datetime) {
        this.clientId = clientId;
        this.datetime = datetime;
    }

    public long getClientId() {
        return clientId;
    }

    public String getDatetime() {
        return datetime;
    }
}
