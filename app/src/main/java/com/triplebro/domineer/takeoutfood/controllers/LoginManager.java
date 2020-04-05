package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.triplebro.domineer.takeoutfood.service.NetworkConnectionService;

public class LoginManager implements ServiceConnection {

    private Context context;
    private String phone_number;
    private String password;
    private int userType;

    public LoginManager(Context context) {
        this.context = context;
    }

    public void login(String phone_number, String password, int userType) {
        this.phone_number = phone_number;
        this.password = password;
        this.userType = userType;
        Intent service = new Intent(context, NetworkConnectionService.class);
        context.bindService(service,this,Context.BIND_AUTO_CREATE);
        System.out.println("-----------------------------------------------");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkConnectionService.MyBinder myBinder = (NetworkConnectionService.MyBinder) service;
        myBinder.login(context, phone_number,password,this,userType);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
