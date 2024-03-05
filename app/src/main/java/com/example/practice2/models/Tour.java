package com.example.practice2.models;

public class Tour {
    public String name;
    public String schedule;
    public int imgResource;
    public String startDate;
    public String startTime;

    public Tour(String name, String schedule, int imgResource) {
        this.name = name;
        this.schedule = schedule;
        this.imgResource = imgResource;
    }

    public String getDescription() {
        return name+"\n"+schedule;
    }
}
