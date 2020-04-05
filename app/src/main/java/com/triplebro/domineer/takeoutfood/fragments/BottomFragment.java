package com.triplebro.domineer.takeoutfood.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.triplebro.domineer.takeoutfood.R;

import org.jetbrains.annotations.Nullable;

public class BottomFragment extends Fragment implements View.OnClickListener {

    private View fragment_bottom;
    private LinearLayout ll_first;
    private LinearLayout ll_type;
    private LinearLayout ll_found;
    private LinearLayout ll_shopping_cart;
    private LinearLayout ll_myself;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Button bt_first;
    private Button bt_type;
    private Button bt_found;
    private Button bt_shopping_cart;
    private Button bt_myself;
    private TextView tv_first;
    private TextView tv_type;
    private TextView tv_found;
    private TextView tv_shopping_cart;
    private TextView tv_myself;

    private Button lastFunctionButton;
    private TextView lastFunctionTextView;
    private ScaleAnimation scaleAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_bottom = inflater.inflate(R.layout.fragment_bottom, null);
        initView();
        initScale();
        setOnClickListener();
        fragmentManager = getActivity().getFragmentManager();
        return fragment_bottom;
    }

    private void initScale() {
        scaleAnimation = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f,50,50);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillBefore(true);
    }

    private void initView() {
        ll_first = fragment_bottom.findViewById(R.id.ll_first);
        ll_type = fragment_bottom.findViewById(R.id.ll_type);
        ll_found = fragment_bottom.findViewById(R.id.ll_found);
        ll_shopping_cart = fragment_bottom.findViewById(R.id.ll_shopping_cart);
        ll_myself = fragment_bottom.findViewById(R.id.ll_myself);
        bt_first = fragment_bottom.findViewById(R.id.bt_first);
        bt_type = fragment_bottom.findViewById(R.id.bt_type);
        bt_found = fragment_bottom.findViewById(R.id.bt_found);
        bt_shopping_cart = fragment_bottom.findViewById(R.id.bt_shopping_cart);
        bt_myself = fragment_bottom.findViewById(R.id.bt_myself);
        tv_first = fragment_bottom.findViewById(R.id.tv_first);
        tv_type = fragment_bottom.findViewById(R.id.tv_type);
        tv_found = fragment_bottom.findViewById(R.id.tv_found);
        tv_shopping_cart = fragment_bottom.findViewById(R.id.tv_shopping_cart);
        tv_myself = fragment_bottom.findViewById(R.id.tv_myself);

        if (lastFunctionTextView == null) {
            lastFunctionTextView = tv_first;
        }
        if (lastFunctionButton == null) {
            lastFunctionButton = bt_first;
        }

        bt_found.bringToFront();

    }

    public void setOnClickListener() {

        ll_first.setOnClickListener(this);
        ll_type.setOnClickListener(this);
        ll_found.setOnClickListener(this);
        ll_shopping_cart.setOnClickListener(this);
        ll_myself.setOnClickListener(this);
        bt_first.setOnClickListener(this);
        bt_type.setOnClickListener(this);
        bt_found.setOnClickListener(this);
        bt_shopping_cart.setOnClickListener(this);
        bt_myself.setOnClickListener(this);
        tv_first.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_found.setOnClickListener(this);
        tv_shopping_cart.setOnClickListener(this);
        tv_myself.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
            case R.id.bt_first:
            case R.id.tv_first:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new FirstPageFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_first);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_first.setTextColor(Color.BLACK);
                lastFunctionTextView = tv_first;
                break;
            case R.id.ll_type:
            case R.id.bt_type:
            case R.id.tv_type:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new TypeFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_type);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_type.setTextColor(Color.BLACK);
                lastFunctionTextView = tv_type;
                break;
            case R.id.ll_found:
            case R.id.bt_found:
            case R.id.tv_found:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new FoundFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_found);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_found.setTextColor(Color.BLACK);
                lastFunctionTextView = tv_found;
                break;
            case R.id.ll_shopping_cart:
            case R.id.bt_shopping_cart:
            case R.id.tv_shopping_cart:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new ShoppingCartFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_shopping_cart);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_shopping_cart.setTextColor(Color.BLACK);
                lastFunctionTextView = tv_shopping_cart;
                break;
            case R.id.ll_myself:
            case R.id.bt_myself:
            case R.id.tv_myself:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_top, new MyselfFragment());
                transaction.commit();
                changeImageForButton(lastFunctionButton, bt_myself);
                lastFunctionTextView.setTextColor(Color.GRAY);
                tv_myself.setTextColor(Color.BLACK);
                lastFunctionTextView = tv_myself;
                break;
        }
    }

    private void changeImageForButton(Button lastFunctionButton, Button onClickButton) {
        if(lastFunctionButton == onClickButton){
            return;
        }
        switch (lastFunctionButton.getId()) {
            case R.id.bt_first:
                lastFunctionButton.setBackgroundResource(R.mipmap.firstpage);
                break;
            case R.id.bt_type:
                lastFunctionButton.setBackgroundResource(R.mipmap.type);
                break;
            case R.id.bt_found:
                lastFunctionButton.setBackgroundResource(R.mipmap.found);
                break;
            case R.id.bt_shopping_cart:
                lastFunctionButton.setBackgroundResource(R.mipmap.shopping_cart);
                break;
            case R.id.bt_myself:
                lastFunctionButton.setBackgroundResource(R.mipmap.myself);
                break;
        }
        switch (onClickButton.getId()) {
            case R.id.bt_first:
                bt_first.startAnimation(scaleAnimation);
                onClickButton.setBackgroundResource(R.mipmap.firstpage_onclick);
                break;
            case R.id.bt_type:
                bt_type.startAnimation(scaleAnimation);
                onClickButton.setBackgroundResource(R.mipmap.type_onclick);
                break;
            case R.id.bt_found:
                bt_found.startAnimation(scaleAnimation);
                onClickButton.setBackgroundResource(R.mipmap.found_onclick);
                break;
            case R.id.bt_shopping_cart:
                bt_shopping_cart.startAnimation(scaleAnimation);
                onClickButton.setBackgroundResource(R.mipmap.shopping_cart_onclick);
                break;
            case R.id.bt_myself:
                bt_myself.startAnimation(scaleAnimation);
                onClickButton.setBackgroundResource(R.mipmap.myself_onclick);
                break;
        }
        this.lastFunctionButton = onClickButton;
    }

}
