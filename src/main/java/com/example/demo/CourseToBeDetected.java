package com.example.demo;
import java.util.ArrayList;

public class CourseToBeDetected{
    private String studentID;
    private String courseName;
    private String courseNumber;
    private String semester;

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

    public void setSemester(String smemster){
        this.semester = smemster;
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
}