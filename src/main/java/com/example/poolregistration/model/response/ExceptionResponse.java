package com.example.poolregistration.model.response;

public class ExceptionResponse {
    private final String message;
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
