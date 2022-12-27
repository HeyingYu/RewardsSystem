package com.example.rewardssystem.exception;

import java.time.LocalDate;

public class ErrorDetails {
    private LocalDate timestamp;
    private String meesage;
    private String details;

    public ErrorDetails(LocalDate timestamp, String meesage, String details) {
        this.timestamp = timestamp;
        this.meesage = meesage;
        this.details = details;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getMeesage() {
        return meesage;
    }

    public String getDetails() {
        return details;
    }
}

