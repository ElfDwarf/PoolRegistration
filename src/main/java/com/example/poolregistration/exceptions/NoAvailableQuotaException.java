package com.example.poolregistration.exceptions;

public class NoAvailableQuotaException extends RuntimeException {
    public NoAvailableQuotaException() {
        super();
    }

    public NoAvailableQuotaException(String message) {
        super(message);
    }
}
