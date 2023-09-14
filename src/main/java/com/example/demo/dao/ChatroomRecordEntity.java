package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("chatroomRecord") //collection name
public class ChatroomRecordEntity {

    String from = "";
    String text = "";
    String atWhere = "";

    public String getfrom() {
        return from;
    }

    public void setfrom(String from) {
        this.from = from;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public String getatWhere() {
        return atWhere;
    }

    public void setatWhere(String atWhere) {
        this.atWhere = atWhere;
    }


}
