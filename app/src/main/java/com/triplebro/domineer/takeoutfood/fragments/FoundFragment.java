package com.triplebro.domineer.takeoutfood.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.SubmitActivity;
import com.triplebro.domineer.takeoutfood.adapters.FoundAdapter;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.controllers.FoundManger;

import java.util.List;

public class FoundFragment extends Fragment implements View.OnClickListener {

    private View fragment_found;
    private ListView lv_found;
    private ImageView iv_submit;
    private FoundManger foundManger;
    private List<SubmitInfo> submitInfoList;
    private FoundAdapter foundAdapter;
    private TextView tv_tip;
    private SharedPreferences userInfo;
    private String phone_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment_found = inflater.inflate(R.layout.fragment_found, container, false);
        initView();
        initData();
        setOnClickListener();
        return fragment_found;
    }

    @Override
    public void onStart() {
        super.onStart();phone_number = userInfo.getString("phone_number", "");
        if(phone_number.length() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            lv_found.setVisibility(View.GONE);
        }else{
            tv_tip.setVisibility(View.GONE);
            lv_found.setVisibility(View.VISIBLE);
            tv_tip.setText("还没登录呢，不能看发现！！！");
            submitInfoList = foundManger.getSubmitInfoList();
            if(submitInfoList.size() == 0){
                tv_tip.setVisibility(View.VISIBLE);
                lv_found.setVisibility(View.GONE);
                tv_tip.setText("发现空空如也，快去发布点东西吧！！！");
            }
            foundAdapter.setSubmitInfoList(submitInfoList);
        }
    }

    private void setOnClickListener() {
        iv_submit.setOnClickListener(this);
    }

    private void initData() {
        foundManger = new FoundManger(getActivity());
        userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        if(phone_number.length() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            lv_found.setVisibility(View.GONE);
        }else{
            tv_tip.setVisibility(View.GONE);
            lv_found.setVisibility(View.VISIBLE);
            tv_tip.setText("还没登录呢，不能看发现！！！");
            submitInfoList = foundManger.getSubmitInfoList();
            if(submitInfoList.size() == 0){
                tv_tip.setVisibility(View.VISIBLE);
                lv_found.setVisibility(View.GONE);
                tv_tip.setText("发现空空如也，快去发布点东西吧！！！");
            }
            foundAdapter = new FoundAdapter(getActivity(), submitInfoList);
            lv_found.setAdapter(foundAdapter);
        }
    }

    private void initView() {
        lv_found = (ListView) fragment_found.findViewById(R.id.lv_found);
        iv_submit = (ImageView) fragment_found.findViewById(R.id.iv_submit);
        tv_tip = fragment_found.findViewById(R.id.tv_tip);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_submit:
                if(phone_number.length()!=11){
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent submit = new Intent(getActivity(), SubmitActivity.class);
                startActivity(submit);
                break;
        }
    }
}
