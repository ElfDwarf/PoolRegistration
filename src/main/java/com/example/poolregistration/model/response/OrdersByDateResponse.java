package com.example.poolregistration.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrdersByDateResponse {

    @Schema(description = "Время")
    private final String time;

    @Schema(description = "Количество мест")
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
