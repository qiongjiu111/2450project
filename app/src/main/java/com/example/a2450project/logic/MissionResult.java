package com.example.a2450project.logic;

public class MissionResult {

    private boolean success;
    private String log;

    public MissionResult(boolean success, String log) {
        this.success = success;
        this.log = log;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getLog() {
        return log;
    }
}