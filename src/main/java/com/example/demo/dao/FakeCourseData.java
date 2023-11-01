package com.example.demo.dao;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("FakeCourseCollection")
public class FakeCourseData {
    private String id;
    private String courseName;
    private String courseNumber;
    private String semester;
    private String maxPeople;
    private String nowPeople;

    public FakeCourseData(){}

    public FakeCourseData(String name, String number, String semester, String max, String now){
        this.courseName = name;
        this.courseNumber = number;
        this.semester = semester;
        this. maxPeople = max;
        this.nowPeople = now;
    }

    public void setCourseName(String name){
        this.courseName = name;
    }
    public void setCourseNumber(String number){
        this.courseNumber = number;
    }
    public void setSemester(String semester){
        this.semester = semester;
    }
    public void setMaxPeople(String max){
        this.maxPeople = max;
    }
    public void setNowPeople(String now){
        this.nowPeople = now;
    }
    public String getCourseName(){
        return courseName;
    }
    public String getCourseNumber(){
        return courseNumber;
    }
    public String getSemester(){
        return semester;
    }
    public String getMaxPeople(){
        return maxPeople;
    }
    public String getNowPeople(){
        return nowPeople;
    }
}
