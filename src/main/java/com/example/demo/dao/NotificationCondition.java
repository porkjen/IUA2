package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "NotificationCollection")
public class NotificationCondition {
    private String id;
    private String studentID;
    private RentPushCondition rentCondition;
    private ArrayList<ExchangePushCondition> exchangeCondition;
    private ArrayList<String> desireClasses = new ArrayList<>();

    public NotificationCondition(){}

    public NotificationCondition(String studentID){
        this.studentID = studentID;
    }

    public String getStudentID(){
        return studentID;
    }
    public void setRentCondition(RentPushCondition rentCondition){
        this.rentCondition = rentCondition;
    }
    public RentPushCondition getRentCondition(){
        return rentCondition;
    }
    public void setExchangeCondition(ArrayList<ExchangePushCondition> exchangeCondition){
        this.exchangeCondition = exchangeCondition;
    }
    public ArrayList<ExchangePushCondition> getExchangeCondition(){
        return exchangeCondition;
    }

    public ArrayList<String> getDesireClasses() {
        return desireClasses;
    }

    public void setDesireClasses(String desireClass) {
        this.desireClasses.add(desireClass);
    }
}
