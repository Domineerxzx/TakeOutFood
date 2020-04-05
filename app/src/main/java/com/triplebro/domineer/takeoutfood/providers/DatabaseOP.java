package com.triplebro.domineer.takeoutfood.providers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.models.AdminInfo;
import com.triplebro.domineer.takeoutfood.models.CollectionFoodInfo;
import com.triplebro.domineer.takeoutfood.models.CollectionSubmitInfo;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.UploadUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOP implements ISource {

    private Context context;

    public DatabaseOP(Context context) {
        this.context = context;
    }

    public boolean getIsCollection(int food_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        } else {
            TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
            SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
            Cursor collectionFoodInfo = db.query("collectionFoodInfo", null, "food_id = ? and phone_number = ?", new String[]{String.valueOf(food_id), phone_number}, null, null, null);
            if (collectionFoodInfo != null && collectionFoodInfo.getCount() > 0) {
                collectionFoodInfo.moveToNext();
                collectionFoodInfo.close();
                db.close();
                return true;
            } else {
                collectionFoodInfo.close();
                db.close();
                return false;
            }
        }
    }

    public boolean getIsCollectionSubmit(int submit_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        } else {
            TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
            SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfo = db.query("collectionSubmitInfo", null, "submit_id = ? and phone_number = ?", new String[]{String.valueOf(submit_id), phone_number}, null, null, null);
            if (collectionSubmitInfo != null && collectionSubmitInfo.getCount() > 0) {
                collectionSubmitInfo.moveToNext();
                collectionSubmitInfo.close();
                db.close();
                return true;
            } else {
                collectionSubmitInfo.close();
                db.close();
                return false;
            }
        }
    }

    public List<CollectionFoodInfo> getFoodCollectionInfoList(String phone_number) {
        List<CollectionFoodInfo> collectionFoodInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionFoodInfoList;
        } else {
            TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
            SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
            Cursor collectionFoodInfoCursor = db.query("collectionFoodInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (collectionFoodInfoCursor != null && collectionFoodInfoCursor.getCount() > 0) {
                while (collectionFoodInfoCursor.moveToNext()) {
                    CollectionFoodInfo collectionFoodInfo = new CollectionFoodInfo();
                    collectionFoodInfo.setPhone_number(phone_number);
                    collectionFoodInfo.setFood_id(collectionFoodInfoCursor.getInt(2));
                    collectionFoodInfoList.add(collectionFoodInfo);
                }
                collectionFoodInfoCursor.close();
                db.close();
                return collectionFoodInfoList;
            } else {
                if (collectionFoodInfoCursor != null) {
                    collectionFoodInfoCursor.close();
                }
                db.close();
                return collectionFoodInfoList;
            }
        }
    }

    public List<CollectionSubmitInfo> getSubmitCollectionInfoList(String phone_number) {
        List<CollectionSubmitInfo> collectionSubmitInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionSubmitInfoList;
        } else {
            TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
            SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfoCursor = db.query("collectionSubmitInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (collectionSubmitInfoCursor != null && collectionSubmitInfoCursor.getCount() > 0) {
                while (collectionSubmitInfoCursor.moveToNext()) {
                    CollectionSubmitInfo collectionSubmitInfo = new CollectionSubmitInfo();
                    collectionSubmitInfo.setPhone_number(phone_number);
                    collectionSubmitInfo.setSubmit_id(collectionSubmitInfoCursor.getInt(2));
                    collectionSubmitInfoList.add(collectionSubmitInfo);
                }
                collectionSubmitInfoCursor.close();
                db.close();
                return collectionSubmitInfoList;
            } else {
                if (collectionSubmitInfoCursor != null) {
                    collectionSubmitInfoCursor.close();
                }
                db.close();
                return collectionSubmitInfoList;
            }
        }
    }

    public List<FoodInfo> getCollectedFoodInfoList(List<CollectionFoodInfo> collectionInfoList) {
        List<FoodInfo> foodInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        for (CollectionFoodInfo collectionFoodInfo : collectionInfoList) {
            int food_id = collectionFoodInfo.getFood_id();
            Cursor foodInfoCursor = db.query("foodInfo", null, "food_id = ?", new String[]{String.valueOf(food_id)}, null, null, null);
            if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
                foodInfoCursor.moveToNext();
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodInfoCursor.getInt(0));
                foodInfo.setFood_name(foodInfoCursor.getString(1));
                foodInfo.setPrice(foodInfoCursor.getInt(2));
                foodInfo.setFood_image(foodInfoCursor.getString(3));
                foodInfo.setType_generalize_id(foodInfoCursor.getInt(4));
                foodInfo.setType_concrete_id(foodInfoCursor.getInt(5));
                foodInfo.setPhone_number(foodInfoCursor.getString(6));
                foodInfoList.add(foodInfo);
                foodInfoCursor.close();
            } else {
                if (foodInfoCursor != null) {
                    foodInfoCursor.close();
                }
            }
        }
        db.close();
        return foodInfoList;
    }

    public List<SubmitInfo> getCollectedSubmitInfoList(List<CollectionSubmitInfo> collectionInfoList) {
        List<SubmitInfo> submitInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        for (CollectionSubmitInfo collectionSubmitInfo : collectionInfoList) {
            int submit_id = collectionSubmitInfo.getSubmit_id();
            Cursor submitInfoCursor = db.query("submitInfo", null, "submit_id = ?", new String[]{String.valueOf(submit_id)}, null, null, null);
            if (submitInfoCursor != null && submitInfoCursor.getCount() > 0) {
                submitInfoCursor.moveToNext();
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setSubmit_id(submitInfoCursor.getInt(0));
                submitInfo.setPhone_number(submitInfoCursor.getString(1));
                submitInfo.setNickname(submitInfoCursor.getString(2));
                submitInfo.setUser_head(submitInfoCursor.getString(3));
                submitInfo.setSubmit_content(submitInfoCursor.getString(4));
                submitInfoList.add(submitInfo);
                submitInfoCursor.close();
            } else {
                if (submitInfoCursor != null) {
                    submitInfoCursor.close();
                }
            }
        }
        db.close();
        return submitInfoList;
    }

    public List<FoodInfo> getFoodRecommendInfoList() {
        List<FoodInfo> foodInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor foodRecommendInfoCursor = db.query("foodInfo", null, null, null, null, null, "food_id desc");
        if (foodRecommendInfoCursor != null && foodRecommendInfoCursor.getCount() > 0) {
            while (foodRecommendInfoCursor.moveToNext()) {
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodRecommendInfoCursor.getInt(0));
                foodInfo.setFood_name(foodRecommendInfoCursor.getString(1));
                foodInfo.setPrice(foodRecommendInfoCursor.getInt(2));
                foodInfo.setFood_image(foodRecommendInfoCursor.getString(3));
                foodInfoList.add(foodInfo);
            }
        }
        if (foodRecommendInfoCursor != null) {
            foodRecommendInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public List<FoodInfo> getSearchInfoList(String searchKey) {
        List<FoodInfo> foodInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor foodInfoCursor = db.query("foodInfo", new String[]{"food_id", "food_name", "price", "food_image"}, "food_name like ?", new String[]{"%" + searchKey + "%"}, null, null, null);
        if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
            while (foodInfoCursor.moveToNext()) {
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodInfoCursor.getInt(0));
                foodInfo.setFood_name(foodInfoCursor.getString(1));
                foodInfo.setPrice(foodInfoCursor.getInt(2));
                foodInfo.setFood_image(foodInfoCursor.getString(3));
                foodInfoList.add(foodInfo);
            }
        }
        if (foodInfoCursor != null) {
            foodInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public List<FoodInfo> getTypeRecommendFoodList(int type_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<FoodInfo> foodInfoList = new ArrayList<>();
        Cursor foodInfoCursor;
        switch (type_id) {
            case 0:
                foodInfoCursor = db.query("foodInfo", new String[]{"food_id", "food_name", "price", "food_image"}, "type_generalize_id = ? or type_generalize_id = ?", new String[]{String.valueOf(1), String.valueOf(2)}, null, null, null);
                if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
                    while (foodInfoCursor.moveToNext()) {
                        FoodInfo foodInfo = new FoodInfo();
                        foodInfo.setFood_id(foodInfoCursor.getInt(0));
                        foodInfo.setFood_name(foodInfoCursor.getString(1));
                        foodInfo.setPrice(foodInfoCursor.getInt(2));
                        foodInfo.setFood_image(foodInfoCursor.getString(3));
                        foodInfoList.add(foodInfo);
                    }
                }
                break;
            default:
                foodInfoCursor = db.query("foodInfo", new String[]{"food_id", "food_name", "price", "food_image"}, "type_generalize_id = ?", new String[]{String.valueOf(type_id)}, null, null, null);
                if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
                    while (foodInfoCursor.moveToNext()) {
                        FoodInfo foodInfo = new FoodInfo();
                        foodInfo.setFood_id(foodInfoCursor.getInt(0));
                        foodInfo.setFood_name(foodInfoCursor.getString(1));
                        foodInfo.setPrice(foodInfoCursor.getInt(2));
                        foodInfo.setFood_image(foodInfoCursor.getString(3));
                        foodInfoList.add(foodInfo);
                    }
                }
                break;
        }
        if (foodInfoCursor != null) {
            foodInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public boolean addFoodCollection(int food_id) {

        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number == null || phone_number.length() == 0) {
            Toast.makeText(context, "还没登录呢，不能收藏商品", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone_number", phone_number);
            contentValues.put("food_id", food_id);
            long collectionFoodInfo = db.insert("collectionFoodInfo", null, contentValues);
            if (collectionFoodInfo >= 0) {
                Toast.makeText(context, "添加收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "添加收藏失败", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return true;
        }
    }

    public boolean deleteFoodCollection(int food_id) {

        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int collectionFoodInfo = db.delete("collectionFoodInfo", "phone_number = ? and food_id = ?", new String[]{phone_number, String.valueOf(food_id)});
        if (collectionFoodInfo >= 0) {
            Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        } else {
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        }
    }

    public List<TypeGeneralizeInfo> getFoodGeneralizeType() {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<TypeGeneralizeInfo> typeGeneralizeInfoList = new ArrayList<>();
        Cursor typeGeneralize = db.query("typeGeneralize", null, null, null, null, null, null);
        if (typeGeneralize != null && typeGeneralize.getCount() > 0) {
            while (typeGeneralize.moveToNext()) {
                TypeGeneralizeInfo typeGeneralizeInfo = new TypeGeneralizeInfo();
                typeGeneralizeInfo.setType_generalize_id(typeGeneralize.getInt(0));
                typeGeneralizeInfo.setType_generalize_name(typeGeneralize.getString(1));
                typeGeneralizeInfoList.add(typeGeneralizeInfo);
            }
        }
        if (typeGeneralize != null) {
            typeGeneralize.close();
        }
        db.close();
        return typeGeneralizeInfoList;
    }

    public List<TypeConcreteInfo> getFoodConcreteType(int type_generalize_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<TypeConcreteInfo> typeConcreteInfoList = new ArrayList<>();
        Cursor typeConcrete = db.query("typeConcrete", null, "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if (typeConcrete != null && typeConcrete.getCount() > 0) {
            while (typeConcrete.moveToNext()) {
                TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
                typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
                typeConcreteInfo.setType_concrete_name(typeConcrete.getString(2));
                typeConcreteInfoList.add(typeConcreteInfo);
            }
        }
        if (typeConcrete != null) {
            typeConcrete.close();
        }
        db.close();
        return typeConcreteInfoList;
    }

    public TypeGeneralizeInfo getFoodGeneralizeTypeInfo(int type_generalize_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor typeGeneralize = db.query("typeGeneralize", null, "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if (typeGeneralize != null && typeGeneralize.getCount() > 0) {
            typeGeneralize.moveToNext();
            TypeGeneralizeInfo typeGeneralizeInfo = new TypeGeneralizeInfo();
            typeGeneralizeInfo.setType_generalize_id(typeGeneralize.getInt(0));
            typeGeneralizeInfo.setType_generalize_name(typeGeneralize.getString(1));
            typeGeneralize.close();
            return typeGeneralizeInfo;
        }
        return null;
    }

    public TypeConcreteInfo getFoodConcreteTypeInfo(int type_concrete_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor typeConcrete = db.query("typeConcrete", null, "type_concrete_id = ?", new String[]{String.valueOf(type_concrete_id)}, null, null, null);
        if (typeConcrete != null && typeConcrete.getCount() > 0) {
            typeConcrete.moveToNext();
            TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
            typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
            typeConcreteInfo.setType_concrete_name(typeConcrete.getString(2));
            typeConcrete.close();
            db.close();
            return typeConcreteInfo;
        }
        return null;
    }


    public int addFoodInfo(ContentValues foodInfo) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long insert = db.insert("foodInfo", null, foodInfo);
        OssHandler ossHandler = new OssHandler(context);
        UploadUtils.uploadFileToOss(ossHandler, ProjectProperties.BUCKET_NAME, "xuzhanxin/" + foodInfo.getAsString("food_image"), foodInfo.getAsString("food_image"));
        if (insert != -1) {
            Toast.makeText(context, "添加商品信息成功", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.query("foodInfo", new String[]{"food_id"}, "phone_number = ?", new String[]{foodInfo.getAsString("phone_number")}, null, null, "food_id desc");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                int food_id = cursor.getInt(0);
                cursor.close();
                db.close();
                return food_id;
            }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return -1;
        } else {
            Toast.makeText(context, "添加商品信息失败", Toast.LENGTH_SHORT).show();
            db.close();
            return -1;
        }
    }

    public void addFoodSizeInfo(ContentValues sizeInfo) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long foodSizeInfo = db.insert("foodSizeInfo", null, sizeInfo);
        if (foodSizeInfo != -1) {
            Toast.makeText(context, "添加商品尺码及库存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加商品尺码及库存失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void addFoodImageInfo(ContentValues imageInfo) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long foodSizeInfo = db.insert("foodImageInfo", null, imageInfo);
        OssHandler ossHandler = new OssHandler(context);
        UploadUtils.uploadFileToOss(ossHandler, ProjectProperties.BUCKET_NAME, "xuzhanxin/" + imageInfo.getAsString("food_image"), imageInfo.getAsString("food_image"));
        if (foodSizeInfo != -1) {
            Toast.makeText(context, "添加商品图片成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加商品图片失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<FoodInfo> getFoodInfoList(String phone_number) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<FoodInfo> foodInfoList = new ArrayList<>();
        Cursor foodInfoCursor = db.query("foodInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
            while (foodInfoCursor.moveToNext()) {
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodInfoCursor.getInt(0));
                foodInfo.setFood_name(foodInfoCursor.getString(1));
                foodInfo.setPrice(foodInfoCursor.getInt(2));
                foodInfo.setFood_image(foodInfoCursor.getString(3));
                foodInfo.setType_generalize_id(foodInfoCursor.getInt(4));
                foodInfo.setType_concrete_id(foodInfoCursor.getInt(5));
                foodInfo.setPhone_number(foodInfoCursor.getString(6));
                foodInfoList.add(foodInfo);
            }
        }
        if (foodInfoCursor != null) {
            foodInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public List<FoodInfo> getFoodInfoList() {
        List<FoodInfo> foodInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor foodRecommendInfoCursor = db.query("foodInfo", null, null, null, null, null, null);
        if (foodRecommendInfoCursor != null && foodRecommendInfoCursor.getCount() > 0) {
            while (foodRecommendInfoCursor.moveToNext()) {
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodRecommendInfoCursor.getInt(0));
                foodInfo.setFood_name(foodRecommendInfoCursor.getString(1));
                foodInfo.setPrice(foodRecommendInfoCursor.getInt(2));
                foodInfo.setFood_image(foodRecommendInfoCursor.getString(3));
                foodInfoList.add(foodInfo);
            }
        }
        if (foodRecommendInfoCursor != null) {
            foodRecommendInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public List<String> getFoodImageList(int food_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<String> foodImageList = new ArrayList<>();
        Cursor foodImageInfo = db.query("foodImageInfo", null, "food_id = ?", new String[]{String.valueOf(food_id)}, null, null, null);
        if (foodImageInfo != null && foodImageInfo.getCount() > 0) {
            while (foodImageInfo.moveToNext()) {
                String s = foodImageInfo.getString(2);
                foodImageList.add(s);
            }
        }
        if (foodImageInfo != null) {
            foodImageInfo.close();
        }
        db.close();
        return foodImageList;
    }

    public List<FoodSizeInfo> getFoodSizeInfoList(int food_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<FoodSizeInfo> foodSizeInfoList = new ArrayList<>();
        Cursor foodSizeInfoCursor = db.query("foodSizeInfo", null, "food_id = ?", new String[]{String.valueOf(food_id)}, null, null, null);
        if (foodSizeInfoCursor != null && foodSizeInfoCursor.getCount() > 0) {
            while (foodSizeInfoCursor.moveToNext()) {
                FoodSizeInfo foodSizeInfo = new FoodSizeInfo();
                foodSizeInfo.setSize_name(foodSizeInfoCursor.getString(2));
                foodSizeInfo.setSize_count(foodSizeInfoCursor.getInt(3));
                foodSizeInfoList.add(foodSizeInfo);
            }
        }
        if (foodSizeInfoCursor != null) {
            foodSizeInfoCursor.close();
        }
        db.close();
        return foodSizeInfoList;
    }

    public void deleteFood(int food_id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.delete("foodImageInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
        db.delete("foodSizeInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
        db.delete("collectionFoodInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
        db.delete("shoppingCartInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
        db.delete("foodRecommendInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
        db.delete("foodInfo", "food_id = ?", new String[]{String.valueOf(food_id)});
    }

    public List<TypeConcreteInfo> getConcreteTypeList(int type_generalize_id) {
        List<TypeConcreteInfo> typeConcreteInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor typeConcrete = db.query("typeConcrete", new String[]{"type_concrete_id", "type_concrete_name", "type_concrete_image"},
                "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if (typeConcrete != null && typeConcrete.getCount() > 0) {
            while (typeConcrete.moveToNext()) {
                TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
                typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
                typeConcreteInfo.setType_concrete_name(typeConcrete.getString(1));
                typeConcreteInfo.setType_concrete_image(typeConcrete.getString(2));
                typeConcreteInfoList.add(typeConcreteInfo);
            }
        }
        if (typeConcrete != null) {
            typeConcrete.close();
        }
        db.close();
        return typeConcreteInfoList;
    }

    public List<TypeGeneralizeInfo> getGeneralizeTypeList() {
        List<TypeGeneralizeInfo> typeGeneralizeInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor typeGeneralize = db.query("typeGeneralize", null, null, null, null, null, null);
        if (typeGeneralize != null && typeGeneralize.getCount() > 0) {
            while (typeGeneralize.moveToNext()) {
                TypeGeneralizeInfo typeGeneralizeInfo = new TypeGeneralizeInfo();
                typeGeneralizeInfo.setType_generalize_id(typeGeneralize.getInt(0));
                typeGeneralizeInfo.setType_generalize_name(typeGeneralize.getString(1));
                typeGeneralizeInfoList.add(typeGeneralizeInfo);
            }
        }
        if (typeGeneralize != null) {
            typeGeneralize.close();
        }
        db.close();
        return typeGeneralizeInfoList;
    }

    public List<String> getSubmitImageInfoList(int submit_id) {
        List<String> submitImageList = new ArrayList<String>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor submitImageInfoCursor = db.query("submitImageInfo", new String[]{"submit_image"}, "submit_id = ?", new String[]{String.valueOf(submit_id)}, null, null, null);
        if (submitImageInfoCursor != null && submitImageInfoCursor.getCount() > 0) {
            while (submitImageInfoCursor.moveToNext()) {
                String submitImage = submitImageInfoCursor.getString(0);
                submitImageList.add(submitImage);
            }
        }
        if (submitImageInfoCursor != null) {
            submitImageInfoCursor.close();
        }
        db.close();
        return submitImageList;
    }

    public List<String> getBannerImageList() {
        List<String> bannerImageList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor foodRecommendInfo = db.query("foodInfo",
                new String[]{"food_image"}, null, null,
                null, null, "food_id desc");
        if (foodRecommendInfo != null && foodRecommendInfo.getCount() > 0) {
            while (foodRecommendInfo.moveToNext()) {
                String recommendImage = foodRecommendInfo.getString(0);
                bannerImageList.add(recommendImage);
                if (bannerImageList.size() == 5) {
                    break;
                }
            }
        }
        if (foodRecommendInfo != null) {
            foodRecommendInfo.close();
        }
        db.close();
        return bannerImageList;
    }

    public List<FoodInfo> getFoodInfoList(int type_concrete_id) {
        List<FoodInfo> foodInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor foodInfoCursor = db.query("foodInfo", null, "type_concrete_id = ?", new String[]{String.valueOf(type_concrete_id)}, null, null, null);
        if (foodInfoCursor != null && foodInfoCursor.getCount() > 0) {
            while (foodInfoCursor.moveToNext()) {
                FoodInfo foodInfo = new FoodInfo();
                foodInfo.setFood_id(foodInfoCursor.getInt(0));
                foodInfo.setFood_name(foodInfoCursor.getString(1));
                foodInfo.setPrice(foodInfoCursor.getInt(2));
                foodInfo.setFood_image(foodInfoCursor.getString(3));
                foodInfo.setType_generalize_id(foodInfoCursor.getInt(4));
                foodInfo.setType_concrete_id(foodInfoCursor.getInt(5));
                foodInfo.setPhone_number(foodInfoCursor.getString(6));
                foodInfoList.add(foodInfo);
            }
        }
        if (foodInfoCursor != null) {
            foodInfoCursor.close();
        }
        db.close();
        return foodInfoList;
    }

    public boolean checkTableIsExist(String tableName) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor isExist = db.query(tableName, null, null, null, null, null, null);
        if (isExist.getCount() > 0) {
            isExist.close();
            db.close();
            return true;
        } else {
            isExist.close();
            db.close();
            return false;
        }
    }

    public void insertUserInfo(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("userInfo", null, contentValues);
        db.close();
    }

    public void insertAdminInfo(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("adminInfo", null, contentValues);
        db.close();
    }

    public void deleteTable() {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.delete("adminInfo", null, null);
        db.delete("userInfo", null, null);
        db.delete("typeConcrete", null, null);
        db.delete("typeGeneralize", null, null);
    }

    public void insertTypeGeneralize(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("typeGeneralize", null, contentValues);
        db.close();
    }

    public void insertTypeConcrete(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("typeConcrete", null, contentValues);
        db.close();
    }

    public long commitOrder(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long orderInfo = db.insert("orderInfo", null, contentValues);
        db.close();
        return orderInfo;
    }

    public List<OrderInfo> getAllOrderInfoList(String phone_number) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<OrderInfo> allOrderInfoList = new ArrayList<>();
        Cursor orderInfoCursor = db.query("orderInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (orderInfoCursor != null && orderInfoCursor.getCount() > 0) {
            while (orderInfoCursor.moveToNext()) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.set_id(orderInfoCursor.getInt(0));
                orderInfo.setOrder_state(orderInfoCursor.getInt(1));
                orderInfo.setPhone_number(orderInfoCursor.getString(2));
                allOrderInfoList.add(orderInfo);
            }
        }
        if (orderInfoCursor != null) {
            orderInfoCursor.close();
        }
        db.close();
        return allOrderInfoList;
    }

    public List<ShoppingCartInfo> getOrderContentInfoList(int order_id) {
        List<ShoppingCartInfo> shoppingCartInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<Integer> shoppingCartIdList = new ArrayList<>();
        Cursor orderContentInfo = db.query("orderContentInfo", null, "order_id = ?", new String[]{String.valueOf(order_id)}, null, null, null);
        if (orderContentInfo != null && orderContentInfo.getCount() > 0) {
            while (orderContentInfo.moveToNext()) {
                shoppingCartIdList.add(orderContentInfo.getInt(2));
            }
        }
        if (orderContentInfo != null) {
            orderContentInfo.close();
        }
        for (Integer shoppingCartId :
                shoppingCartIdList) {
            Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", null, "_id = ? and isCommit = ?", new String[]{String.valueOf(shoppingCartId),"1"}, null, null, null);
            if(shoppingCartInfoCursor!= null&&shoppingCartInfoCursor.getCount()>0){
                shoppingCartInfoCursor.moveToNext();
                ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
                shoppingCartInfo.set_id(shoppingCartInfoCursor.getInt(0));
                shoppingCartInfo.setFood_id(shoppingCartInfoCursor.getInt(1));
                shoppingCartInfo.setSize_name(shoppingCartInfoCursor.getString(2));
                shoppingCartInfo.setCount(shoppingCartInfoCursor.getInt(3));
                shoppingCartInfo.setFood_name(shoppingCartInfoCursor.getString(4));
                shoppingCartInfo.setPhone_number(shoppingCartInfoCursor.getString(5));
                shoppingCartInfo.setFood_image(shoppingCartInfoCursor.getString(6));
                shoppingCartInfo.setPrice(shoppingCartInfoCursor.getInt(7));
                shoppingCartInfo.setIsCommit(shoppingCartInfoCursor.getInt(8));
                shoppingCartInfoList.add(shoppingCartInfo);
            }
        }
        return shoppingCartInfoList;
    }

    public void updateShoppingCartInfo(ContentValues contentValues, int id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.update("shoppingCartInfo",contentValues,"_id = ?",new String[]{String.valueOf(id)});
    }

    public void commitOrderContent(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("orderContentInfo", null, contentValues);
        db.close();
    }

    public List<OrderInfo> getStateOrderInfoList(String phone_number, int orderState) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<OrderInfo> stateOrderInfoList = new ArrayList<>();
        Cursor orderInfoCursor = db.query("orderInfo", null, "phone_number = ? and order_state = ?", new String[]{phone_number,String.valueOf(orderState)}, null, null, null);
        if (orderInfoCursor != null && orderInfoCursor.getCount() > 0) {
            while (orderInfoCursor.moveToNext()) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.set_id(orderInfoCursor.getInt(0));
                orderInfo.setOrder_state(orderInfoCursor.getInt(1));
                orderInfo.setPhone_number(orderInfoCursor.getString(2));
                stateOrderInfoList.add(orderInfo);
            }
        }
        if (orderInfoCursor != null) {
            orderInfoCursor.close();
        }
        db.close();
        return stateOrderInfoList;
    }

    public void updateWaitGetToWaitEvaluate(int id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_state",ProjectProperties.ORDER_STATE_WAIT_EVALYATE);
        db.update("orderInfo",contentValues,"_id = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateWaitPayToWaitSend(int id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_state",ProjectProperties.ORDER_STATE_WAIT_SEND);
        db.update("orderInfo",contentValues,"_id = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateWaitSendToWaitGet(int id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_state",ProjectProperties.ORDER_STATE_WAIT_GET);
        db.update("orderInfo",contentValues,"_id = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateWaitEvaluateToDone(int id) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_state",ProjectProperties.ORDER_STATE_DONE);
        db.update("orderInfo",contentValues,"_id = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void insertFoodInfo(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("foodInfo", null, contentValues);
        db.close();
    }

    public void insertFoodImageInfo(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("foodImageInfo", null, contentValues);
        db.close();
    }

    public void insertFoodSizeInfo(ContentValues contentValues) {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        db.insert("foodSizeInfo", null, contentValues);
        db.close();
    }

    public List<AdminInfo> getAllShopInfo() {
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        List<AdminInfo> adminInfoList = new ArrayList<>();
        Cursor adminInfoCursor = db.query("adminInfo", null, null, null, null, null, null);
        if (adminInfoCursor != null && adminInfoCursor.getCount() > 0) {
            while (adminInfoCursor.moveToNext()) {
                AdminInfo adminInfo = new AdminInfo();
                adminInfo.setPhone_number(adminInfoCursor.getString(0));
                adminInfo.setPassword(adminInfoCursor.getString(1));
                adminInfo.setNickname(adminInfoCursor.getString(2));
                adminInfo.setUser_head(adminInfoCursor.getString(3));
                adminInfoList.add(adminInfo);
            }
        }
        if (adminInfoCursor != null) {
            adminInfoCursor.close();
        }
        db.close();
        return adminInfoList;
    }
}
