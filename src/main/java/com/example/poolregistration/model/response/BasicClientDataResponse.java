package com.example.poolregistration.model.response;

public class BasicClientDataResponse {
    private final Long id;
    private final String name;

    public BasicClientDataResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
