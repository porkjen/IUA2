package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
@Document("ForeignLanguageCollection")
public class ForeignLanguageEntity {

    private String id;
    private String classNum = ""; //課號*
    private String department = ""; //開課系所
    private String teacher = ""; //授課老師*
    private String name = ""; //課名*
    private String ENname = "";//英文課名
    private String yearClass = ""; //開課年班(2A)
    private String credit = ""; //學分
    private String upper = ""; //人數上限
    private String lower = ""; //人數下限
    private String category; //選課類別*
    private String duration = ""; //開課期限(單學期)
    private String[] time; //上課時間*
    private String classroom = ""; //上課地點*
    private String online = "";//是否遠距
    private String mainField = "";//主領域
    private String note = "";//備註
    ////////大綱////////
    private String target = "";//教學目標
    private String targetE = "";
    private String prerequisites = ""; //先修科目
    private String prerequisitesE = "";
    private String outline = "";//教材內容
    private String outlineE = "";
    private String teachingMethod = ""; //教學方式
    private String teachingMethodE = "";
    private String reference = ""; //參考書目
    private String referenceE = "";
    private String syllabus = ""; //教學進度
    private String syllabusE = "";
    private String evaluation = ""; //評量方式
    private String evaluationE = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getENname() {
        return ENname;
    }

    public void setENname(String ENname) {
        this.ENname = ENname;
    }

    public String getYearClass() {
        return yearClass;
    }

    public void setYearClass(String yearClass) {
        this.yearClass = yearClass;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getMainField() {
        return mainField;
    }

    public void setMainField(String mainField) {
        this.mainField = mainField;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetE() {
        return targetE;
    }

    public void setTargetE(String targetE) {
        this.targetE = targetE;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getPrerequisitesE() {
        return prerequisitesE;
    }

    public void setPrerequisitesE(String prerequisitesE) {
        this.prerequisitesE = prerequisitesE;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getOutlineE() {
        return outlineE;
    }

    public void setOutlineE(String outlineE) {
        this.outlineE = outlineE;
    }

    public String getTeachingMethod() {
        return teachingMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        this.teachingMethod = teachingMethod;
    }

    public String getTeachingMethodE() {
        return teachingMethodE;
    }

    public void setTeachingMethodE(String teachingMethodE) {
        this.teachingMethodE = teachingMethodE;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceE() {
        return referenceE;
    }

    public void setReferenceE(String referenceE) {
        this.referenceE = referenceE;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getSyllabusE() {
        return syllabusE;
    }

    public void setSyllabusE(String syllabusE) {
        this.syllabusE = syllabusE;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getEvaluationE() {
        return evaluationE;
    }

    public void setEvaluationE(String evaluationE) {
        this.evaluationE = evaluationE;
    }

    @Override
    public String toString() {
        return "ForeignCourseEntity{" +
                "id=" + id + '\'' +
                ", classNum=" + classNum + '\n' +
                ", department=" + department + '\n' +
                ", teacher=" + teacher + '\n' +
                ", name=" + name + '\n' +
                ", ENname=" + ENname + '\n' +
                ", yearClass=" + yearClass + '\n' +
                ", credit=" + credit + '\n' +
                ", upper=" + upper + '\n' +
                ", lower=" + lower + '\n' +
                ", category=" + category + '\n' +
                ", duration=" + duration + '\n' +
                ", time=" + Arrays.toString(time) +
                ", classroom=" + classroom + '\n' +
                ", online=" + online + '\n' +
                ", mainField=" + mainField + '\n' +
                ", note=" + note + '\n' +
                ", target=" + target + '\n' +
                ", targetE=" + targetE + '\n' +
                ", prerequisites=" + prerequisites + '\n' +
                ", prerequisitesE=" + prerequisitesE + '\n' +
                ", outline=" + outline + '\n' +
                ", outlineE=" + outlineE + '\n' +
                ", teachingMethod=" + teachingMethod + '\n' +
                ", teachingMethodE=" + teachingMethodE + '\n' +
                ", reference=" + reference + '\n' +
                ", referenceE=" + referenceE + '\n' +
                ", syllabus=" + syllabus + '\n' +
                ", syllabusE=" + syllabusE + '\n' +
                ", evaluation=" + evaluation + '\n' +
                ", evaluationE=" + evaluationE + '\n' +
                '}';
    }
}
