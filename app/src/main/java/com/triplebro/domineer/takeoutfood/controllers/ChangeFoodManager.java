package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class ChangeFoodManager {

    private Context context;

    public ChangeFoodManager(Context context) {
        this.context = context;
    }

    public List<TypeGeneralizeInfo> getFoodGeneralizeType() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<TypeGeneralizeInfo> foodGeneralizeType = databaseOP.getFoodGeneralizeType();
        return foodGeneralizeType;
    }

    public List<TypeConcreteInfo> getFoodConcreteType(int type_generalize_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<TypeConcreteInfo> foodConcreteType = databaseOP.getFoodConcreteType(type_generalize_id);
        return foodConcreteType;
    }

    public TypeGeneralizeInfo getFoodGeneralizeTypeInfo(int type_generalize_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        TypeGeneralizeInfo foodGeneralizeType = databaseOP.getFoodGeneralizeTypeInfo(type_generalize_id);
        return foodGeneralizeType;
    }

    public TypeConcreteInfo getFoodConcreteTypeInfo(int type_concrete_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        TypeConcreteInfo foodConcreteType = databaseOP.getFoodConcreteTypeInfo(type_concrete_id);
        return foodConcreteType;
    }

    public int addFoodInfo(ContentValues foodInfo) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        int food_id = databaseOP.addFoodInfo(foodInfo);
        return food_id;
    }

    public void addFoodSizeInfo(int food_id, List<FoodSizeInfo> foodSizeInfoList, String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        for (FoodSizeInfo foodSizeInfo : foodSizeInfoList) {
            ContentValues sizeInfo = new ContentValues();
            sizeInfo.put("food_id",food_id);
            sizeInfo.put("size_name",foodSizeInfo.getSize_name());
            sizeInfo.put("size_count",foodSizeInfo.getSize_count());
            sizeInfo.put("phone_number",phone_number);
            databaseOP.addFoodSizeInfo(sizeInfo);
        }
    }

    public void addFoodImageInfo(int food_id, List<String> foodImageList, String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        if(foodImageList.get(foodImageList.size()-1).length() == 0){
            foodImageList.remove(foodImageList.size()-1);
        }
        for (String food_image : foodImageList) {
            ContentValues imageInfo = new ContentValues();
            imageInfo.put("food_id",food_id);
            imageInfo.put("food_image",food_image);
            imageInfo.put("phone_number",phone_number);
            databaseOP.addFoodImageInfo(imageInfo);
        }
    }

    public List<String> getFoodImageList(int food_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<String> foodImageList = databaseOP.getFoodImageList(food_id);
        return foodImageList;
    }

    public List<FoodSizeInfo> getFoodSizeInfoList(int food_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodSizeInfo> foodSizeInfoList = databaseOP.getFoodSizeInfoList(food_id);
        return foodSizeInfoList;
    }

    public void deleteFood(int food_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.deleteFood(food_id);
    }
}
