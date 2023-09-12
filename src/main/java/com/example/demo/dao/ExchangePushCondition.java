package com.example.demo.dao;

public class ExchangePushCondition {
    private String c_number;
    private String c_time;
    private String c_category;

    public ExchangePushCondition(){}

    public ExchangePushCondition(String number, String time, String category){
        this.c_number = number;
        this.c_time = time;
        this.c_category = category;
    }

    public String getNumber(){
        return c_number;
    }
    public String getTime(){
        return c_time;
    }
    public String getCategory(){
        return c_category;
    }
}
