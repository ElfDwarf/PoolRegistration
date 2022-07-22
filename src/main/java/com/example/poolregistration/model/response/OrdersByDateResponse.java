package com.example.poolregistration.model.response;

public class OrdersByDateResponse {
    private final String time;
    private final int count;

    public OrdersByDateResponse(String time, int count) {
        this.time = time;
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public int getCount() {
        return count;
    }
}
