package com.example.demo.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("recommandCourse") //collection name
public class RecommandCourseEntity {

    @Id
    private String id;
    String studentID;

    List<Info> info = new ArrayList<>();

    List<Show> show = new ArrayList<>();

    List<Display> display = new ArrayList<>();

    public static class Display{
       String name = "";
       String field = "";
       String category = "";
       String[] timeList;
       String way = "";

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
        public String[] getTimeList() {
            return timeList;
        }

        public void setTimeList(String[] timeList) {
            this.timeList = timeList;
        }
        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }
    }

    public static class Show{
        String name = "";
        String field = "";
        String category = "";
        String[] timeList;
        String way = "";

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
        public String[] getTimeList() {
            return timeList;
        }

        public void setTimeList(String[] timeList) {
            this.timeList = timeList;
        }
        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
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

    public List<Show> getShow() {
        return show;
    }
    public void setShow(Show show) {
        this.show.add(show);
    }

    public List<Display> getDisplay() {
        return display;
    }
    public void setDisplay(Display display) {
        this.display.add(display);
    }
    public void setWholeDisplay(List<Display> display) {
        this.display = display;
    }
}
