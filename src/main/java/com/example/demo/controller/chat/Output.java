package com.example.demo.controller.chat;

public class Output {
    private String dateStr;
    private Message message;

    public Output(String dateStr, Message message) {
        this.dateStr = dateStr;
        this.message = message;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
