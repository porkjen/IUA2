package com.example.demo;

public class AllScore extends Course{
    private String academicYear;
    private String overallResult;
    public AllScore(String department, String classCode, String professor, String ChienseLesson, String EnglishLesson, String grade, String credit, String must, String time, String mainField, String subField, String remark, String overallResult, String academicYear){
        super(department, classCode, professor, ChienseLesson, EnglishLesson, grade, credit, must, time, mainField, subField, remark);
        this.overallResult = overallResult;
        this.academicYear = academicYear;
    }
    public String getAcademicYear(){
        return this.academicYear;
    }
    public String getOverallResult(){
        return this.overallResult;
    }
}