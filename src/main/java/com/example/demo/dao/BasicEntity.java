package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("basicCollection") //collection name
public class BasicEntity {


    private String id;
    private String studentID = "";
    private String password = "";
    private String name = "";
    private String department = "";
    private String grade = "";
    private String nickname = "";
    private String email = "";
    private String team = ""; //班級
    private String birth = "";
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getStudentID() {
        return studentID;
    }
    public String getPassword() {
        return password;
    }

}
