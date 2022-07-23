package com.example.poolregistration.model.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "order")
public class PoolOrder {
    @Id
    @GeneratedValue
    private String id;

    @ManyToMany
    private PoolClient client;

    private LocalDate reserveDate;
    private LocalTime reserveTime;
    private int duration;

    public PoolOrder(PoolClient client, LocalDate reserveDate, LocalTime reserveTime, int duration) {
        this.client = client;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PoolClient getClient() {
        return client;
    }

    public void setClient(PoolClient client) {
        this.client = client;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    public LocalTime getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(LocalTime reserveTime) {
        this.reserveTime = reserveTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
