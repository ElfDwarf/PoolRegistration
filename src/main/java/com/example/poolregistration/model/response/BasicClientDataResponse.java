package com.example.poolregistration.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class BasicClientDataResponse {
    @Schema(description = "ID клиента")
    private final Long id;
    @Schema(description = "ФИО клиента")
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
