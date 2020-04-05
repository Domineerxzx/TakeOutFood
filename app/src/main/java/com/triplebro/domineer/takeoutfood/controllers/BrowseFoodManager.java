package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;


public class BrowseFoodManager {

    private Context context;

    public BrowseFoodManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> foodInfoList = databaseOP.getFoodInfoList(phone_number);
        return foodInfoList;
    }
}
