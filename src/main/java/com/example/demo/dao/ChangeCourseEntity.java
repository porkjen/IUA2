package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document("change_CourseCollection")
public class ChangeCourseEntity {
    //test
    private String id;
    private String postId; //C12345
    private String studentID;
    private String nickname;
    private String course;
    private String category; //選修、通識...
    private String[] time;
    private String teacher;
    private String content;
    private String post_time;
    private List<String> contact = new ArrayList<>();
    private String decided = "";//最終結果(交換對象的學號)
    private String status = "未換";
    private String desiredClass;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.add(contact);
    }

    public String getDecided() {
        return decided;
    }

    public void setDecided(String decided) {
        this.decided = decided;
    }

    public String getDesiredClass() {
        return desiredClass;
    }

    public void setDesiredClass(String desiredClass) {
        this.desiredClass = desiredClass;
    }
}