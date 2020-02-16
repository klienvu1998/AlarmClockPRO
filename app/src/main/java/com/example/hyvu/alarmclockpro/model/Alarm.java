package com.example.hyvu.alarmclockpro.model;

import java.io.Serializable;

public class Alarm implements Serializable {
    int id;
    int hour;
    int minute;
    String message;
    int isChecked;

    public Alarm() {
        isChecked=0;
    }

    public Alarm(int hour, int minute, String message) {
        this.hour = hour;
        this.minute = minute;
        this.message = message;
    }

    public Alarm(int id, int hour, int minute, String message) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.message = message;
    }

    public Alarm(int id, int hour, int minute, String message, int isChecked) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.message = message;
        this.isChecked = isChecked;
    }

    public int getChecked() {
        return isChecked;
    }

    public void setChecked(int checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
