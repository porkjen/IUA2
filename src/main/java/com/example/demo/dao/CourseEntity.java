package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("CourseCollection")
public class CourseEntity{
    private String c_name;
    private String c_category;
    private String c_number;
    private String c_time;
    private String c_classroom;
    private String c_teacher;
    private String c_grade;
    private String c_people;
    private String c_department;
    
    public CourseEntity(){}

    public CourseEntity(String name, String category, String number, String time, String room, String teacher, String grade, String people, String dept){
        this.c_name = name;
        this.c_category = category;
        this.c_number = number;
        this.c_time = time;
        this.c_classroom = room;
        this.c_teacher = teacher;
        this.c_grade = grade;
        this.c_people = people;
        this.c_department = dept;
    }

    public String getName(){
        return c_name;
    }
    public String getCategory(){
        return c_category;
    }
    public String getNumber(){
        return c_number;
    }
    public String getTime(){
        return c_time;
    }
    public String getRoom(){
        return c_classroom;
    }
    public String getTeacher(){
        return c_teacher;
    }
    public String getGrade(){
        return c_grade;
    }
    public String getPeople(){
        return c_people;
    }
    public String getDept(){
        return c_department;
    }
}