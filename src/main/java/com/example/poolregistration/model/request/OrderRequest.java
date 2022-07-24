package com.example.poolregistration.model.request;


public class OrderRequest {
    private long clientId;
    private String datetime;

    private final int duration;

    public OrderRequest() {
        duration = 1;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getDuration() {
        return duration;
    }
}
