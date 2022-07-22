package com.example.poolregistration.model.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;

public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private PoolClient client;

    private LocalDateTime reserveTime;

    private int duration;

    public Order(PoolClient client, LocalDateTime reserveTime, int duration) {
        this.client = client;
        this.reserveTime = reserveTime;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PoolClient getClient() {
        return client;
    }

    public void setClient(PoolClient clientId) {
        this.client = clientId;
    }

    public LocalDateTime getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(LocalDateTime reserveTime) {
        this.reserveTime = reserveTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
