package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("CourseCollection")
public class CourseEntity{
    private String c_semester;
    private String c_name;
    private String c_category;  //選課類別(必選修)
    private String c_number;
    private String c_time;
    private String c_classroom;
    private String c_teacher;
    private String c_grade;
    private String c_people;
    private String c_department;    //開課系所
    private String c_credit;
    private String c_objective; //教學目標
    private String c_precourse; //先修
    private String c_outline;   //教材
    private String c_teachingMethod;    //教學方式
    private String c_reference; //參考書
    private String c_syllabus;  //教學進度
    private String c_evaluation;    //評量方式
    private String c_field;
    
    public CourseEntity(){}

    public CourseEntity(String semester, String name, String category, String number, String time, String room, String teacher, String grade, String people, String dept, String credit, String objective, String precourse, String outline, String method, String reference, String syllabus, String evaluation){
        this.c_semester = semester;
        this.c_name = name;
        this.c_category = category;
        this.c_number = number;
        this.c_time = time;
        this.c_classroom = room;
        this.c_teacher = teacher;
        this.c_grade = grade;
        this.c_people = people;
        this.c_department = dept;
        this.c_credit = credit;
        this.c_objective = objective;
        this.c_precourse = precourse;
        this.c_outline = outline;
        this.c_teachingMethod = method;
        this.c_reference = reference;
        this.c_syllabus = syllabus;
        this.c_evaluation = evaluation;
    }

    public String getSemester(){
        return c_semester;
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

    public String getField() {
        return c_field;
    }

    public void setField(String field) {
        this.c_field = field;
    }

    public String getCCredit() {
        return c_credit;
    }

    public void setCCredit(String c_credit) {
        this.c_credit = c_credit;
    }

    public  String getCObjective(){ return c_objective;}

    public void setCObjective(String c_objective){this.c_objective = c_objective;}

    public  String getCPrecourse(){ return c_precourse;}

    public void setCPrecourse(String c_precourse){this.c_precourse = c_precourse;}

    public  String getCOutline(){ return c_outline;}

    public void setCOutline(String c_outline){this.c_outline = c_outline;}

    public  String getCTmethod(){ return c_teachingMethod;}

    public void setCTmethod(String c_tmethod){this.c_teachingMethod = c_tmethod;}

    public  String getCReference(){ return c_reference;}

    public void setCReference(String c_reference){this.c_reference = c_reference;}

    public  String getCSyllabus(){ return c_syllabus;}

    public void setCSyllabus(String c_syllabus){this.c_syllabus = c_syllabus;}

    public  String getCEvaluation(){ return c_evaluation;}

    public void setCEvaluation(String c_evaluation){this.c_evaluation = c_evaluation;}
}