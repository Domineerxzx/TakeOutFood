package com.triplebro.domineer.takeoutfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class TakeOutFoodOpenHelper extends SQLiteOpenHelper {
    public TakeOutFoodOpenHelper(@Nullable Context context) {
        super(context, "TakeOutFood", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //用户表
        db.execSQL("create table userInfo(phone_number varchar(20) primary key,password varchar(20),nickname varchar(20),user_head varchar(200))");
        //管理员表
        db.execSQL("create table adminInfo(phone_number varchar(20) primary key,password varchar(20),nickname varchar(20),user_head varchar(200))");

        //商品表
        db.execSQL("create table foodInfo(food_id Integer primary key autoincrement,food_name varchar(20),price number,food_image varchar(200)," +
                "type_generalize_id number,type_concrete_id number,phone_number varchar(20)," +
                "FOREIGN KEY (type_generalize_id) REFERENCES typeGeneralize(type_generalize_id)," +
                "FOREIGN KEY (type_concrete_id) REFERENCES typeConcrete(type_concrete_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");
        //商品图片表
        db.execSQL("create table foodImageInfo(_id Integer primary key autoincrement,food_id Integer,food_image varchar(200),phone_number varchar(20)," +
                "FOREIGN KEY (food_id) REFERENCES foodInfo(food_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");
        //商品尺码/库存表
        db.execSQL("create table foodSizeInfo(_id Integer primary key autoincrement,food_id Integer,size_name varchar(20),size_count number,phone_number varchar(20)," +
                "FOREIGN KEY (food_id) REFERENCES foodInfo(food_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");

        //概括分类表
        db.execSQL("create table typeGeneralize(type_generalize_id Integer primary key autoincrement,type_generalize_name varchar(20))");
        //详细分类表
        db.execSQL("create table typeConcrete(type_concrete_id Integer primary key autoincrement,type_generalize_id Integer,type_concrete_name varchar(20)," +
                "type_concrete_image varchar(200),FOREIGN KEY (type_generalize_id) REFERENCES typeGeneralize(type_generalize_id))");

        //发现表
        db.execSQL("create table submitInfo(submit_id Integer primary key autoincrement,phone_number varchar(20),nickname varchar(20),user_head varchar(200)," +
                "submit_content varchar(500),FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");
        //发现图片表
        db.execSQL("create table submitImageInfo(_id Integer primary key autoincrement,submit_id Integer,submit_image varchar(200)," +
                "FOREIGN KEY (submit_id) REFERENCES submitInfo(submit_id))");

        //购物车表
        db.execSQL("create table shoppingCartInfo(_id Integer primary key autoincrement,food_id Integer,size_name varchar(20),count number,food_name varchar(20)," +
                "phone_number varchar(20),food_image varchar(200),price number,isCommit int," +
                "FOREIGN KEY (food_id) REFERENCES foodInfo(food_id)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");

        //地址管理表
        db.execSQL("create table locationInfo(_id Integer primary key autoincrement,phone_number varchar(20),name varchar(20),city varchar(20)," +
                "location varcahr(100),zip_code number,mobile varchar(20)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");

        //收藏商品表
        db.execSQL("create table collectionFoodInfo(_id Integer primary key autoincrement,phone_number varchar(20),food_id Integer," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number)," +
                "FOREIGN KEY (food_id) REFERENCES foodInfo(food_id))");
        //收藏发现表
        db.execSQL("create table collectionSubmitInfo(_id Integer primary key autoincrement,phone_number varchar(20),submit_id Integer," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number)," +
                "FOREIGN KEY (submit_id) REFERENCES submitInfo(submit_id))");

        //商品推荐表
        db.execSQL("create table foodRecommendInfo(_id Integer primary key autoincrement,food_id Integer,food_name varchar(20)," +
                "price number,recommend_image varchar(200)," +
                "FOREIGN KEY (food_id) REFERENCES foodInfo(food_id))");

        //订单表
        db.execSQL("create table orderInfo(_id Integer primary key autoincrement,order_state Integer,phone_number varchar(20)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");
        //订单详细信息表
        db.execSQL("create table orderContentInfo(_id Integer primary key autoincrement,order_id Integer,shopping_cart_id Integer,phone_number varchar(20)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number)," +
                "FOREIGN KEY (order_id) REFERENCES orderInfo(_id)," +
                "FOREIGN KEY (shopping_cart_id) REFERENCES shoppingCartInfo(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints 开启外键约束
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }
}
