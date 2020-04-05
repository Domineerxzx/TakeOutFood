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
import com.triplebro.domineer.takeoutfood.adapters.WaitGetOrderAdapter;
import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.controllers.OrderManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.views.MyListView;

import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,4:25
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class WaitGetOrderFragment extends Fragment {
    private View fragment_wait_get_order;
    private MyListView lv_wait_get_order;
    private OrderManager orderManager;
    private SharedPreferences userInfo;
    private String phone_number;
    private List<OrderInfo> waitGetOrderInfoList;
    private WaitGetOrderAdapter waitGetOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment_wait_get_order = inflater.inflate(R.layout.fragment_wait_get_order, container, false);
        initView();
        initData();
        setOnClickListener();
        return fragment_wait_get_order;
    }

    @Override
    public void onStart() {
        super.onStart();
        waitGetOrderInfoList = orderManager.getStateOrderInfoList(phone_number,ProjectProperties.ORDER_STATE_WAIT_GET);
        waitGetOrderAdapter.setWaitGetOrderInfoList(waitGetOrderInfoList);
    }

    private void setOnClickListener() {

    }

    private void initData() {
        userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        orderManager = new OrderManager(getActivity());
        waitGetOrderInfoList = orderManager.getStateOrderInfoList(phone_number,ProjectProperties.ORDER_STATE_WAIT_GET);
        waitGetOrderAdapter = new WaitGetOrderAdapter(getActivity(), waitGetOrderInfoList);
        lv_wait_get_order.setAdapter(waitGetOrderAdapter);
    }

    private void initView() {
        lv_wait_get_order = (MyListView) fragment_wait_get_order.findViewById(R.id.lv_wait_get_order);
    }
}
