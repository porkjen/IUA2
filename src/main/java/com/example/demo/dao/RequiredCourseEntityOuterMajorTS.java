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
}
