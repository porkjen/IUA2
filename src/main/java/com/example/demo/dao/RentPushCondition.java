package com.example.demo.dao;

public class RentPushCondition {
    private String studentID;
    private String h_rent;
    private String h_gender;
    private String h_people;  //人數
    private String[] h_style;  //房型
    private String[] h_region;    //地區
    private String h_floor;
    private String h_parking;
    private String[] h_power;   //電費
    private String[] h_water;   //水費
    private int h_power_money = 0;
    private int h_water_money = 0;

    public RentPushCondition(){}

    public RentPushCondition(String studentID){
        this.studentID = studentID;
    }

    public String getStudentID(){
        return studentID;
    }
    public void setH_rent(String rent){
        this.h_rent = rent;
    }
    public String getRent(){
        return h_rent;
    }
    public void setH_gender(String gender){
        this.h_gender = gender;
    }
    public String getGender(){
        return h_gender;
    }
    public void setH_people(String people){
        this.h_people = people;
    }
    public String getPeople(){
        return h_people;
    }
    public void setH_style(String[] style){
        this.h_style = style;
    }
    public String[] getStyle(){
        return h_style;
    }
    public void setH_region(String[] region){
        this.h_region = region;
    }
    public String[] getRegion(){
        return h_region;
    }
    public void setH_floor(String floor){
        this.h_floor = floor;
    }
    public String getFloor(){
        return h_floor;
    }
    public void setH_parking(String parking){
        this.h_parking = parking;
    }
    public String getParking(){
        return h_parking;
    }
    public void setH_power(String[] power){
        this.h_power = power;
    }
    public String[] getPower(){
        return h_power;
    }
    public void setH_water(String[] water){
        this.h_water = water;
    }
    public String[] getWater(){
        return h_water;
    }
    public void setH_power_money(int power_money){
        this.h_power_money = power_money;
    }
    public int getPowerMoney(){
        return h_power_money;
    }
    public void setH_water_money(int water_money){
        this.h_water_money = water_money;
    }
    public int getWaterMoney(){
        return h_water_money;
    }
}
