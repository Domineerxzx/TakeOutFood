package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class DeleteFoodManager {

    private Context context;

    public DeleteFoodManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> foodInfoList = databaseOP.getFoodInfoList(phone_number);
        return foodInfoList;
    }

    public void deleteFood(int food_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.deleteFood(food_id);
    }
}
