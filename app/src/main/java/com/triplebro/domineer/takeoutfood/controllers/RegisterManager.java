package com.triplebro.domineer.takeoutfood.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.triplebro.domineer.takeoutfood.service.NetworkConnectionService;

public class RegisterManager implements ServiceConnection {

    private Context context;
    private String phone_number;
    /*private String request_code;*/
    private String password;
    private String nickname;
    private int userType;

    private int registerState;

    private static final int STATE_GET_REQUEST = 0;
    private static final int STATE_REGISTER = 1;

    public RegisterManager(Context context) {
        this.context = context;
    }

    /*public void getRequestCode(String phone_number){
        this.phone_number = phone_number;
        Intent intent = new Intent(context, NetworkConnectionService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
        registerState = STATE_GET_REQUEST;
    }*/

    public void register(String phone_number,/* String request_code,*/ String password, String nickname, int userType){
        this.phone_number = phone_number;
        /*this.request_code = request_code;*/
        this.password = password;
        this.nickname = nickname;
        this.userType = userType;
        Intent intent = new Intent(context, NetworkConnectionService.class);
        context.bindService(intent,this,Context.BIND_AUTO_CREATE);
        registerState = STATE_REGISTER;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkConnectionService.MyBinder myBinder = (NetworkConnectionService.MyBinder) service;
        switch (registerState){
            /*case STATE_GET_REQUEST:
                myBinder.getRequestCode(context,phone_number,this);
                break;*/
            case STATE_REGISTER:
                myBinder.register(context,phone_number/*,request_code*/,password,nickname,this,userType);
                break;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
