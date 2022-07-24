package com.example.poolregistration.model.dao;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class PoolOrder {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    private PoolClient client;

    private LocalDate reserveDate;
    private LocalTime reserveTime;
    private int duration;

    public PoolOrder() {
    }

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
