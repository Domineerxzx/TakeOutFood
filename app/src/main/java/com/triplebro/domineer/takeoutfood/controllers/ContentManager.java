package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;
import java.util.List;

public class ContentManager {

    private Context context;

    public ContentManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getFoodInfoList(int type_concrete_id) {

        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> foodInfoList = databaseOP.getFoodInfoList(type_concrete_id);
        return foodInfoList;
    }
}
