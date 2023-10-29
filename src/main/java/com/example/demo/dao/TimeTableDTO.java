package com.example.demo.dao;

public class TimeTableDTO {
    private String name = ""; //課名*
    private String classNum = ""; //課號*
    private String[] time; //上課時間*
    private String classroom = ""; //上課地點*
    private String teacher = ""; //授課老師*
    private String category; //選課類別*
    private boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public TimeTableDTO(String name, String classNum, String[] time, String classroom, String teacher, String category, boolean available) {
        this.name = name;
        this.classNum = classNum;
        this.time = time;
        this.classroom = classroom;
        this.teacher = teacher;
        this.category = category;
        this.available = available;
    }
}
