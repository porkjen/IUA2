package com.example.demo;
import java.util.ArrayList;

public class CourseToBeDetected{
    private String studentID;
    private String courseName;
    private String courseNumber;

    public CourseToBeDetected(){}

    public void setID(String id){
        this.studentID = id;
    }

    public void setName(String name){
        this.courseName = name;
    }

    public void setNumber(String number){
        this.courseNumber = number;
    }

    public String getID(){
        return studentID;
    }

    public String getName(){
        return courseName;
    }

    public String getNumber(){
        return courseNumber;
    }
}