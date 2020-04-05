package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.controllers.DeleteFoodManager;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.TwoButtonDialog;

import java.util.List;

public class DeleteFoodActivity extends Activity implements OnItemClickListener,View.OnClickListener {

    private ImageView iv_close_delete_food;
    private DeleteFoodManager deleteFoodManager;
    private SharedPreferences adminInfo;
    private String phone_number;
    private List<FoodInfo> foodInfoList;
    private RecyclerView rv_food_info;
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);
        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_delete_food = (ImageView) findViewById(R.id.iv_close_delete_food);
        rv_food_info = (RecyclerView) findViewById(R.id.rv_food_info);
    }

    private void initData() {
        deleteFoodManager = new DeleteFoodManager(this);
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        foodInfoList = deleteFoodManager.getFoodInfoList(phone_number);
        rv_food_info.setLayoutManager(new GridLayoutManager(this,2));
        recommendAdapter = new RecommendAdapter(this, foodInfoList);
        rv_food_info.setAdapter(recommendAdapter);
    }

    private void setOnClickListener() {
        iv_close_delete_food.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, final int position) {
        TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
        twoButtonDialog.show("删除商品", "是否要删除该商品？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFoodManager.deleteFood(foodInfoList.get(position).getFood_id());
                foodInfoList.remove(position);
                recommendAdapter.setData(foodInfoList);
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        },getFragmentManager());
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_delete_food:
                finish();
                break;
        }
    }
}
