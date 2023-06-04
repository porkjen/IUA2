package com.example.demo;

public class ExcelDataBean {

    public ExcelDataBean(){

    }

    private String academicYear; //學期
    private String classNumber; //課號
    private String credit; //學分
    private String category; //選別
    private String className; //課名
    private String score; //分數

    public String getAcademicYear(){
        return academicYear;
    }

    public void setAcademicYear(String academicYear){
        this.academicYear = academicYear;
    }

    public String getClassNumber(){
        return classNumber;
    }

    public void setClassNumber(String classNumber){
        this.classNumber = classNumber;
    }

    public String getCredit(){
        return credit;
    }

    public void setCredit(String credit){
        this.credit = credit;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score = score;
    }

    @Override
    public String toString(){
        return "\n學期"+getAcademicYear()+"\n課號"+getClassNumber()+"\n學分"+getCredit()+"\n選別"+getCategory()+"\n課名"+getClassName()+"\n分數"+getScore()+"\n========================";
    }


}
