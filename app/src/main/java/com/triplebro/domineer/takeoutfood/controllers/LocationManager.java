package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.models.AddressInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private Context context;

    public LocationManager(Context context) {
        this.context = context;
    }

    public List<AddressInfo> getAddressInfoList(String phone_number) {
        List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        Cursor locationInfoCursor = db.query("locationInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (locationInfoCursor != null && locationInfoCursor.getCount() > 0) {
            while (locationInfoCursor.moveToNext()) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.set_id(locationInfoCursor.getInt(0));
                addressInfo.setPhone_number(locationInfoCursor.getString(1));
                addressInfo.setName(locationInfoCursor.getString(2));
                addressInfo.setCity(locationInfoCursor.getString(3));
                addressInfo.setLocation(locationInfoCursor.getString(4));
                addressInfo.setZip_code(locationInfoCursor.getInt(5));
                addressInfo.setMobile(locationInfoCursor.getString(6));
                addressInfoList.add(addressInfo);
            }
        }
        if (locationInfoCursor != null) {
            locationInfoCursor.close();
        }
        db.close();
        return addressInfoList;
    }

    public void uploadAddressInfo(AddressInfo addressInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", addressInfo.getPhone_number());
        contentValues.put("name", addressInfo.getName());
        contentValues.put("city", addressInfo.getCity());
        contentValues.put("location", addressInfo.getLocation());
        contentValues.put("zip_code", addressInfo.getZip_code());
        contentValues.put("mobile", addressInfo.getMobile());
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long locationInsertResult = db.insert("locationInfo", null, contentValues);
        if (locationInsertResult >= 0) {
            Toast.makeText(context, "添加地址成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "添加地址失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void updateAddressInfo(AddressInfo addressInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", addressInfo.getPhone_number());
        contentValues.put("name", addressInfo.getName());
        contentValues.put("city", addressInfo.getCity());
        contentValues.put("location", addressInfo.getLocation());
        contentValues.put("zip_code", addressInfo.getZip_code());
        contentValues.put("mobile", addressInfo.getMobile());
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        long locationInsertResult = db.update("locationInfo",contentValues,"_id = ?",new String[]{String.valueOf(addressInfo.get_id())});
        if (locationInsertResult >= 0) {
            Toast.makeText(context, "修改地址成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "修改地址失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
