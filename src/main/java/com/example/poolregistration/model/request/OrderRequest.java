package com.example.poolregistration.model.request;


public class OrderRequest {
    private final long clientId;
    private final String datetime;

    private final long duration;

    public OrderRequest(long clientId, String datetime) {
        this.clientId = clientId;
        this.datetime = datetime;
        this.duration = 1;
    }

    public OrderRequest(long clientId, String datetime, long duration) {
        this.clientId = clientId;
        this.datetime = datetime;
        this.duration = duration;
    }

    public long getClientId() {
        return clientId;
    }

    public String getDatetime() {
        return datetime;
    }

    public long getDuration() {
        return duration;
    }
}
