package com.triplebro.domineer.takeoutfood.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.FoodDetailsActivity;
import com.triplebro.domineer.takeoutfood.activities.SearchActivity;
import com.triplebro.domineer.takeoutfood.activities.TypeRecommendActivity;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.models.AdminInfo;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.interfaces.OnScrollChangedListener;
import com.triplebro.domineer.takeoutfood.controllers.FirstPageManager;
import com.triplebro.domineer.takeoutfood.utils.imageUtils.GlideImageLoader;
import com.triplebro.domineer.takeoutfood.views.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends Fragment implements OnScrollChangedListener, View.OnClickListener, OnItemClickListener {

    private View fragment_firstPage;
    private FrameLayout fl_search;
    private Banner bn_banner;
    private RecyclerView rv_recommend;
    private MyScrollView msv_first_page;
    private TextView tv_search;
    private RelativeLayout rl_search;
    private LinearLayout ll_chinese_food;
    private LinearLayout ll_dessert;
    private LinearLayout ll_snacks;
    private LinearLayout ll_fast_food;
    private LinearLayout ll_flower;
    private LinearLayout ll_fresh;
    private LinearLayout ll_life;
    private LinearLayout ll_medical;
    private TextView tv_chinese_food;
    private TextView tv_dessert;
    private TextView tv_snacks;
    private TextView tv_fast_food;
    private TextView tv_flower;
    private TextView tv_fresh;
    private TextView tv_life;
    private TextView tv_medical;
    private ImageView iv_chinese_food;
    private ImageView iv_dessert;
    private ImageView iv_snacks;
    private ImageView iv_fast_food;
    private ImageView iv_flower;
    private ImageView iv_fresh;
    private ImageView iv_life;
    private ImageView iv_medical;
    private FirstPageManager firstPageManager;
    private List<FoodInfo> foodInfoList = new ArrayList<>();
    private RecommendAdapter recommendAdapter;
    private List<String> bannerImageList;
    private List<FoodInfo> functionFoodList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_firstPage = inflater.inflate(R.layout.fragment_first_page, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_firstPage;
    }

    private void setOnClickListener() {
        tv_search.setOnClickListener(this);
        rl_search.setOnClickListener(this);

        ll_chinese_food.setOnClickListener(this);
        ll_dessert.setOnClickListener(this);
        ll_snacks.setOnClickListener(this);
        ll_fast_food.setOnClickListener(this);
        ll_flower.setOnClickListener(this);
        ll_fresh.setOnClickListener(this);
        ll_life.setOnClickListener(this);
        ll_medical.setOnClickListener(this);

        tv_chinese_food.setOnClickListener(this);
        tv_dessert.setOnClickListener(this);
        tv_snacks.setOnClickListener(this);
        tv_fast_food.setOnClickListener(this);
        tv_flower.setOnClickListener(this);
        tv_fresh.setOnClickListener(this);
        tv_life.setOnClickListener(this);
        tv_medical.setOnClickListener(this);

        iv_chinese_food.setOnClickListener(this);
        iv_dessert.setOnClickListener(this);
        iv_snacks.setOnClickListener(this);
        iv_fast_food.setOnClickListener(this);
        iv_flower.setOnClickListener(this);
        iv_fresh.setOnClickListener(this);
        iv_life.setOnClickListener(this);
        iv_medical.setOnClickListener(this);
    }

    private void initData() {
        firstPageManager = new FirstPageManager(getActivity());
        foodInfoList = firstPageManager.getFoodRecommendInfoList();
        recommendAdapter = new RecommendAdapter(getActivity(), foodInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
        bannerImageList = firstPageManager.getBannerImageList();
        bn_banner.setImages(bannerImageList);
        bn_banner.start();
        bn_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), FoodDetailsActivity.class);
                intent.putExtra("foodInfo", foodInfoList.get(position));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        fl_search = fragment_firstPage.findViewById(R.id.fl_search);
        fl_search.bringToFront();
        bn_banner = fragment_firstPage.findViewById(R.id.bn_banner);
        rv_recommend = fragment_firstPage.findViewById(R.id.rv_recommend);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend.setLayoutManager(gridLayoutManager);

        msv_first_page = fragment_firstPage.findViewById(R.id.msv_first_page);
        msv_first_page.setOnScrollChangedListener(this);
        tv_search = fragment_firstPage.findViewById(R.id.tv_search);
        rl_search = fragment_firstPage.findViewById(R.id.rl_search);
        ll_chinese_food = fragment_firstPage.findViewById(R.id.ll_chinese_food);
        ll_dessert = fragment_firstPage.findViewById(R.id.ll_dessert);
        ll_snacks = fragment_firstPage.findViewById(R.id.ll_snacks);
        ll_fast_food = fragment_firstPage.findViewById(R.id.ll_fast_food);
        ll_flower = fragment_firstPage.findViewById(R.id.ll_flower);
        ll_fresh = fragment_firstPage.findViewById(R.id.ll_fresh);
        ll_life = fragment_firstPage.findViewById(R.id.ll_life);
        ll_medical = fragment_firstPage.findViewById(R.id.ll_medical);
        tv_chinese_food = fragment_firstPage.findViewById(R.id.tv_chinese_food);
        tv_dessert = fragment_firstPage.findViewById(R.id.tv_dessert);
        tv_snacks = fragment_firstPage.findViewById(R.id.tv_snacks);
        tv_fast_food = fragment_firstPage.findViewById(R.id.tv_fast_food);
        tv_flower = fragment_firstPage.findViewById(R.id.tv_flower);
        tv_fresh = fragment_firstPage.findViewById(R.id.tv_fresh);
        tv_life = fragment_firstPage.findViewById(R.id.tv_life);
        tv_medical = fragment_firstPage.findViewById(R.id.tv_medical);
        iv_chinese_food = fragment_firstPage.findViewById(R.id.iv_chinese_food);
        iv_dessert = fragment_firstPage.findViewById(R.id.iv_dessert);
        iv_snacks = fragment_firstPage.findViewById(R.id.iv_snacks);
        iv_fast_food = fragment_firstPage.findViewById(R.id.iv_fast_food);
        iv_flower = fragment_firstPage.findViewById(R.id.iv_flower);
        iv_fresh = fragment_firstPage.findViewById(R.id.iv_fresh);
        iv_life = fragment_firstPage.findViewById(R.id.iv_life);
        iv_medical = fragment_firstPage.findViewById(R.id.iv_medical);
        bn_banner.setImageLoader(new GlideImageLoader());
        bn_banner.isAutoPlay(true);
        bn_banner.setDelayTime(5000);
        bn_banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    public void onStart() {
        super.onStart();
        foodInfoList = firstPageManager.getFoodRecommendInfoList();
        recommendAdapter = new RecommendAdapter(getActivity(), foodInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (oldy <= 45) {
            fl_search.setBackgroundColor(Color.TRANSPARENT);
        } else {
            fl_search.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
            case R.id.rl_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_chinese_food:
            case R.id.tv_chinese_food:
            case R.id.iv_chinese_food:
                Intent clothes = new Intent(getActivity(), TypeRecommendActivity.class);
                clothes.putExtra("title", tv_chinese_food.getText().toString());
                clothes.putExtra("type", 0);
                getActivity().startActivity(clothes);
                break;
            case R.id.ll_dessert:
            case R.id.tv_dessert:
            case R.id.iv_dessert:
                Intent bags = new Intent(getActivity(), TypeRecommendActivity.class);
                bags.putExtra("title", tv_dessert.getText().toString());
                bags.putExtra("type", 3);
                getActivity().startActivity(bags);
                break;
            case R.id.ll_snacks:
            case R.id.tv_snacks:
            case R.id.iv_snacks:
                Intent household = new Intent(getActivity(), TypeRecommendActivity.class);
                household.putExtra("title", tv_snacks.getText().toString());
                household.putExtra("type", 5);
                getActivity().startActivity(household);
                break;
            case R.id.ll_fast_food:
            case R.id.tv_fast_food:
            case R.id.iv_fast_food:
                Intent technology = new Intent(getActivity(), TypeRecommendActivity.class);
                technology.putExtra("title", tv_fast_food.getText().toString());
                technology.putExtra("type", 6);
                getActivity().startActivity(technology);
                break;
            case R.id.ll_flower:
            case R.id.tv_flower:
            case R.id.iv_flower:
                Intent life = new Intent(getActivity(), TypeRecommendActivity.class);
                life.putExtra("title", tv_flower.getText().toString());
                life.putExtra("type",4);
                getActivity().startActivity(life);
                break;
            case R.id.ll_fresh:
            case R.id.tv_fresh:
            case R.id.iv_fresh:
                Intent mother_and_baby = new Intent(getActivity(), TypeRecommendActivity.class);
                mother_and_baby.putExtra("title", tv_fresh.getText().toString());
                mother_and_baby.putExtra("type",7);
                getActivity().startActivity(mother_and_baby);
                break;
            case R.id.ll_life:
            case R.id.tv_life:
            case R.id.iv_life:
                Intent beauty_make_up = new Intent(getActivity(), TypeRecommendActivity.class);
                beauty_make_up.putExtra("title", tv_life.getText().toString());
                beauty_make_up.putExtra("type",8);
                getActivity().startActivity(beauty_make_up);
                break;
            case R.id.ll_medical:
            case R.id.tv_medical:
            case R.id.iv_medical:
                Intent tide_card = new Intent(getActivity(), TypeRecommendActivity.class);
                tide_card.putExtra("title", tv_medical.getText().toString());
                tide_card.putExtra("type",9);
                getActivity().startActivity(tide_card);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), FoodDetailsActivity.class);
        intent.putExtra("foodInfo", foodInfoList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(firstPageManager);
    }*/
}
