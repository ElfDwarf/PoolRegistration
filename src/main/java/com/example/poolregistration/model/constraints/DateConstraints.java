package com.example.poolregistration.model.constraints;

public class DateConstraints {
    private final int startTime;
    private final int endTime;
    private final int maxClientsPerTime;

    public DateConstraints(int startTime, int endTime, int maxClientsPerTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxClientsPerTime = maxClientsPerTime;
    }
    public int getStartTime() {
        return startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public int getMaxClientsPerTime() {
        return maxClientsPerTime;
    }
}
