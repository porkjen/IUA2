package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("restaurant")
//We use the annotation @Document to set the collection name that will be used by the model.
//If the collection doesn’t exist, MongoDB will create it.
public class MapDao {
    private String name;
    private String place_id;
    private double rating;
    private String[] weekday_text = {"", "", "", "", "", "", ""};//營業時間
    private String formatted_address;
    private String[] author_name = new String[5];
    private String[] text = new String[5];
    private String[] time = new String[5];
    private String URL;
    void setName(String name){
        this.name = name;
    }
    void setPlace_id(String place_id){
        this.place_id = place_id;
    }
    void setRating(double rating){
        this.rating = rating;
    }
    void setWeekday_text(String weekday_text, int i){
        this.weekday_text[i] = weekday_text;
    }
    void setFormatted_address(String formatted_address){
        this.formatted_address = formatted_address;
    }
    void setAuthor_name(String author_name, int index){
        this.author_name[index] = author_name;
    }
    void setText(String text, int index){
        this.text[index] = text;
    }
    void setTime(String time, int index){
        this.time[index] = time;
    }
    void setURL(String URL) { this.URL = URL; }
    String getName(){
        return this.name;
    }
    String getPlace_id(){
        return this.place_id;
    }
    double getRating(){
        return this.rating;
    }
    String getWeekday_text(){
        String output = "";
        for(int i=0; i<7; i++) {
            output += this.weekday_text[i] + "\n";
        }
        return output;
    }
    String getFormatted_address(){
        return formatted_address;
    }
}
