package com.example.medicalappreminder_java.models;

import com.example.medicalappreminder_java.Constants.Status;

import java.io.Serializable;
import java.util.Objects;

public class CustomTime implements Serializable {
    int hour;
    int minute;
    Status status;

    public CustomTime(int hour, int minute ) {
        super();
        this.hour = hour;
        this.minute = minute;
        this.status = Status.notItsTimeYet;
    }

    public CustomTime(CustomTime ct)
    {
        super();
        this.hour = ct.hour;
        this.minute = ct.minute;
        this.status = ct.status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomTime that = (CustomTime) o;
        return hour == that.hour && minute == that.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }
}
