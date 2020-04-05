package com.triplebro.domineer.takeoutfood.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.AboutUsActivity;
import com.triplebro.domineer.takeoutfood.activities.CollectionFoodActivity;
import com.triplebro.domineer.takeoutfood.activities.CollectionSubmitActivity;
import com.triplebro.domineer.takeoutfood.activities.FeedbackActivity;
import com.triplebro.domineer.takeoutfood.activities.LocationActivity;
import com.triplebro.domineer.takeoutfood.activities.LoginActivity;
import com.triplebro.domineer.takeoutfood.activities.OrderActivity;
import com.triplebro.domineer.takeoutfood.activities.SettingActivity;
import com.triplebro.domineer.takeoutfood.activities.UserInfoActivity;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.PermissionUtil;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.TwoButtonDialog;
import com.triplebro.domineer.takeoutfood.utils.intentUtils.PermissionController;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public class MyselfFragment extends Fragment implements View.OnClickListener {

    private View fragment_myself;
    private TextView tv_nickname;
    private LinearLayout ll_user_info;
    private TextView tv_username;
    private ImageView iv_user_head;
    private RelativeLayout rl_location;
    private ImageView iv_location;
    private TextView tv_location;
    private ImageView iv_location_more;
    private RelativeLayout rl_setting;
    private ImageView iv_setting;
    private TextView tv_setting;
    private ImageView iv_setting_more;
    private RelativeLayout rl_contact_us;
    private ImageView iv_contact_us;
    private TextView tv_contact_us;
    private ImageView iv_contact_us_more;
    private RelativeLayout rl_collection_submit;
    private ImageView iv_collection_submit;
    private TextView tv_collection_submit;
    private ImageView iv_collection_submit_more;
    private RelativeLayout rl_collection_food;
    private ImageView iv_collection_food;
    private TextView tv_collection_food;
    private ImageView iv_collection_food_more;
    private String phone_number;
    private RelativeLayout rl_feedback;
    private ImageView iv_feedback;
    private TextView tv_feedback;
    private ImageView iv_feedback_more;
    private RelativeLayout rl_about_us;
    private ImageView iv_about_us;
    private TextView tv_about_us;
    private ImageView iv_about_us_more;
    private RelativeLayout rl_my_order;
    private LinearLayout ll_order_wait_pay;
    private LinearLayout ll_order_wait_send;
    private LinearLayout ll_order_wait_get;
    private LinearLayout ll_order_wait_evaluate;
    private TextView tv_my_order;
    private TextView tv_all_order;
    private TextView tv_order_wait_pay;
    private TextView tv_order_wait_send;
    private TextView tv_order_wait_get;
    private TextView tv_order_wait_evaluate;
    private ImageView iv_order_wait_evaluate;
    private ImageView iv_order_wait_get;
    private ImageView iv_order_wait_send;
    private ImageView iv_order_wait_pay;
    private ImageView iv_all_order;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_myself = inflater.inflate(R.layout.fragment_myself, null);
        initView();
        setOnClickListener();
        return fragment_myself;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "暂无登录信息");
        String nickname = userInfo.getString("nickname", "点击  登录/注册");
        String userHead = userInfo.getString("userHead", "");
        if (userHead.length() != 0) {
            File file = new File(userHead);
            boolean exists = file.exists();
            if (file.length() > 0) {
                Glide.with(getActivity()).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
            } else {
                OssHandler ossHandler = new OssHandler(getActivity(), iv_user_head, true);
                DownloadUtils.downloadFileFromOss(file, ossHandler, ProjectProperties.BUCKET_NAME, "xuzhanxin/" + userHead);
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.user_head_default).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        }
        if (phone_number.equals("暂无登录信息")) {
            tv_username.setText(phone_number);
        } else {
            tv_username.setText("ID:" + phone_number);
        }
        tv_nickname.setText(nickname);
    }

    private void setOnClickListener() {
        tv_nickname.setOnClickListener(this);
        ll_user_info.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        iv_location_more.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        iv_setting_more.setOnClickListener(this);
        rl_contact_us.setOnClickListener(this);
        iv_contact_us.setOnClickListener(this);
        tv_contact_us.setOnClickListener(this);
        iv_contact_us_more.setOnClickListener(this);
        rl_collection_submit.setOnClickListener(this);
        iv_collection_submit.setOnClickListener(this);
        tv_collection_submit.setOnClickListener(this);
        iv_collection_submit_more.setOnClickListener(this);
        rl_collection_food.setOnClickListener(this);
        iv_collection_food.setOnClickListener(this);
        tv_collection_food.setOnClickListener(this);
        iv_collection_food_more.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        iv_feedback.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        iv_feedback_more.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        iv_about_us.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
        iv_about_us_more.setOnClickListener(this);
        rl_my_order.setOnClickListener(this);
        ll_order_wait_pay.setOnClickListener(this);
        ll_order_wait_send.setOnClickListener(this);
        ll_order_wait_get.setOnClickListener(this);
        ll_order_wait_evaluate.setOnClickListener(this);
        tv_my_order.setOnClickListener(this);
        tv_all_order.setOnClickListener(this);
        tv_order_wait_pay.setOnClickListener(this);
        tv_order_wait_send.setOnClickListener(this);
        tv_order_wait_get.setOnClickListener(this);
        tv_order_wait_evaluate.setOnClickListener(this);
        iv_order_wait_evaluate.setOnClickListener(this);
        iv_order_wait_get.setOnClickListener(this);
        iv_order_wait_send.setOnClickListener(this);
        iv_order_wait_pay.setOnClickListener(this);
        iv_all_order.setOnClickListener(this);
    }

    private void initView() {
        tv_nickname = fragment_myself.findViewById(R.id.tv_nickname);
        ll_user_info = fragment_myself.findViewById(R.id.ll_user_info);
        tv_username = fragment_myself.findViewById(R.id.tv_username);
        iv_user_head = fragment_myself.findViewById(R.id.iv_user_head);
        rl_location = fragment_myself.findViewById(R.id.rl_location);
        iv_location = fragment_myself.findViewById(R.id.iv_location);
        tv_location = fragment_myself.findViewById(R.id.tv_location);
        iv_location_more = fragment_myself.findViewById(R.id.iv_location_more);
        rl_setting = fragment_myself.findViewById(R.id.rl_setting);
        iv_setting = fragment_myself.findViewById(R.id.iv_setting);
        tv_setting = fragment_myself.findViewById(R.id.tv_setting);
        iv_setting_more = fragment_myself.findViewById(R.id.iv_setting_more);
        rl_contact_us = fragment_myself.findViewById(R.id.rl_contact_us);
        iv_contact_us = fragment_myself.findViewById(R.id.iv_contact_us);
        tv_contact_us = fragment_myself.findViewById(R.id.tv_contact_us);
        iv_contact_us_more = fragment_myself.findViewById(R.id.iv_contact_us_more);
        rl_collection_submit = fragment_myself.findViewById(R.id.rl_collection_submit);
        iv_collection_submit = fragment_myself.findViewById(R.id.iv_collection_submit);
        tv_collection_submit = fragment_myself.findViewById(R.id.tv_collection_submit);
        iv_collection_submit_more = fragment_myself.findViewById(R.id.iv_collection_submit_more);
        rl_collection_food = fragment_myself.findViewById(R.id.rl_collection_food);
        iv_collection_food = fragment_myself.findViewById(R.id.iv_collection_food);
        tv_collection_food = fragment_myself.findViewById(R.id.tv_collection_food);
        iv_collection_food_more = fragment_myself.findViewById(R.id.iv_collection_food_more);
        rl_feedback = fragment_myself.findViewById(R.id.rl_feedback);
        iv_feedback = fragment_myself.findViewById(R.id.iv_feedback);
        tv_feedback = fragment_myself.findViewById(R.id.tv_feedback);
        iv_feedback_more = fragment_myself.findViewById(R.id.iv_feedback_more);
        rl_about_us = fragment_myself.findViewById(R.id.rl_about_us);
        iv_about_us = fragment_myself.findViewById(R.id.iv_about_us);
        tv_about_us = fragment_myself.findViewById(R.id.tv_about_us);
        iv_about_us_more = fragment_myself.findViewById(R.id.iv_about_us_more);
        rl_my_order = fragment_myself.findViewById(R.id.rl_my_order);
        ll_order_wait_pay = fragment_myself.findViewById(R.id.ll_order_wait_pay);
        ll_order_wait_send = fragment_myself.findViewById(R.id.ll_order_wait_send);
        ll_order_wait_get = fragment_myself.findViewById(R.id.ll_order_wait_get);
        ll_order_wait_evaluate = fragment_myself.findViewById(R.id.ll_order_wait_evaluate);
        tv_my_order = fragment_myself.findViewById(R.id.tv_my_order);
        tv_all_order = fragment_myself.findViewById(R.id.tv_all_order);
        tv_order_wait_pay = fragment_myself.findViewById(R.id.tv_order_wait_pay);
        tv_order_wait_send = fragment_myself.findViewById(R.id.tv_order_wait_send);
        tv_order_wait_get = fragment_myself.findViewById(R.id.tv_order_wait_get);
        tv_order_wait_evaluate = fragment_myself.findViewById(R.id.tv_order_wait_evaluate);
        iv_all_order = fragment_myself.findViewById(R.id.iv_all_order);
        iv_order_wait_pay = fragment_myself.findViewById(R.id.iv_order_wait_pay);
        iv_order_wait_send = fragment_myself.findViewById(R.id.iv_order_wait_send);
        iv_order_wait_get = fragment_myself.findViewById(R.id.iv_order_wait_get);
        iv_order_wait_evaluate = fragment_myself.findViewById(R.id.iv_order_wait_evaluate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nickname:
            case R.id.ll_user_info:
            case R.id.tv_username:
            case R.id.iv_user_head:
                SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                phone_number = userInfo.getString("phone_number", "暂无登录信息");
                String nickname = userInfo.getString("nickname", "点击  登录/注册");
                if (phone_number.equals("暂无登录信息") && nickname.equals("点击  登录/注册")) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    break;
                } else {
                    Intent user_info = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(user_info);
                    break;
                }
            case R.id.rl_my_order:
            case R.id.tv_all_order:
            case R.id.iv_all_order:
            case R.id.tv_my_order:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent all_order = new Intent(getActivity(), OrderActivity.class);
                all_order.putExtra("type",0);
                startActivity(all_order);
                break;
            case R.id.ll_order_wait_pay:
            case R.id.tv_order_wait_pay:
            case R.id.iv_order_wait_pay:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent wait_pay = new Intent(getActivity(), OrderActivity.class);
                wait_pay.putExtra("type",1);
                startActivity(wait_pay);
                break;
            case R.id.ll_order_wait_send:
            case R.id.tv_order_wait_send:
            case R.id.iv_order_wait_send:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent wait_send = new Intent(getActivity(), OrderActivity.class);
                wait_send.putExtra("type",2);
                startActivity(wait_send);
                break;
            case R.id.ll_order_wait_get:
            case R.id.tv_order_wait_get:
            case R.id.iv_order_wait_get:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent wait_get = new Intent(getActivity(), OrderActivity.class);
                wait_get.putExtra("type",3);
                startActivity(wait_get);
                break;
            case R.id.ll_order_wait_evaluate:
            case R.id.tv_order_wait_evaluate:
            case R.id.iv_order_wait_evaluate:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent wait_evaluate = new Intent(getActivity(), OrderActivity.class);
                wait_evaluate.putExtra("type",4);
                startActivity(wait_evaluate);
                break;
            case R.id.rl_location:
            case R.id.iv_location:
            case R.id.tv_location:
            case R.id.iv_location_more:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent location = new Intent(getActivity(), LocationActivity.class);
                startActivity(location);
                break;
            case R.id.rl_setting:
            case R.id.iv_setting:
            case R.id.tv_setting:
            case R.id.iv_setting_more:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(setting);
                break;

            case R.id.rl_collection_submit:
            case R.id.iv_collection_submit:
            case R.id.tv_collection_submit:
            case R.id.iv_collection_submit_more:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent collection_submit = new Intent(getActivity(), CollectionSubmitActivity.class);
                startActivity(collection_submit);
                break;

            case R.id.rl_collection_food:
            case R.id.iv_collection_food:
            case R.id.tv_collection_food:
            case R.id.iv_collection_food_more:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent collection_food = new Intent(getActivity(), CollectionFoodActivity.class);
                startActivity(collection_food);
                break;

            case R.id.rl_feedback:
            case R.id.iv_feedback:
            case R.id.tv_feedback:
            case R.id.iv_feedback_more:
                if (phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent feedback = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(feedback);
                break;

            case R.id.rl_about_us:
            case R.id.iv_about_us:
            case R.id.tv_about_us:
            case R.id.iv_about_us_more:
                Intent about_us = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(about_us);
                break;

            case R.id.rl_contact_us:
            case R.id.iv_contact_us:
            case R.id.tv_contact_us:
            case R.id.iv_contact_us_more:
                PermissionUtil.requestPower(getActivity(), getActivity(), Manifest.permission.CALL_PHONE);
                TwoButtonDialog contact_us = new TwoButtonDialog();
                String title = "联系我们";
                String message = "拨打电话：18840919546";
                final String telephone = "18840919546";
                contact_us.show(title, message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "未授权拨打电话,请设置开启权限", Toast.LENGTH_SHORT).show();
                            PermissionController.gotoMeizuPermission(getActivity());
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + telephone));
                            getActivity().startActivity(intent);
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "取消呼叫", Toast.LENGTH_SHORT).show();
                    }
                }, getActivity().getFragmentManager());
                break;
        }
    }
}
