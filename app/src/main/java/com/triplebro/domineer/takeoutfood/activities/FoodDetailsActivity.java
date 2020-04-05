package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.controllers.FirstPageManager;
import com.triplebro.domineer.takeoutfood.controllers.FoodDetailsManager;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.AddShoppingCartDialog;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.ShareUtil;
import com.triplebro.domineer.takeoutfood.utils.imageUtils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailsActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_food_details;
    private ImageView iv_share_food_details;
    private ImageView iv_collection_food_details;
    private boolean isCollection;
    private Banner bn_food;
    private RecyclerView rv_recommend_inside;
    private ArrayList<String> images;
    private FoodInfo foodInfo;
    private TextView tv_food_name;
    private TextView tv_price;
    private FoodDetailsManager foodDetailsManager;
    private List<FoodInfo> foodInfoList;
    private List<String> foodImagePathList;
    private TextView tv_add_shopping_cart;
    private TextView tv_select_size;
    private List<FoodSizeInfo> foodSizeInfoList;
    private SharedPreferences userInfo;
    private String phone_number;
    private FirstPageManager firstPageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        Intent intent = getIntent();
        foodInfo = (FoodInfo) intent.getSerializableExtra("foodInfo");
        foodDetailsManager = new FoodDetailsManager(this);
        foodInfoList = foodDetailsManager.getFoodInfoList();
        foodImagePathList = foodDetailsManager.getFoodImagePathList(foodInfo.getFood_id());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend_inside.setLayoutManager(gridLayoutManager);
        RecommendAdapter recommendAdapter = new RecommendAdapter(this, foodInfoList);
        rv_recommend_inside.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(FoodDetailsActivity.this, FoodDetailsActivity.class);
                intent.putExtra("foodInfo", foodInfoList.get(position));
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        bn_food.setImageLoader(new GlideImageLoader());
        bn_food.setImages(foodImagePathList);
        bn_food.isAutoPlay(false);
        bn_food.setIndicatorGravity(BannerConfig.CENTER);
        bn_food.start();
        bn_food.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        tv_food_name.setText(foodInfo.getFood_name());
        tv_price.setText(String.valueOf(foodInfo.getPrice()));
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        DatabaseOP databaseOP = new DatabaseOP(this);
        isCollection = databaseOP.getIsCollection(foodInfo.getFood_id());
        if(isCollection){
            iv_collection_food_details.setBackgroundResource(R.mipmap.collection_click);
        }
    }

    private void setOnClickListener() {
        iv_close_food_details.setOnClickListener(this);
        iv_collection_food_details.setOnClickListener(this);
        tv_add_shopping_cart.setOnClickListener(this);
        tv_select_size.setOnClickListener(this);
        iv_share_food_details.setOnClickListener(this);
    }

    private void initView() {
        iv_close_food_details = (ImageView) findViewById(R.id.iv_close_food_details);
        iv_share_food_details = (ImageView) findViewById(R.id.iv_share_food_details);
        iv_collection_food_details = (ImageView) findViewById(R.id.iv_collection_food_details);
        iv_close_food_details.bringToFront();
        iv_share_food_details.bringToFront();
        iv_collection_food_details.bringToFront();
        bn_food = (Banner) findViewById(R.id.bn_food);
        rv_recommend_inside = (RecyclerView) findViewById(R.id.rv_recommend_inside);
        tv_food_name = (TextView) findViewById(R.id.tv_food_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_add_shopping_cart = (TextView) findViewById(R.id.tv_add_shopping_cart);
        tv_select_size = (TextView) findViewById(R.id.tv_select_size);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_food_details:
                finish();
                break;
            case R.id.iv_share_food_details:
                ShareUtil.shareText(this, "商品名称："+foodInfo.getFood_name()+"价格："+foodInfo.getPrice(),"分享此商品给朋友");
                break;
            case R.id.iv_collection_food_details:
                firstPageManager = new FirstPageManager(this);
                if (phone_number.length() > 0) {
                    if (isCollection) {
                        boolean deleteFoodCollection = firstPageManager.deleteFoodCollection(foodInfo.getFood_id());
                        if (deleteFoodCollection) {
                            iv_collection_food_details.setBackgroundResource(R.mipmap.collection);
                            isCollection = false;
                        }
                    } else {
                        boolean addFoodCollection = firstPageManager.addFoodCollection(foodInfo.getFood_id());
                        if (addFoodCollection) {
                            iv_collection_food_details.setBackgroundResource(R.mipmap.collection_click);
                            isCollection = true;
                        }
                    }
                } else {
                    Toast.makeText(this, "还没登录呢，不能收藏商品", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_select_size:
            case R.id.tv_add_shopping_cart:
                foodSizeInfoList = foodDetailsManager.getFoodSizeInfoList(foodInfo.getFood_id());
                AddShoppingCartDialog addShoppingCartDialog = new AddShoppingCartDialog();
                addShoppingCartDialog.showDialog(this, foodInfo, foodSizeInfoList);
                break;
        }
    }
}
