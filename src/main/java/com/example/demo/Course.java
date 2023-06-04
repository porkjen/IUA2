package com.example.demo;
public class Course {
    private String department;
    private String classCode;
    private String professor;
    private String ChienseLesson;
    private String EnglishLesson;
    private String grade;
    private String credit;
    private String must;
    private String time;
    private String mainField;
    private String subField;
    private String remark;
    public Course(String department, String classCode, String professor, String ChienseLesson, String EnglishLesson, String grade, String credit, String must, String time, String mainField, String subField, String remark){
        this.department = department;
        this.classCode = classCode;
        this.professor = professor;
        this.ChienseLesson = ChienseLesson;
        this.EnglishLesson = EnglishLesson;
        this.grade = grade;
        this.credit = credit;
        this.must = must;
        this.time = time;
        this.mainField = mainField;
        this.subField = subField;
        this.remark = remark;
    }
    public String getClassCode(){
        return this.classCode;
    }
    public String getProfessor(){
        return this.professor;
    }
    public String getChienseLesson(){
        return this.ChienseLesson;
    }
    public String getEnglishLesson(){
        return this.EnglishLesson;
    }
    public String getGrade(){
        return this.grade;
    }
    public String getCredit(){
        return this.credit;
    }
    public String getMust(){
        return this.must;
    }
    public String getTime(){
        return this.time;
    }
    public String getMainField(){
        return this.mainField;
    }
    public String getSubField(){
        return this.subField;
    }
    public String getRemark(){
        return this.remark;
    }
    public String getDepartment(){
        return this.department;
    }
}
