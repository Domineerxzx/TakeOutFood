package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.controllers.RegisterManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button bt_login;
    private ImageView iv_close_create;
    private RegisterManager registerManager;
    private EditText et_phone_number;
    private EditText et_username;
    private EditText et_password;
    private EditText et_request_code;
    private Button bt_request_code;
    private Button bt_create;
    private String phone_number;
    private String password;
    private String username;
    private String request_code;
    private CheckBox cb_agree;
    private Button bt_admin_register;
    private Button bt_user_register;
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        registerManager = new RegisterManager(this);
    }

    private void setOnClickListener() {
        bt_login.setOnClickListener(this);
        iv_close_create.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        //bt_request_code.setOnClickListener(this);
        bt_admin_register.setOnClickListener(this);
        bt_user_register.setOnClickListener(this);
    }

    private void initView() {
        bt_login = findViewById(R.id.bt_login);
        iv_close_create = findViewById(R.id.iv_close_create);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
//        et_request_code = findViewById(R.id.et_request_code);
//        bt_request_code = findViewById(R.id.bt_request_code);
        bt_create = findViewById(R.id.bt_create);
        cb_agree = findViewById(R.id.cb_agree);
        bt_admin_register = (Button) findViewById(R.id.bt_admin_register);
        bt_user_register = (Button) findViewById(R.id.bt_user_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_close_create:
                finish();
                break;
            /*case R.id.bt_request_code:
                phone_number = et_phone_number.getText().toString();
                if (phone_number.length() == 11) {
                    bt_request_code.setBackgroundResource(R.drawable.shape_alpha_card);
                    bt_request_code.setText("已获取验证码");
                    registerManager.getRequestCode(phone_number);
                } else {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                }
                break;*/
            case R.id.bt_create:
                phone_number = et_phone_number.getText().toString();
                password = et_password.getText().toString();
                username = et_username.getText().toString();
                request_code = et_request_code.getText().toString();
                phone_number = et_phone_number.getText().toString();
                if (username.length() == 0) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (phone_number.length() != 11) {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                } else if (request_code.length() != 4) {
                    Toast.makeText(this, "验证码不能少于4位数", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!cb_agree.isChecked()) {
                    Toast.makeText(this, "请查看并同意条款与条件", Toast.LENGTH_SHORT).show();
                } else {
                    registerManager.register(phone_number, /*request_code,*/ password, username,userType);
                }
                break;
            case R.id.bt_admin_register:
                bt_admin_register.setBackgroundResource(R.drawable.shape_pay);
                bt_user_register.setBackgroundResource(R.drawable.shape_alpha_card);
                userType = ProjectProperties.ADMIN;
                break;
            case R.id.bt_user_register:
                bt_admin_register.setBackgroundResource(R.drawable.shape_alpha_card);
                bt_user_register.setBackgroundResource(R.drawable.shape_pay);
                userType = ProjectProperties.USER;
                break;
        }
    }
}
