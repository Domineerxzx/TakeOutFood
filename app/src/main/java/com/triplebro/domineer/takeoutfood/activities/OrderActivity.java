package com.triplebro.domineer.takeoutfood.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.ViewPagerAdapter;
import com.triplebro.domineer.takeoutfood.fragments.AllOrderFragment;
import com.triplebro.domineer.takeoutfood.fragments.WaitEvaluateOrderFragment;
import com.triplebro.domineer.takeoutfood.fragments.WaitGetOrderFragment;
import com.triplebro.domineer.takeoutfood.fragments.WaitPayOrderFragment;
import com.triplebro.domineer.takeoutfood.fragments.WaitSendOrderFragment;
import com.triplebro.domineer.takeoutfood.views.NavitationLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private NavitationLayout nl_order;
    private ViewPager vp_order;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> orderFragmentList;
    private ImageView iv_close_order;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initData();
        setOnClickListener();
        }

    private void setOnClickListener() {
        iv_close_order.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        String titles[] = new String[]{"全部订单","待付款","待发货","待收货","待评价"};
        nl_order.setViewPager(this, titles, vp_order, R.color.Gray, R.color.AppColor, 16, 16, 0, 0, true);
        nl_order.setBgLine(this, 1, R.color.line);
        nl_order.setNavLine(this, 2, R.color.AppColor, 0);
        orderFragmentList =  new ArrayList<>();
        orderFragmentList.add(new AllOrderFragment());
        orderFragmentList.add(new WaitPayOrderFragment());
        orderFragmentList.add(new WaitSendOrderFragment());
        orderFragmentList.add(new WaitGetOrderFragment());
        orderFragmentList.add(new WaitEvaluateOrderFragment());
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), orderFragmentList);
        vp_order.setAdapter(pagerAdapter);
        vp_order.setCurrentItem(type);
    }

    private void initView() {
        nl_order = (NavitationLayout) findViewById(R.id.nl_order);
        vp_order = (ViewPager) findViewById(R.id.vp_order);
        iv_close_order = (ImageView) findViewById(R.id.iv_close_order);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_order:
                finish();
                break;
        }
    }
}
