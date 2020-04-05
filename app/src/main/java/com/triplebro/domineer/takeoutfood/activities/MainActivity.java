package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.fragments.BottomFragment;
import com.triplebro.domineer.takeoutfood.fragments.FirstPageFragment;
import com.triplebro.domineer.takeoutfood.service.NetworkConnectionService;
import com.triplebro.domineer.takeoutfood.utils.PermissionUtil;

public class MainActivity extends Activity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FrameLayout fl_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Intent service = new Intent(this, NetworkConnectionService.class);
        startService(service);
        PermissionUtil.requestPower(this, this, "android.permission.CAMERA"); //请求权限
        PermissionUtil.requestPower(this, this, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    private void initView() {
        fl_bottom = (FrameLayout) findViewById(R.id.fl_bottom);
        fl_bottom.bringToFront();
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_top, new FirstPageFragment());
        transaction.replace(R.id.fl_bottom, new BottomFragment());
        transaction.commit();
    }
}
