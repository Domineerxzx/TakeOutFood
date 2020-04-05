package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.controllers.BrowseFoodManager;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;

import java.util.List;

public class BrowseFoodInfoActivity extends Activity implements OnItemClickListener,View.OnClickListener {

    private ImageView iv_close_browse_and_change_food;
    private BrowseFoodManager browseFoodManager;
    private RecyclerView rv_food_info;
    private SharedPreferences adminInfo;
    private String phone_number;
    private RecommendAdapter recommendAdapter;
    private List<FoodInfo> foodInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_food_info);

        initView();
        initData();
        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodInfoList = browseFoodManager.getFoodInfoList(phone_number);
        recommendAdapter.setData(foodInfoList);
    }

    private void initView() {
        iv_close_browse_and_change_food = (ImageView) findViewById(R.id.iv_close_browse_and_change_food);
        rv_food_info = (RecyclerView) findViewById(R.id.rv_food_info);
    }

    private void initData() {
        browseFoodManager = new BrowseFoodManager(this);
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        foodInfoList = browseFoodManager.getFoodInfoList(phone_number);
        rv_food_info.setLayoutManager(new GridLayoutManager(this,2));
        recommendAdapter = new RecommendAdapter(this, foodInfoList);
        rv_food_info.setAdapter(recommendAdapter);
    }

    private void setOnClickListener() {
        iv_close_browse_and_change_food.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_browse_and_change_food:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent food = new Intent(this, ChangeFoodInfoActivity.class);
        food.putExtra("foodInfo",foodInfoList.get(position));
        startActivity(food);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
