package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("savedCollection")
public class SavedEntity {
    private String id;
    private String studentID;
    private List<String> postId = new ArrayList<>();

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

    public List<String> getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId.add(postId);
    }
    public void removePostId(String postId) {
        this.postId.remove(postId);
    }
}
