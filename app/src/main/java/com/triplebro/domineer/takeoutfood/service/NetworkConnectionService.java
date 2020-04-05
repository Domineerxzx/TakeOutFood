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

        public void getRequestCode(Context context, String phone_number, ServiceConnection serviceConnection) {
            NetworkConnectionService.this.getRequestCode(context, phone_number, serviceConnection);
        }

        public void setCountDown(CountDownView countDownView) {
            NetworkConnectionService.this.setCountDown(countDownView);
        }
    }

    private void setCountDown(final CountDownView countDownView) {
        countDownView.setCountTime(300)
                .setHourTvBackgroundRes(R.drawable.shape_white_card)
                .setHourTvTextColorHex("#FF0000")
                .setHourTvTextSize(18)

                .setHourColonTvBackgroundColorHex("#00FFFFFF")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#FF7198")
                .setHourColonTvTextSize(18)

                .setMinuteTvBackgroundRes(R.drawable.shape_white_card)
                .setMinuteTvTextColorHex("#FF0000")
                .setMinuteTvTextSize(18)

                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#FF7198")
                .setMinuteColonTvTextSize(18)

                .setSecondTvBackgroundRes(R.drawable.shape_white_card)
                .setSecondTvTextColorHex("#FF0000")
                .setSecondTvTextSize(18)

                // 开启倒计时
                .startCountDown()

                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        setCountDown(countDownView);
                    }
                });
    }

    private void updateRegisterInfo(final Context context, final String phone_number, final String password, final String nickname, ServiceConnection serviceConnection, final RegisterHandler registerHandler, final int userType) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhoneNumber", phone_number);
        builder.add("userNickName", nickname);
        builder.add("userPassword", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_UPDATE_REGISTER_INFO, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
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
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "更新注册信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getRequestCode(final Context context, final String phone_number, ServiceConnection serviceConnection) {
        final RegisterHandler registerHandler = new RegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_GET_REQUEST_CODE, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                System.out.println("registerRequestCode------------------------------------" + register_message);
                if (register_message.contains("{\"message\":1}")) {
                    registerHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_SUCCESS);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void register(final Context context, final String phone_number, /*String request_code,*/ final String password, final String nickname, final ServiceConnection serviceConnection, final int userType) {
        final RegisterHandler registerHandler = new RegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        //builder.add("registerNumber", request_code);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_REGISTER, builder, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
                SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("phone_number", phone_number);
                contentValues.put("password", password);
                contentValues.put("nickname", nickname);
                writableDatabase.insert("adminInfo",null,contentValues);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long userInfo;
                String register_message = response.body().string();
                System.out.println("register------------------------------------" + register_message);
                if (register_message.equals("{\"message\":1}")) {
                    updateRegisterInfo(context, phone_number, password, nickname, serviceConnection, registerHandler, userType);
                    TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
                    SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("phone_number", phone_number);
                    contentValues.put("password", password);
                    contentValues.put("nickname", nickname);
                    if (userType == ProjectProperties.ADMIN) {
                        userInfo = writableDatabase.insert("adminInfo", null, contentValues);
                    } else {
                        userInfo = writableDatabase.insert("userInfo", null, contentValues);
                    }
                    if (userInfo != -1) {
                        System.out.println("SUCCESS-----------------------数据库插入成功" + userInfo);
                        writableDatabase.close();
                    } else {
                        System.out.println("error-----------------------数据库插入失败" + userInfo);
                        writableDatabase.close();
                    }
                } else {
                    registerHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void login(final Context context, final String phone_number, final String password, ServiceConnection serviceConnection, final int userType) {

        final LoginHandler loginHandler = new LoginHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhone", phone_number);
        builder.add("password", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_LOGIN, builder, new Callback() {

            private String userHead;
            private String nickname;

            @Override
            public void onFailure(Call call, IOException e) {
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

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (userType == ProjectProperties.ADMIN) {
                    String login_message = response.body().string();
                    System.out.println("login" + "------------------------------------" + login_message);
                    if (login_message.contains("\"message\":1")) {

                        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
                        SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
                        Cursor adminInfo = writableDatabase.query("adminInfo", new String[]{"nickname", "user_head"},
                                "phone_number=?", new String[]{phone_number}, null, null, null);
                        if (adminInfo != null || adminInfo.getCount() > 0) {
                            while (adminInfo.moveToNext()) {
                                nickname = adminInfo.getString(0);
                                userHead = adminInfo.getString(1);
                                System.out.println(nickname);
                            }
                        }
                        writableDatabase.close();
                        SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("phone_number", phone_number);
                        edit.putString("nickname", nickname);
                        edit.putString("password", password);
                        edit.putString("userHead", userHead);
                        edit.commit();
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_ADMIN_SUCCESS);
                    } else {
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_ADMIN_FAILED);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    String login_message = response.body().string();
                    System.out.println("login" + "------------------------------------" + login_message);
                    if (login_message.contains("\"message\":1")) {

                        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
                        SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
                        Cursor userInfo = writableDatabase.query("userInfo", new String[]{"nickname", "user_head"},
                                "phone_number=?", new String[]{phone_number}, null, null, null);
                        if (userInfo != null || userInfo.getCount() > 0) {
                            while (userInfo.moveToNext()) {
                                nickname = userInfo.getString(0);
                                userHead = userInfo.getString(1);
                                System.out.println(nickname);
                            }
                        }
                        writableDatabase.close();
                        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("phone_number", phone_number);
                        edit.putString("nickname", nickname);
                        edit.putString("password", password);
                        edit.putString("userHead", userHead);
                        edit.commit();
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_SUCCESS);
                    } else {
                        loginHandler.sendEmptyMessage(ProjectProperties.LOGIN_FAILED);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

    }
}
