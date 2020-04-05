package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.CollectionFoodInfo;
import com.triplebro.domineer.takeoutfood.models.CollectionSubmitInfo;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class CollectionManager {

    private Context context;

    public CollectionManager(Context context) {
        this.context = context;
    }

    public List<FoodInfo> getCollectedFoodInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CollectionFoodInfo> collectionInfoList = databaseOP.getFoodCollectionInfoList(phone_number);
        List<FoodInfo> collectedFoodInfoList = databaseOP.getCollectedFoodInfoList(collectionInfoList);
        return collectedFoodInfoList;
    }

    public List<SubmitInfo> getCollectionSubmitInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CollectionSubmitInfo> collectionInfoList = databaseOP.getSubmitCollectionInfoList(phone_number);
        List<SubmitInfo> collectedSubmitInfoList = databaseOP.getCollectedSubmitInfoList(collectionInfoList);
        return collectedSubmitInfoList;
    }
}
