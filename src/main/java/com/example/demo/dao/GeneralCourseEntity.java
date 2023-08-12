package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("g_CourseCollection") //collection name
public class GeneralCourseEntity {
    String name = "";
    String number = "";
    float rate;
    String classroom;
    String time; //"306、307"
    String teacher = "";
    String evaluation = ""; //評量方式
    String subfield;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getSubfield(){
        return subfield;
    }

    public void setSubfield(String subfield){
        this.subfield = subfield;
    }
}
