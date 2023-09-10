package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("courseOMajorTS") //collection name
public class RequiredCourseEntityOuterMajorTS {

    String c_name = "";
    String c_category = "";
    String c_number = "";
    String c_grade = "";
    String c_credit = "";
    String c_teacher = "";
    String c_major = "";
    String c_time = "";
    String c_location = "";
    String c_people = "";
    String c_objective = "";
    String c_precourse = "";
    String c_outline = "";
    String c_tmethod = "";
    String c_reference = "";
    String c_syllabus = "";
    String c_evaluation = "";


    public String getCName() {
        return c_name;
    }

    public void setCName(String c_name) {
        this.c_name = c_name;
    }

    public String getCCategory() {
        return c_category;
    }

    public void setCCategory(String c_category) {
        this.c_category = c_category;
    }

    public String getCNumber() {
        return c_number;
    }

    public void setCNumber(String c_number) {
        this.c_number = c_number;
    }

    public String getCGrade() {
        return c_grade;
    }

    public void setCGrade(String c_grade) {
        this.c_grade = c_grade;
    }

    public String getCCredit() {
        return c_credit;
    }

    public void setCCredit(String c_credit) {
        this.c_credit = c_credit;
    }

    public String getCTeacher() {
        return c_teacher;
    }

    public void setCTeacher(String c_teacher) {
        this.c_teacher = c_teacher;
    }

    public String getCMajor() {
        return c_major;
    }

    public void setCMajor(String c_major) {
        this.c_major = c_major;
    }
    public  String getCTime(){ return c_time;}
    public void setCTime(String c_time){this.c_time = c_time;}

    public  String getCLocation(){ return c_location;}

    public void setCLocation(String c_location){this.c_location = c_location;}

    public  String getCPeople(){ return c_people;}

    public void setCPeople(String c_people){this.c_people = c_people;}

    public  String getCObjective(){ return c_objective;}

    public void setCObjective(String c_objective){this.c_objective = c_objective;}

    public  String getCPrecourse(){ return c_precourse;}

    public void setCPrecourse(String c_precourse){this.c_precourse = c_precourse;}

    public  String getCOutline(){ return c_outline;}

    public void setCOutline(String c_outline){this.c_outline = c_outline;}

    public  String getCTmethod(){ return c_tmethod;}

    public void setCTmethod(String c_tmethod){this.c_tmethod = c_tmethod;}

    public  String getCReference(){ return c_reference;}

    public void setCReference(String c_reference){this.c_reference = c_reference;}

    public  String getCSyllabus(){ return c_syllabus;}

    public void setCSyllabus(String c_syllabus){this.c_syllabus = c_syllabus;}

    public  String getCEvaluation(){ return c_evaluation;}

    public void setCEvaluation(String c_evaluation){this.c_evaluation = c_evaluation;}
}
