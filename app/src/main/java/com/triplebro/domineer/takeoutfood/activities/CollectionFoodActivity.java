package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.controllers.CollectionManager;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;

import java.util.List;

public class CollectionFoodActivity extends Activity implements View.OnClickListener,OnItemClickListener {

    private ImageView iv_close_collection_food;
    private String phone_number;
    private RecyclerView rv_collection_food;
    private RecommendAdapter recommendAdapter;
    private CollectionManager collectionManager;
    private List<FoodInfo> collectedFoodInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_food);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_collection_food.setOnClickListener(this);
    }

    private void initData() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        collectionManager = new CollectionManager(this);
        collectedFoodInfoList = collectionManager.getCollectedFoodInfoList(phone_number);
        if(collectedFoodInfoList.size() == 0){
            Toast.makeText(this, "暂无收藏信息，快去添加吧", Toast.LENGTH_SHORT).show();
        }
        recommendAdapter = new RecommendAdapter(this, collectedFoodInfoList);
        rv_collection_food.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        iv_close_collection_food = (ImageView) findViewById(R.id.iv_close_collection_food);
        rv_collection_food = (RecyclerView) findViewById(R.id.rv_collection_food);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_collection_food.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_collection_food:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent collectionFood = new Intent(this, FoodDetailsActivity.class);
        collectionFood.putExtra("foodInfo", collectedFoodInfoList.get(position));
        startActivity(collectionFood);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
