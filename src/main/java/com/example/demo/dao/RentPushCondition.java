package com.example.demo.dao;

import com.example.demo.dao.FoodEntity.p;

public class RentPushCondition {
    private String h_rent;
    private String h_gender;
    private String h_people;  //人數
    private String[] h_style;  //房型
    private String[] h_region;    //地區
    private String h_floor;
    private String h_parking;
    private String[] h_power;   //電費
    private String[] h_water;   //水費

    public RentPushCondition(){}

    public RentPushCondition(String rent, String gender, String people, String[] style, String[] region, String floor, String parking, String[] water, String[] power){
        this.h_rent = rent;
        this.h_gender = gender;
        this.h_people = people;
        this.h_style = style;
        this.h_region = region;
        this.h_floor = floor;
        this.h_parking = parking;
        this.h_power = power;
        this.h_water = water;
    }

    public String getRent(){
        return h_rent;
    }
    public String getGender(){
        return h_gender;
    }
    public String getPeople(){
        return h_people;
    }
    public String[] getType(){
        return h_style;
    }
    public String[] getRegion(){
        return h_region;
    }
    public String getFloor(){
        return h_floor;
    }
    public String getParking(){
        return h_parking;
    }
    public String[] getPower(){
        return h_power;
    }
    public String[] getWater(){
        return h_water;
    }
}
