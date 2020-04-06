package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;

public class AdminManagerActivity extends Activity implements View.OnClickListener {

    private TextView tv_admin;
    private LinearLayout ll_add_goods;
    private LinearLayout ll_browse_and_change_goods_info;
    private LinearLayout ll_delete_goods;
    private LinearLayout ll_change_admin_info;
    private TextView tv_add_goods;
    private TextView tv_browse_and_change_goods_info;
    private TextView tv_delete_goods;
    private TextView tv_change_admin_info;
    private ImageView iv_add_goods;
    private ImageView iv_browse_and_change_goods_info;
    private ImageView iv_delete_goods;
    private ImageView iv_change_admin_info;
    private SharedPreferences adminInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
    }

    private void setOnClickListener() {
        tv_admin.setOnClickListener(this);
        ll_add_goods.setOnClickListener(this);
        ll_browse_and_change_goods_info.setOnClickListener(this);
        ll_delete_goods.setOnClickListener(this);
        tv_add_goods.setOnClickListener(this);
        tv_browse_and_change_goods_info.setOnClickListener(this);
        tv_delete_goods.setOnClickListener(this);
        iv_add_goods.setOnClickListener(this);
        iv_browse_and_change_goods_info.setOnClickListener(this);
        iv_delete_goods.setOnClickListener(this);
    }

    private void initView() {
        tv_admin = (TextView) findViewById(R.id.tv_admin);
        ll_add_goods = (LinearLayout) findViewById(R.id.ll_add_goods);
        ll_browse_and_change_goods_info = (LinearLayout) findViewById(R.id.ll_browse_and_change_goods_info);
        ll_delete_goods = (LinearLayout) findViewById(R.id.ll_delete_goods);
        tv_add_goods = (TextView) findViewById(R.id.tv_add_goods);
        tv_browse_and_change_goods_info = (TextView) findViewById(R.id.tv_browse_and_change_goods_info);
        tv_delete_goods = (TextView) findViewById(R.id.tv_delete_goods);
        iv_add_goods = (ImageView) findViewById(R.id.iv_add_goods);
        iv_browse_and_change_goods_info = (ImageView) findViewById(R.id.iv_browse_and_change_goods_info);
        iv_delete_goods = (ImageView) findViewById(R.id.iv_delete_goods);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_admin:
                finish();
                break;
            case R.id.ll_add_goods:
            case R.id.tv_add_goods:
            case R.id.iv_add_goods:
                Intent addFood = new Intent(this, AddFoodActivity.class);
                startActivity(addFood);
                break;
            case R.id.ll_browse_and_change_goods_info:
            case R.id.tv_browse_and_change_goods_info:
            case R.id.iv_browse_and_change_goods_info:
                Intent browseFood = new Intent(this, BrowseFoodInfoActivity.class);
                startActivity(browseFood);
                break;
            case R.id.ll_delete_goods:
            case R.id.tv_delete_goods:
            case R.id.iv_delete_goods:
                Intent deleteFood = new Intent(this, DeleteFoodActivity.class);
                startActivity(deleteFood);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor edit = adminInfo.edit();
        edit.clear();
        edit.commit();
        super.onDestroy();
    }
}
