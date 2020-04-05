package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class AddFoodController {

    private Context context;

    public AddFoodController(Context context) {
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
        foodImageList.remove(foodImageList.size()-1);
        for (String food_image : foodImageList) {
            ContentValues imageInfo = new ContentValues();
            imageInfo.put("food_id",food_id);
            if(food_image.length() == 0){
                Toast.makeText(context, "商品图片不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            imageInfo.put("food_image",food_image);
            imageInfo.put("phone_number",phone_number);
            databaseOP.addFoodImageInfo(imageInfo);
        }
    }
}
