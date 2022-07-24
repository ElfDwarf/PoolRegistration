package com.example.poolregistration.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExceptionResponse {

    @Schema(description = "Сообщение об ошибке")
    private final String message;

    @Schema(description = "Время получения ошибки")
    private final String timestamp;

    public ExceptionResponse(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
