package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.os.Bundle;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.handlers.DataInsertHandler;
import com.triplebro.domineer.takeoutfood.controllers.SplashManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.InitOssClient;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private SplashManager splashManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        InitOssClient.initOssClient(this, ProjectProperties.TOKEN_ADDRESS, ProjectProperties.ENDPOINT);
        final DataInsertHandler dataInsertHandler = new DataInsertHandler(this);
        new Thread(){
            @Override
            public void run() {
                splashManager = new SplashManager(SplashActivity.this);
                List<String> table_name = new ArrayList<>();
                table_name.add("adminInfo");
                table_name.add("userInfo");
                table_name.add("typeGeneralize");
                table_name.add("typeConcrete");
                table_name.add("foodInfo");
                table_name.add("foodImageInfo");
                table_name.add("foodSizeInfo");
                List<String> nonentity_table_name  = splashManager.checkData(table_name);
                if(nonentity_table_name.size() == 0){
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                splashManager.uploadData(nonentity_table_name,dataInsertHandler);
            }
        }.start();
    }
}
