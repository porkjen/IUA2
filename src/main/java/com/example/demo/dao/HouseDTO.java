package com.example.demo.dao;

public class HouseDTO {
    private String postId = "";
    private String name = "";
    private String title = "";

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

    public HouseDTO(String post_id, String name, String title) {
        this.postId = post_id;
        this.name = name;
        this.title = title;
    }
}
