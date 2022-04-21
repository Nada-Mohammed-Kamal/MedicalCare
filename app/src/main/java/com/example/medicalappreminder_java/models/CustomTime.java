package com.example.medicalappreminder_java.models;

import com.example.medicalappreminder_java.Constants.Status;

public class CustomTime {
    int hour;
    int minute;
    Status status;

    public CustomTime(int hour, int minute ) {
        this.hour = hour;
        this.minute = minute;
        this.status = Status.notItsTimeYet;
    }



    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
