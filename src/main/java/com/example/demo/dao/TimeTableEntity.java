package com.example.demo.dao;

import org.apache.poi.ss.formula.eval.UnaryMinusEval;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("timeTableCollection") //collection name
public class TimeTableEntity {
    private String id;
    private String studentID;
    private List<Info> info;
    private List<Pre_Info> pre_info;

    // Nested class for the taken coursed
    public static class Info {
        private String name = "";
        private String classNum = "";
        private String time = "";
        private String classroom = "";

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }
    }
    public static class Pre_Info {
        private String p_class = "";
        private String p_classNum = "";
        private String p_time = "";
        private String p_classroom = "";

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

        public String getP_time() {
            return p_time;
        }

        public void setP_time(String p_time) {
            this.p_time = p_time;
        }

        public String getP_classroom() {
            return p_classroom;
        }

        public void setP_classroom(String p_classroom) {
            this.p_classroom = p_classroom;
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

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public List<Pre_Info> getPre_info() {
        return pre_info;
    }

    public void setPre_info(List<Pre_Info> pre_info) {
        this.pre_info = pre_info;
    }

}
