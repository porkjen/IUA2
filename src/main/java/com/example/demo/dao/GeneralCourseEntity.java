package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("g_CourseCollection") //collection name
public class GeneralCourseEntity {
    String semester;
    String name = "";
    String classNum = "";
    String classroom;
    String time; //"306、307"
    String teacher = "";
    String evaluation = ""; //評量方式
    String subfield;
    String category = "";
    String target = "";
    String syllabus = "";

    public String getSemester(){
        return semester;
    }

    public void setSemester(String smester){
        this.semester = smester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}
