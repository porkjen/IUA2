package com.example.demo.dao;

import org.apache.poi.ss.formula.eval.UnaryMinusEval;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document("timeTableCollection") //collection name
public class TimeTableEntity {
    private String id;
    private String studentID;
    private List<Info> info = new ArrayList<>();
    private List<Pre_Info> pre_info = new ArrayList<>();


    // Nested class for the taken coursed
    public static class Info {
        private String name = ""; //課名*
        private String ENname = "";//英文課名
        private String classNum = ""; //課號*
        private String[] time; //上課時間*
        private String classroom = ""; //上課地點*
        private String teacher = ""; //授課老師*
        private String category; //選課類別*
        private String yearClass = ""; //開課年班(2A)
        private String upper = ""; //人數上限
        private String lower = ""; //人數下限
        private String department = ""; //開課系所
        private String duration = ""; //開課期限(單學期)
        private String credit = ""; //學分
        private String online = "";//是否遠距
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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getPrerequisites() {
            return prerequisites;
        }

        public void setPrerequisites(String prerequisites) {
            this.prerequisites = prerequisites;
        }

        public String getOutline() {
            return outline;
        }

        public void setOutline(String outline) {
            this.outline = outline;
        }

        public String getTeachingMethod() {
            return teachingMethod;
        }

        public void setTeachingMethod(String teachingMethod) {
            this.teachingMethod = teachingMethod;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getSyllabus() {
            return syllabus;
        }

        public void setSyllabus(String syllabus) {
            this.syllabus = syllabus;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getTargetE() {
            return targetE;
        }

        public void setTargetE(String targetE) {
            this.targetE = targetE;
        }

        public String getPrerequisitesE() {
            return prerequisitesE;
        }

        public void setPrerequisitesE(String prerequisitesE) {
            this.prerequisitesE = prerequisitesE;
        }

        public String getOutlineE() {
            return outlineE;
        }

        public void setOutlineE(String outlineE) {
            this.outlineE = outlineE;
        }

        public String getTeachingMethodE() {
            return teachingMethodE;
        }

        public void setTeachingMethodE(String teachingMethodE) {
            this.teachingMethodE = teachingMethodE;
        }

        public String getReferenceE() {
            return referenceE;
        }

        public void setReferenceE(String referenceE) {
            this.referenceE = referenceE;
        }

        public String getSyllabusE() {
            return syllabusE;
        }

        public void setSyllabusE(String syllabusE) {
            this.syllabusE = syllabusE;
        }

        public String getEvaluationE() {
            return evaluationE;
        }

        public void setEvaluationE(String evaluationE) {
            this.evaluationE = evaluationE;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "name='" + name + '\'' +
                    ", \nENname='" + ENname + '\'' +
                    ", \nclassNum='" + classNum + '\'' +
                    ", \ntime=" + Arrays.toString(time) +
                    ", \nclassroom='" + classroom + '\'' +
                    ", \nteacher='" + teacher + '\'' +
                    ", \ncategory='" + category + '\'' +
                    ", \nyearClass='" + yearClass + '\'' +
                    ", \nupper='" + upper + '\'' +
                    ", \nlower='" + lower + '\'' +
                    ", \ndepartment='" + department + '\'' +
                    ", \nduration='" + duration + '\'' +
                    ", \ncredit='" + credit + '\'' +
                    ", \nonline='" + online + '\'' +
                    ", \ntarget='" + target + '\'' +
                    ", \ntargetE='" + targetE + '\'' +
                    ", \nprerequisites='" + prerequisites + '\'' +
                    ", \nprerequisitesE='" + prerequisitesE + '\'' +
                    ", \noutline='" + outline + '\'' +
                    ", \noutlineE='" + outlineE + '\'' +
                    ", \nteachingMethod='" + teachingMethod + '\'' +
                    ", \nteachingMethodE='" + teachingMethodE + '\'' +
                    ", \nreference='" + reference + '\'' +
                    ", \nreferenceE='" + referenceE + '\'' +
                    ", \nsyllabus='" + syllabus + '\'' +
                    ", \nsyllabusE='" + syllabusE + '\'' +
                    ", \nevaluation='" + evaluation + '\'' +
                    ", \nevaluationE='" + evaluationE + '\'' +
                    '}';
        }
    }
    public static class Pre_Info {
        private String p_class = "";
        private String p_classNum = "";
        private String p_category = "";
        private String[] p_time = new String[3];
        private String p_classroom = "";
        private String p_teacher = "";
        private String p_target = "";
        private String p_evaluation = "";
        private String p_syllabus = "";

        public String getP_class() {
            return p_class;
        }

        public void setP_class(String p_class) {
            this.p_class = p_class;
        }

        public String getP_classNum() {
            return p_classNum;
        }

        public void setP_classNum(String p_classNum) {
            this.p_classNum = p_classNum;
        }

        public String[] getP_time() {
            return p_time;
        }

        public void setP_time(String[] p_time) {
            this.p_time = p_time;
        }

        public String getP_classroom() {
            return p_classroom;
        }

        public void setP_classroom(String p_classroom) {
            this.p_classroom = p_classroom;
        }

        public String getP_category() {
            return p_category;
        }

        public void setP_category(String p_category) {
            this.p_category = p_category;
        }

        public String getP_teacher() {
            return p_teacher;
        }

        public void setP_teacher(String p_teacher) {
            this.p_teacher = p_teacher;
        }

        public String getP_target() {
            return p_target;
        }

        public void setP_target(String p_target) {
            this.p_target = p_target;
        }

        public String getP_evaluation() {
            return p_evaluation;
        }

        public void setP_evaluation(String p_evaluation) {
            this.p_evaluation = p_evaluation;
        }

        public String getP_syllabus() {
            return p_syllabus;
        }

        public void setP_syllabus(String p_syllabus) {
            this.p_syllabus = p_syllabus;
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public List<Info> getInfo() {
        return info;
    }
    public void setWholeInfo(List<Info> info){this.info = info;}
    public void setInfo(Info info) {
        this.info.add(info);
    }

    public List<Pre_Info> getPre_info() {
        return pre_info;
    }
    public void setWholePre_info(List<Pre_Info> pre_info){this.pre_info = pre_info;}
    public void setPre_info(Pre_Info pre_info) {
        this.pre_info.add(pre_info);
    }
    public void removePre_info(Pre_Info pre_info) {
        this.pre_info.remove(pre_info);
    }

}