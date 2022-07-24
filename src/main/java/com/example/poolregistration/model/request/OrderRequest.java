package com.example.poolregistration.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;

public class OrderRequest {
    @Schema(description = "ID клиента", defaultValue = "1", required = true)
    private long clientId;

    @Schema(description = "Дата и время посещения формата yyyy-MM-dd hh:mm", defaultValue = "2022-07-24 15:00", required = true)
    private String datetime;

    @Schema(description = "Длительность посещения", defaultValue = "1")
    @Min(value = 1)
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
