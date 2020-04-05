package com.triplebro.domineer.takeoutfood.controllers;

import android.content.Context;

import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,5:57
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class OrderManager {

    private Context context;

    public OrderManager(Context context) {
        this.context = context;
    }

    public List<OrderInfo> getAllOrderInfoList(String phone_number) {
        List<OrderInfo> allOrderInfoList = new ArrayList<>();
        DatabaseOP databaseOP = new DatabaseOP(context);
        allOrderInfoList = databaseOP.getAllOrderInfoList(phone_number);
        return allOrderInfoList;
    }


    public List<ShoppingCartInfo> getOrderContentInfoList(int order_id) {
        List<ShoppingCartInfo> shoppingCartInfoList = new ArrayList<>();
        DatabaseOP databaseOP = new DatabaseOP(context);
        shoppingCartInfoList = databaseOP.getOrderContentInfoList(order_id);
        return shoppingCartInfoList;
    }

    public int getOrderCountPrice(List<ShoppingCartInfo> shoppingCartInfoList) {
        int sum = 0;
        for (ShoppingCartInfo shoppingCartInfo :
                shoppingCartInfoList) {
            sum += shoppingCartInfo.getCount() * shoppingCartInfo.getPrice();
        }
        return sum;
    }

    public List<OrderInfo> getStateOrderInfoList(String phone_number, int orderState) {
        List<OrderInfo> stateOrderInfoList = new ArrayList<>();
        DatabaseOP databaseOP = new DatabaseOP(context);
        stateOrderInfoList = databaseOP.getStateOrderInfoList(phone_number,orderState);
        return stateOrderInfoList;
    }

    public void updateWaitGetToWaitEvaluate(int id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.updateWaitGetToWaitEvaluate(id);
    }

    public void updateWaitPayToWaitSend(int id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.updateWaitPayToWaitSend(id);
    }

    public void updateWaitSendToWaitGet(int id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.updateWaitSendToWaitGet(id);
    }

    public void updateWaitEvaluateToDone(int id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.updateWaitEvaluateToDone(id);
    }
}
