package com.example.demo;

public class CourseToBeDetected{
    private String studentID;
    private String courseName;
    private String courseNumber;
    private String semester;

    public CourseToBeDetected(){}

    public void setstudentID(String id){
        this.studentID = id;
    }

    public void setcourseName(String name){
        this.courseName = name;
    }

    public void setcourseNumber(String number){
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