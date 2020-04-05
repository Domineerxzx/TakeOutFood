package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.takeoutfood.models.SubmitImageInfo;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.UploadUtils;

public class SubmitManager {

    private Context context;

    public SubmitManager(Context context) {
        this.context = context;
    }

    public int UploadSubmitInfo(SubmitInfo submitInfo) {
        int submit_id = -1;
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", submitInfo.getPhone_number());
        contentValues.put("nickname", submitInfo.getNickname());
        contentValues.put("user_head", submitInfo.getUser_head());
        contentValues.put("submit_content", submitInfo.getSubmit_content());
        db.insert("submitInfo", null, contentValues);
        Cursor query = db.query("submitInfo", new String[]{"submit_id"}, "phone_number = ?", new String[]{submitInfo.getPhone_number()}, null, null, "submit_id desc");
        if (query != null && query.getCount() > 0) {
            query.moveToNext();
            submit_id = query.getInt(0);
        }
        if (query != null) {
            query.close();
        }
        db.close();
        return submit_id;
    }


    public void UploadSubmitImageInfo(SubmitImageInfo submitImageInfo) {

        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("submit_id", submitImageInfo.getSubmit_id());
        contentValues.put("submit_image", submitImageInfo.getSubmit_image());
        OssHandler ossHandler = new OssHandler(context);
        UploadUtils.uploadFileToOss(ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+submitImageInfo.getSubmit_image(),submitImageInfo.getSubmit_image());
        db.insert("submitImageInfo", null, contentValues);
        db.close();
    }
}
