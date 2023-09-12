package com.example.demo;
import java.util.ArrayList;

public class CourseToBeDetected{
    private String studentID;
    private String courseName;
    private String courseNumber;
    private String semester;
    private Boolean isFull = true;

    public CourseToBeDetected(){}

    public void setID(String id){
        this.studentID = id;
    }

    public void setCourseName(String name){
        this.courseName = name;
    }

    public void setCourseNumber(String number){
        this.courseNumber = number;
    }

    public void setSemester(String smemster){
        this.semester = smemster;
    }

    public void setIsFull(Boolean isFull){
        this.isFull = isFull;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getName(){
        return courseName;
    }

    public String getNumber(){
        return courseNumber;
    }

    public String getSemester(){
        return semester;
    }

    public Boolean getIsFull(){
        return isFull;
    }
}