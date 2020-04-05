package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FoundManger {

    private Context context;

    public FoundManger(Context context) {
        this.context = context;
    }

    public List<SubmitInfo> getSubmitInfoList() {
        List<SubmitInfo> submitInfoList = new ArrayList<>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor submitInfoCursor = db.query("submitInfo", null, null, null, null, null, "submit_id desc");
        if (submitInfoCursor != null && submitInfoCursor.getCount() > 0) {
            while(submitInfoCursor.moveToNext()){
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setSubmit_id(submitInfoCursor.getInt(0));
                submitInfo.setPhone_number(submitInfoCursor.getString(1));
                submitInfo.setNickname(submitInfoCursor.getString(2));
                submitInfo.setUser_head(submitInfoCursor.getString(3));
                submitInfo.setSubmit_content(submitInfoCursor.getString(4));
                submitInfoList.add(submitInfo);
            }
        }
        if (submitInfoCursor != null) {
            submitInfoCursor.close();
        }
        db.close();
        return submitInfoList;
    }

    public boolean addSubmitCollection(int submit_id) {

        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number == null || phone_number.length() == 0) {
            Toast.makeText(context, "还没登录呢，不能收藏发布", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone_number", phone_number);
            contentValues.put("submit_id", submit_id);
            long collectionFoodInfo = db.insert("collectionSubmitInfo", null, contentValues);
            if (collectionFoodInfo >= 0) {
                Toast.makeText(context, "添加收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "添加收藏失败", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return true;
        }
    }


    public boolean deleteSubmitCollection(int submit_id) {

        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int collectionFoodInfo = db.delete("collectionSubmitInfo", "phone_number = ? and submit_id = ?", new String[]{phone_number, String.valueOf(submit_id)});
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
}
