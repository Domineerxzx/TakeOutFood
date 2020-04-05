package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.takeoutfood.R;

public class AboutUsActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_about_us.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        iv_close_about_us = (ImageView) findViewById(R.id.iv_close_about_us);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_about_us:
                finish();
                break;
        }
    }
}
