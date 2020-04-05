package com.triplebro.domineer.takeoutfood.service;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;
import com.triplebro.domineer.takeoutfood.handlers.LoginHandler;
import com.triplebro.domineer.takeoutfood.handlers.RegisterHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.httpUtils.HttpUtils;

import java.io.IOException;

import cc.ibooker.zcountdownviewlib.CountDownView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class NetworkConnectionService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void login(Context context, String phone_number, String password, ServiceConnection serviceConnection, int userType) {
            NetworkConnectionService.this.login(context, phone_number, password, serviceConnection, userType);
        }

        public void register(Context context, String phone_number, /*String request_code,*/ String password, String nickname, ServiceConnection serviceConnection, int userType) {
            NetworkConnectionService.this.register(context, phone_number, /*request_code,*/ password, nickname, serviceConnection, userType);
        }

    }


    private void updateRegisterInfo(final Context context, final String phone_number, final String password, final String nickname, ServiceConnection serviceConnection, final RegisterHandler registerHandler, final int userType) {
        if (userType == ProjectProperties.ADMIN) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("nickname", nickname);
            edit.putString("phone_number", phone_number);
            edit.putString("password", password);
            edit.commit();
            registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_ADMIN_SUCCESS);
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("nickname", nickname);
            edit.putString("phone_number", phone_number);
            edit.putString("password", password);
            edit.commit();
            registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_SUCCESS);
        }
    }

    private void register(final Context context, final String phone_number, /*String request_code,*/ final String password, final String nickname, final ServiceConnection serviceConnection, final int userType) {
        final RegisterHandler registerHandler = new RegisterHandler(context, serviceConnection);

        updateRegisterInfo(context, phone_number, password, nickname, serviceConnection, registerHandler, userType);
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", phone_number);
        contentValues.put("password", password);
        contentValues.put("nickname", nickname);
        writableDatabase.insert("adminInfo", null, contentValues);

    }

    private void login(final Context context, final String phone_number, final String password, ServiceConnection serviceConnection, final int userType) {

        final LoginHandler loginHandler = new LoginHandler(context, serviceConnection);
        boolean isCheckSuccess = false;
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        if (userType == ProjectProperties.ADMIN) {
            Cursor adminInfo = db.query("adminInfo", new String[]{"password", "nickname", "user_head"}, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (adminInfo != null && adminInfo.getCount() > 0) {
                while (adminInfo.moveToNext()) {
                    String pw = adminInfo.getString(0);
                    if (password.equals(pw)) {
                        isCheckSuccess = true;
                        SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("phone_number", phone_number);
                        edit.putString("nickname", adminInfo.getString(1));
                        edit.putString("password", password);
                        edit.putString("userHead", adminInfo.getString(2));
                        edit.commit();
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_ADMIN_SUCCESS);
                        return;
                    }
                }
                if (isCheckSuccess == false) {
                    loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_ADMIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_ADMIN_FAILED);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "未找到该商户", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Cursor userInfo = db.query("userInfo", new String[]{"password", "nickname", "user_head"}, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (userInfo != null && userInfo.getCount() > 0) {
                while (userInfo.moveToNext()) {
                    String pw = userInfo.getString(0);
                    if (password.equals(pw)) {
                        isCheckSuccess = true;
                        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("phone_number", phone_number);
                        edit.putString("nickname", userInfo.getString(1));
                        edit.putString("password", password);
                        edit.putString("userHead", userInfo.getString(2));
                        edit.commit();
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_SUCCESS);
                        return;
                    }
                }
                if (isCheckSuccess == false) {
                    loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "未找到该用户", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
