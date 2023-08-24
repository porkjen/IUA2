package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("savedCollection")
public class SavedEntity {
    private String id;
    private String studentID;
    private List<String> saved = new ArrayList<>();
    private List<String> posted = new ArrayList<>();

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

    public List<String> getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved.add(saved);
    }
    public void removeSaved(String saved) {
        this.saved.remove(saved);
    }
    public List<String> getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted.add(posted);
    }
    public void removePosted(String posted) {
        this.posted.remove(posted);
    }
}
