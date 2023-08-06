package com.example.demo;

public class CourseCredit {
    private String courseName;
    private int credit;
    
    public CourseCredit(String courseName, int credit){
        this.courseName = courseName;
        this.credit = credit;
    }

    public String getCourseName(){
        return courseName;
    }

    public int getCredit(){
        return credit;
    }
}
