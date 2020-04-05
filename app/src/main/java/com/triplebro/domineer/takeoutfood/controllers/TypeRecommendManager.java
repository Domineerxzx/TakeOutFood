package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class TypeRecommendManager {

    private Context context;
    private List<FoodInfo> typeRecommendFoodList;

    public TypeRecommendManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodList(int type_id){
        DatabaseOP databaseOP = new DatabaseOP(context);
        typeRecommendFoodList = databaseOP.getTypeRecommendFoodList(type_id);
        return typeRecommendFoodList;
    }
}
