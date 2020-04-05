package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.service.NetworkConnectionService;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cc.ibooker.zcountdownviewlib.CountDownView;

public class FirstPageManager implements ServiceConnection {

    private Context context;
    private CountDownView countDownView;

    public FirstPageManager(Context context) {
        this.context = context;
    }

    public List<String> getBannerImageList() {

        List<String> bannerImageList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        bannerImageList = databaseOP.getBannerImageList();
        return bannerImageList;
    }

    public List<FoodInfo> getFoodRecommendInfoList() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<FoodInfo> foodRecommendInfoList = databaseOP.getFoodRecommendInfoList();
        return foodRecommendInfoList;
    }

    public boolean addFoodCollection(int food_id) {

        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean addFoodCollection = databaseOP.addFoodCollection(food_id);
        return addFoodCollection;
    }


    public boolean deleteFoodCollection(int food_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean deleteFoodCollection = databaseOP.deleteFoodCollection(food_id);
        return deleteFoodCollection;
    }

    public List<FoodInfo> getFunctionFoodList(List<FoodInfo> foodInfoList) {
        List<FoodInfo> functionFoodList = new ArrayList<>();
        if(foodInfoList.size() < 9){
            return functionFoodList;
        }
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        Random random=new Random();
        while (functionFoodList.size() != 9) {
            int randomInt=random.nextInt(foodInfoList.size());
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                functionFoodList.add(foodInfoList.get(randomInt));
            }else {
                System.out.println("该商品已经被添加,不能重复添加");
            }
        }
        return functionFoodList;
    }

    public void setCountDown(CountDownView countdownView) {
        if (this.countDownView == null){
            this.countDownView = countdownView;
            Intent service = new Intent(context, NetworkConnectionService.class);
            context.bindService(service, this, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkConnectionService.MyBinder myBinder = (NetworkConnectionService.MyBinder) service;
        myBinder.setCountDown(countDownView);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
