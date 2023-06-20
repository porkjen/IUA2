package com.example.demo.dao;

public class FoodDTO {
    private String postId = "";
    private String nickname = "";
    private String store = "";
    private double rating;
    private String post_time;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public FoodDTO(String postId, String nickname, String store, double rating, String post_time) {
        this.postId = postId;
        this.nickname = nickname;
        this.store = store;
        this.rating = rating;
        this.post_time = post_time;
    }
}
