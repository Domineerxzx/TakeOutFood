package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.MyselfSubmitAdapter;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.controllers.UserManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.ChooseUserHeadDialogUtil;
import com.triplebro.domineer.takeoutfood.utils.imageUtils.RealPathFromUriUtils;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.UploadUtils;
import com.triplebro.domineer.takeoutfood.views.MyListView;
import java.io.File;
import java.util.List;

public class UserInfoActivity extends Activity implements View.OnClickListener {

    private RecyclerView rv_photo_wall;
    private MyListView mlv_user_submit;
    private ImageView iv_close_user_info;
    private TextView tv_nickname;
    private TextView tv_username;
    private ImageView iv_user_head;
    private String phone_number;
    private String nickname;
    private String userHead;
    private File userHeadFile;
    private SharedPreferences userInfo;
    private UserManager userManager;
    private List<SubmitInfo> submitInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_user_info.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
    }

    private void initData() {
        userManager = new UserManager(this);
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "获取信息失败");
        nickname = userInfo.getString("nickname", "获取信息失败");
        userHead = userInfo.getString("userHead", "");
        if (userHead.length() != 0) {
            File file = new File(userHead);
            if (file.length() > 0) {
                Glide.with(this).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
            }else{
                OssHandler ossHandler = new OssHandler(this, iv_user_head, true);
                DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+userHead);
            }
        } else {
            Glide.with(this).load(R.drawable.user_head_default).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        }
        tv_nickname.setText(nickname);
        tv_username.setText("ID:" + phone_number);
        submitInfoList = userManager.getSubmitInfoList(phone_number);
        mlv_user_submit.setAdapter(new MyselfSubmitAdapter(this, submitInfoList));
    }

    private void initView() {
        iv_close_user_info = (ImageView) findViewById(R.id.iv_close_user_info);
        mlv_user_submit = (MyListView) findViewById(R.id.mlv_user_submit);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_username = (TextView) findViewById(R.id.tv_username);
        iv_user_head = (ImageView) findViewById(R.id.iv_user_head);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_user_info:
                finish();
                break;
            case R.id.iv_user_head:
                long timeStamp = System.currentTimeMillis();
                SharedPreferences.Editor edit = userInfo.edit();
                edit.putLong("timeStamp", timeStamp);
                edit.commit();
                ChooseUserHeadDialogUtil.showDialog(this, phone_number, timeStamp);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isCheck = true;
        SharedPreferences.Editor edit = userInfo.edit();
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(this);
        SQLiteDatabase writableDatabase = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch (requestCode) {
            case ProjectProperties.FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    userHeadFile = new File(RealPathFromUriUtils.getRealPathFromUri(this, data.getData()));
                    Glide.with(this).load(userHeadFile).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
                    edit.putString("userHead", userHeadFile.getAbsolutePath());
                    contentValues.put("user_head", userHeadFile.getAbsolutePath());
                } else {
                    isCheck = false;
                }
                break;
            case ProjectProperties.FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    long timeStamp = userInfo.getLong("timeStamp", -1);
                    userHeadFile = new File(getFilesDir() + File.separator + "images" + File.separator + phone_number + timeStamp + ".jpg");
                    Glide.with(this).load(userHeadFile).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
                    edit.putString("userHead", userHeadFile.getAbsolutePath());
                    contentValues.put("user_head", userHeadFile.getAbsolutePath());
                } else {
                    isCheck = false;
                }
                break;
            default:
                break;
        }
        if (isCheck) {
            edit.commit();
            writableDatabase.update("userInfo", contentValues, "phone_number = ?", new String[]{phone_number});
            OssHandler ossHandler = new OssHandler(this);
            UploadUtils.uploadFileToOss(ossHandler, ProjectProperties.BUCKET_NAME, "xuzhanxin/" + contentValues.getAsString("user_head"), contentValues.getAsString("user_head"));
            writableDatabase.close();
        } else {
            Toast.makeText(this, "取消修改", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
