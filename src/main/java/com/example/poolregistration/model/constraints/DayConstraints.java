package com.example.poolregistration.model.constraints;

public class DayConstraints {
    private final int startTime;
    private final int endTime;
    private final int maxClientsPerTime;

    public DayConstraints(int startTime, int endTime, int maxClientsPerTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxClientsPerTime = maxClientsPerTime;
    }
    public int getStartTime() {
        return startTime;
    }
    int getEndTime() {
        return endTime;
    }
    int getMaxClientsPerTime() {
        return maxClientsPerTime;
    }
}
