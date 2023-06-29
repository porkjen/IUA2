package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("MapCollection")
//We use the annotation @Document to set the collection name that will be used by the model.
//If the collection doesn’t exist, MongoDB will create it.
public class FoodEntity {
    private String id;
    private String postId = ""; //F12345
    private String studentID = "";
    private String nickname = "";
    private String store = "";
    private double rating ;
    private int rating_num ; //number of rating people
    private String post_time = ""; //2023 06 15
    private String[] weekday_text = new String[7]; //"星期一: 11:30 – 20:30"
    private String address = "";
    private String URL = "";
    private List<p> review = new ArrayList<>();
    private List<String> saved = new ArrayList<>();


    public static class p {
        private String p_studentID = "";
        private String p_name = "";
        private String p_review = "";
        private String p_time = "";
        private int p_rate;

        public String getP_studentID() {
            return p_studentID;
        }

        public void setP_studentID(String p_studentID) {
            this.p_studentID = p_studentID;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getP_review() {
            return p_review;
        }

        public void setP_review(String p_review) {
            this.p_review = p_review;
        }

        public String getP_time() {
            return p_time;
        }

        public void setP_time(String p_time) {
            this.p_time = p_time;
        }

        public int getP_rate() {
            return p_rate;
        }

        public void setP_rate(int p_rate) {
            this.p_rate = p_rate;
        }
    }

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

    public int getRating_num() {
        return rating_num;
    }

    public void setRating_num(int rating_num) {
        this.rating_num = rating_num;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String[] getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(String weekday_text, int j) {
        this.weekday_text[j] = weekday_text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public List<p> getReview() {
        return review;
    }

    public void setReview(p review) {
        this.review.add(review);
    }

    public void removeReview(p review){ this.review.remove(review); }

    public List<String> getSaved() {
        return saved;
    }
    public void setSaved(String saved) {
        this.saved.add(saved);
    }
    public void removeSaved(String saved) {
        this.saved.remove(saved);
    }
    public void savefirst(String saved) {
        this.saved.add(0, saved);
    }
}
