package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("ChatroomApiCollection") //collection name
public class ChatroomApiEntity {


    private String FirstStudentID;
    private String SecondStudentID = "";
    private String roomApi = "";

    public String getFirstStudentID() {
        return FirstStudentID;
    }

    public void setFirstStudentID(String FirstStudentID) {
        this.FirstStudentID = FirstStudentID;
    }

    public String getSecondStudentID() {
        return SecondStudentID;
    }

    public void setSecondStudentID(String SecondStudentID) {
        this.SecondStudentID = SecondStudentID;
    }

    public String getRoomApi() {
        return roomApi;
    }

    public void setRoomApi(String roomApi) {
        this.roomApi = roomApi;
    }

}
