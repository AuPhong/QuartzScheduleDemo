package com.example.quartzscheduler.info;

import java.io.Serializable;

public class TimerInfo implements Serializable {
    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatIntervalMs;
    private long intervalOffsetMs;
    private String callbackData;

    public int getRemainingFireCount() {
        return remainingFireCount;
    }

    public void setRemainingFireCount(int remainingFireCount) {
        this.remainingFireCount = remainingFireCount;
    }

    public int getTotalFireCount() {
        return totalFireCount;
    }

    public void setTotalFireCount(int totalFireCount) {
        this.totalFireCount = totalFireCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(boolean runForever) {
        this.runForever = runForever;
    }

    public long getRepeatIntervalMs() {
        return repeatIntervalMs;
    }

    public void setRepeatIntervalMs(long repeatIntervalMs) {
        this.repeatIntervalMs = repeatIntervalMs;
    }

    public long getIntervalOffsetMs() {
        return intervalOffsetMs;
    }

    public void setIntervalOffsetMs(long intervalOffsetMs) {
        this.intervalOffsetMs = intervalOffsetMs;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }
}
