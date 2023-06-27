package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

public class SavedDTO {
    List<FoodDTO> savedFood = new ArrayList<>();
    List<HouseDTO> savedHouse = new ArrayList<>();

    public List<FoodDTO> getSavedFood() {
        return savedFood;
    }

    public void setSavedFood(FoodDTO savedFood) {
        this.savedFood.add(savedFood);
    }

    public List<HouseDTO> getSavedHouse() {
        return savedHouse;
    }

    public void setSavedHouse(HouseDTO savedHouse) {
        this.savedHouse.add(savedHouse);
    }
}
