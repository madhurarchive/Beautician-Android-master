package com.provider.beautician.model;

public class WorkingHoursModel {
    private String startTime;
    private String endTime;
    private boolean isOpen;
    private boolean isAvailable;
    private String date;

    public WorkingHoursModel(String date,String startTime, String endTime, boolean isOpen, boolean isAvailable) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isOpen = isOpen;
        this.isAvailable = isAvailable;
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
