package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class FoodDetailsManager {

    private Context context;

    public FoodDetailsManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodInfoList() {
        List<FoodInfo> foodInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        foodInfoList = databaseOP.getFoodInfoList();
        return foodInfoList;
    }

    public List<String> getFoodImagePathList(int food_id) {
        List<String> foodImagePathList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        foodImagePathList = databaseOP.getFoodImageList(food_id);
        return foodImagePathList;
    }

    public List<FoodSizeInfo> getFoodSizeInfoList(int food_id) {
        List<FoodSizeInfo> foodSizeInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        foodSizeInfoList = databaseOP.getFoodSizeInfoList(food_id);
        return foodSizeInfoList;
    }
}
