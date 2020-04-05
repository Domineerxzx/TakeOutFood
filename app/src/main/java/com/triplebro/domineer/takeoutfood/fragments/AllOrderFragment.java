package com.triplebro.domineer.takeoutfood.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.AllOrderAdapter;
import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.controllers.OrderManager;
import com.triplebro.domineer.takeoutfood.views.MyListView;

import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,4:24
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class AllOrderFragment extends Fragment {

    private View fragment_all_order;
    private MyListView lv_all_order;
    private OrderManager orderManager;
    private SharedPreferences userInfo;
    private String phone_number;
    private List<OrderInfo> allOrderInfoList;
    private AllOrderAdapter allOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment_all_order = inflater.inflate(R.layout.fragment_all_order, container, false);
        initView();
        initData();
        setOnClickListener();
        return fragment_all_order;
    }

    @Override
    public void onStart() {
        super.onStart();
        allOrderInfoList = orderManager.getAllOrderInfoList(phone_number);
        allOrderAdapter.setAllOrderInfoList(allOrderInfoList);
    }

    private void setOnClickListener() {

    }

    private void initData() {
        userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        orderManager = new OrderManager(getActivity());
        allOrderInfoList = orderManager.getAllOrderInfoList(phone_number);
        allOrderAdapter = new AllOrderAdapter(getActivity(), allOrderInfoList);
        lv_all_order.setAdapter(allOrderAdapter);
    }

    private void initView() {
        lv_all_order = (MyListView) fragment_all_order.findViewById(R.id.lv_all_order);
    }
}
