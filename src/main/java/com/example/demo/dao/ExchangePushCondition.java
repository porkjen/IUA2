package com.example.demo.dao;

public class ExchangePushCondition {
    private String studentID;
    private String number;
    private String[] time;
    private String[] category;
    private String name;

    public ExchangePushCondition(){}

    public ExchangePushCondition(String studentID){
        this.studentID = studentID;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    public void setTime(String[] time){
        this.time = time;
    }

    public void setCategory(String[] category){
        this.category = category;
    }

    public String getStudentID(){
        return studentID;
    }

    public String getNumber(){
        return number;
    }
    public String getName(){
        return name;
    }
    public String[] getTime(){
        return time;
    }
    public String[] getCategory(){
        return category;
    }
}
