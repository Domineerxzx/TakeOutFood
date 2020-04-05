package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;


public class SearchManager {

    private Context context;

    public SearchManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodRecommendInfoList() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> foodRecommendInfoList = databaseOP.getFoodRecommendInfoList();
        return foodRecommendInfoList;
    }

    public List<FoodInfo> searchInfoList(String searchKey) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> searchInfoList = databaseOP.getSearchInfoList(searchKey);
        return searchInfoList;
    }
}
