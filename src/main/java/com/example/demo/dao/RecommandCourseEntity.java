package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("recommandCourse") //collection name
public class RecommandCourseEntity {
    String studentID;

    List<Info> info = new ArrayList<>();

    List<Display> display = new ArrayList<>();

    public static class Display{
       String name = "";
       String field = "";
       String category = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    // Nested class for the taken coursed
    public static class Info {
        String name = "";
        String number = "";
        String score = "";
        String loved = "";


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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
        public String getLoved() {
            return loved;
        }

        public void setLoved(String loved) {
            this.loved = loved;
        }
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
    public void setInfo(Info info) {
        this.info.add(info);
    }

    public List<Display> getDisplay() {
        return display;
    }
    public void setDisplay(Display display) {
        this.display.add(display);
    }
}
