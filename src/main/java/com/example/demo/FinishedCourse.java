package com.example.demo;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "F_CourseCollection")
public class FinishedCourse{
    private String studentID;
    private String courseName;
    private String credit;
    private String category;
    private String time;
    private String teacher;

    public FinishedCourse(String id){
        this.studentID = id;
    }

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

    public String setId(){
        return studentID;
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