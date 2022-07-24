package com.example.poolregistration.model.dao;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity(name = "client")
public class PoolClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID клиента")
    private Long id;

    @Schema(description = "ФИО клиента")
    private String name;

    @Schema(description = "Номер телефона клиента")
    private String phone;

    @Schema(description = "E-mail клиента")
    private String email;

    public PoolClient() {
    }

    public PoolClient(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
