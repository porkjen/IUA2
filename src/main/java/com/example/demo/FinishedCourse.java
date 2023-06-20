package com.example.demo;

public class FinishedCourse{
    private String courseName;
    private String credit;
    private String category;
    private String time;
    private String teacher;
    private String department;

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


    public void setDepartment(String dept){
        this.department = dept;
    }

    public String getName(){
        return courseName;
    }

    public String getCredit(){
        return credit;
    }

    public String getCategory(){
        return category;
    }

    public String getTime(){
        return time;
    }

    public String getTeacher(){
        return teacher;
    }

    public String getDepartment(){
        return department;
    }
}