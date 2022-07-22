package com.example.poolregistration.model.response;

public class OrdersByDateResponse {
    private final String time;
    private final String count;

    public OrdersByDateResponse(String time, String count) {
        this.time = time;
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public String getCount() {
        return count;
    }
}
