package com.example.demo;

public class FinishedCourse{
    private String courseName;
    private String credit;
    private String category;
    private String time;
    private String teacher;

    public FinishedCourse(){}

    public void setName(String name){
        this.courseName = name;
    }

    public void setCredit(String credit){
        this.credit = credit;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }

    public String setName(){
        return courseName;
    }

    public String setCredit(){
        return credit;
    }

    public String setCategory(){
        return category;
    }

    public String setTime(){
        return time;
    }

    public String setTeacher(){
        return teacher;
    }
}