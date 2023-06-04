package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("houseCollection") //collection name
public class HouseEntity {

    private String id;
    private String postId = "";
    private String studentID = "";
    private String name = "";
    private String title = "";
    private String money = "";
    private String people = "";
    private String address = "";
    private String area = "";
    private String gender = "";
    private String style = "";
    private String water = "";
    private String power = "";
    private String car = "";
    private String floor = "";
    private String rent_date = "";
    private String note = "";
    private String post_time = "";

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRent_date() {
        return rent_date;
    }

    public void setRent_date(String rent_date) {
        this.rent_date = rent_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
