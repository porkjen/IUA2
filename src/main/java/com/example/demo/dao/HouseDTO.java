package com.example.demo.dao;

public class HouseDTO {
    private String postId = "";
    private String name = "";
    private String title = "";
    private String postTime = "";
    private String status;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HouseDTO(String post_id, String name, String title, String postTime, String status) {
        this.postId = post_id;
        this.name = name;
        this.title = title;
        this.postTime = postTime;
        this.status = status;
    }
}
