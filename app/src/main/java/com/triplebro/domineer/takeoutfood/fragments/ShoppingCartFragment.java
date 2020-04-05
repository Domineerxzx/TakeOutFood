package com.triplebro.domineer.takeoutfood.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.FoodDetailsActivity;
import com.triplebro.domineer.takeoutfood.adapters.ShoppingCartAdapter;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.controllers.ShoppingCartManager;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.TwoButtonDialog;

import java.util.List;

public class ShoppingCartFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    private View fragment_ShoppingCart;
    private ListView lv_shopping_cart;
    private ShoppingCartManager shoppingCartManager;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private TextView tv_tip;
    private RelativeLayout rl_pay;
    private String phone_number;
    private TextView tv_count_price;
    private TextView tv_clear;
    private ShoppingCartAdapter shoppingCartAdapter;
    private TwoButtonDialog twoButtonDialog;
    private Button bt_commit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment_ShoppingCart = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        initView();
        initData();
        setOnClickListener();
        return fragment_ShoppingCart;
    }

    private void setOnClickListener() {
        tv_clear.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(phone_number.length() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("还没登录呢，神马都没有！！！");
            lv_shopping_cart.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
        }else if(shoppingCartInfoList.size() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("购物车空空如也，快去买点东西装满它吧！！！");
            lv_shopping_cart.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
        }else{
            lv_shopping_cart.setVisibility(View.VISIBLE);
            rl_pay.setVisibility(View.VISIBLE);
            tv_tip.setVisibility(View.GONE);
        }
    }

    private void initData() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        shoppingCartManager = new ShoppingCartManager(getActivity());
        shoppingCartManager.sumShoppingCart(tv_count_price);
        shoppingCartInfoList = shoppingCartManager.getShoppingCartInfoList();
        shoppingCartAdapter = new ShoppingCartAdapter(getActivity(), shoppingCartInfoList,shoppingCartManager,phone_number,tv_tip,lv_shopping_cart,rl_pay);
        lv_shopping_cart.setAdapter(shoppingCartAdapter);
        lv_shopping_cart.setOnItemClickListener(this);
    }

    private void initView() {
        lv_shopping_cart = fragment_ShoppingCart.findViewById(R.id.lv_shopping_cart);
        tv_tip = fragment_ShoppingCart.findViewById(R.id.tv_tip);
        rl_pay = fragment_ShoppingCart.findViewById(R.id.rl_pay);
        tv_count_price = fragment_ShoppingCart.findViewById(R.id.tv_count_price);
        tv_clear = fragment_ShoppingCart.findViewById(R.id.tv_clear);
        bt_commit = (Button) fragment_ShoppingCart.findViewById(R.id.bt_commit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
                if(phone_number.length()!=11){
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(twoButtonDialog == null){
                    twoButtonDialog = new TwoButtonDialog();
                }
                twoButtonDialog.show("清空购物车", "确定要清空购物车吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shoppingCartManager.clearShoppingCart(shoppingCartInfoList);
                        shoppingCartInfoList = shoppingCartManager.getShoppingCartInfoList();
                        shoppingCartAdapter.setShoppingCartInfoList(shoppingCartInfoList);
                        if(shoppingCartInfoList.size() == 0){
                            tv_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("购物车空空如也，快去买点东西装满它吧！！！");
                            lv_shopping_cart.setVisibility(View.GONE);
                            rl_pay.setVisibility(View.GONE);
                        }else{
                            lv_shopping_cart.setVisibility(View.VISIBLE);
                            rl_pay.setVisibility(View.VISIBLE);
                            tv_tip.setVisibility(View.GONE);
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },getActivity().getFragmentManager());
                break;
            case R.id.bt_commit:
                long order_id = shoppingCartManager.commitOrder();
                shoppingCartManager.commitOrderContent(order_id,shoppingCartInfoList);
                shoppingCartManager.updateShoppingCartInfo(shoppingCartInfoList);
                shoppingCartInfoList = shoppingCartManager.getShoppingCartInfoList();
                shoppingCartAdapter.setShoppingCartInfoList(shoppingCartInfoList);
                if(shoppingCartInfoList.size() == 0){
                    tv_tip.setVisibility(View.VISIBLE);
                    tv_tip.setText("购物车空空如也，快去买点东西装满它吧！！！");
                    lv_shopping_cart.setVisibility(View.GONE);
                    rl_pay.setVisibility(View.GONE);
                }else{
                    lv_shopping_cart.setVisibility(View.VISIBLE);
                    rl_pay.setVisibility(View.VISIBLE);
                    tv_tip.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FoodDetailsActivity.class);
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setFood_id(shoppingCartInfoList.get(position).getFood_id());
        foodInfo.setFood_name(shoppingCartInfoList.get(position).getFood_name());
        foodInfo.setPrice(shoppingCartInfoList.get(position).getPrice());
        intent.putExtra("foodInfo",foodInfo);
        startActivity(intent);
    }
}
