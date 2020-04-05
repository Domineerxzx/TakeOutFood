package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;

public class FeedbackActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_feedback;
    private EditText et_feedback;
    private Button bt_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_feedback.setOnClickListener(this);
        bt_feedback.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        iv_close_feedback = (ImageView) findViewById(R.id.iv_close_feedback);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        bt_feedback = (Button) findViewById(R.id.bt_feedback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_feedback:
                finish();
                break;
            case R.id.bt_feedback:
                String feedback = et_feedback.getText().toString().trim();
                if(feedback.length() == 0){
                    Toast.makeText(this, "反馈信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences feedbackInfo = getSharedPreferences("feedbackInfo", MODE_PRIVATE);
                SharedPreferences.Editor edit = feedbackInfo.edit();
                edit.putString("feedbackInfo",feedback);
                edit.commit();
                Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
