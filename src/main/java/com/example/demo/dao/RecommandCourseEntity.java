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

    List<General> general = new ArrayList<>();

    List<PE> pe = new ArrayList<>();
    List<Info> info = new ArrayList<>();

    List<Show> show = new ArrayList<>();

    List<Display> display = new ArrayList<>();

    public static class General{
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

    public static class PE{
        String name = "";
        String teacher = "";
        String category = "";
        String[] timeList;
        String way = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public List<General> getGeneral() {
        return general;
    }
    public void setGeneral(General general) {
        this.general.add(general);
    }

    public List<PE> getPE() {
        return pe;
    }
    public void setPE(PE pe) {
        this.pe.add(pe);
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
