package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.adapters.SearchHistoryAdapter;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.controllers.SearchManager;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.TwoButtonDialog;
import com.triplebro.domineer.takeoutfood.views.MyListView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private MyListView lv_history;
    private RecyclerView rv_recommend_search;
    private RecyclerView rv_search_result;
    private ImageView iv_close_search;
    private SearchHistoryAdapter searchHistoryAdapter;
    private RecommendAdapter searchResultAdapter;
    private RecommendAdapter recommendAdapter;
    private List<FoodInfo> foodRecommendInfoList;
    private EditText et_search;
    private SearchManager searchManager;
    private RelativeLayout rl_history;
    private RelativeLayout rl_no_search;
    private List<String> searchHistoryList = new ArrayList<>();
    private SharedPreferences searchHistory;
    private String searchHistoryListString;
    private TextView tv_no_history;
    private TextView tv_clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_search.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        lv_history.setOnItemClickListener(this);
        tv_clear_history.setOnClickListener(this);
    }

    private void initData() {
        searchManager = new SearchManager(this);
        foodRecommendInfoList = searchManager.getFoodRecommendInfoList();
        recommendAdapter = new RecommendAdapter(this, foodRecommendInfoList);
        rv_recommend_search.setAdapter(recommendAdapter);
        searchHistory = getSharedPreferences("searchHistory", MODE_PRIVATE);
        searchHistoryListString = searchHistory.getString("searchHistory", "");
        String[] searchHistoryArray = searchHistoryListString.split(",");
        for (int i = 0; i < searchHistoryArray.length; i++) {
            if(searchHistoryArray[i].length() == 0){
                continue;
            }
            searchHistoryList.add(searchHistoryArray[i]);
        }
        if(searchHistoryList.size() == 0){
            tv_no_history.setVisibility(View.VISIBLE);
            lv_history.setVisibility(View.GONE);
        }else{
            tv_no_history.setVisibility(View.GONE);
            lv_history.setVisibility(View.VISIBLE);
        }
        searchHistoryAdapter = new SearchHistoryAdapter(this, searchHistoryList,tv_no_history,lv_history);
        lv_history.setAdapter(searchHistoryAdapter);
        recommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchActivity.this, FoodDetailsActivity.class);
                intent.putExtra("foodInfo", foodRecommendInfoList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
    }

    private void initView() {
        lv_history = (MyListView) findViewById(R.id.lv_history);
        rv_recommend_search = (RecyclerView) findViewById(R.id.rv_recommend_search);
        rv_search_result = (RecyclerView) findViewById(R.id.rv_search_result);
        iv_close_search = (ImageView) findViewById(R.id.iv_close_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recommend_search.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_search_result.setLayoutManager(gridLayoutManager);
        et_search = (EditText) findViewById(R.id.et_search);
        rl_history = (RelativeLayout) findViewById(R.id.rl_history);
        rl_no_search = (RelativeLayout) findViewById(R.id.rl_no_search);
        tv_no_history = (TextView) findViewById(R.id.tv_no_history);
        tv_clear_history = (TextView) findViewById(R.id.tv_clear_history);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_search:
                finish();
                break;
            case R.id.tv_clear_history:
                TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
                twoButtonDialog.show("清空搜索历史记录", "是否确认清空搜索历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor edit = searchHistory.edit();
                        edit.clear();
                        edit.commit();
                        searchHistoryList.clear();
                        searchHistoryAdapter.setStrings(searchHistoryList);
                        tv_no_history.setVisibility(View.VISIBLE);
                        lv_history.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },getFragmentManager());
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        if (s.length() > 0) {
            final List<FoodInfo> searchInfoList = searchManager.searchInfoList(s.toString().trim());
            if (searchInfoList.size() != 0) {
                rl_history.setVisibility(View.GONE);
                rv_search_result.setVisibility(View.VISIBLE);
                rl_no_search.setVisibility(View.GONE);
                searchResultAdapter = new RecommendAdapter(this, searchInfoList);
                rv_search_result.setAdapter(searchResultAdapter);
                searchResultAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(SearchActivity.this, FoodDetailsActivity.class);
                        intent.putExtra("foodInfo", searchInfoList.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view) {

                    }
                });
                searchHistoryListString = searchHistory.getString("searchHistory", "");
                if(searchHistoryListString.indexOf(s.toString().trim()) != -1){
                    return;
                }
                searchHistoryListString = searchHistoryListString +s.toString().trim()+",";
                SharedPreferences.Editor edit = searchHistory.edit();
                edit.clear();
                edit.putString("searchHistory",searchHistoryListString);
                edit.commit();
                tv_no_history.setVisibility(View.GONE);
                lv_history.setVisibility(View.VISIBLE);
                searchHistoryList.add(s.toString().trim());
                searchHistoryAdapter.setStrings(searchHistoryList);
            } else {
                rl_history.setVisibility(View.GONE);
                rv_search_result.setVisibility(View.GONE);
                rl_no_search.setVisibility(View.VISIBLE);
            }
        } else {
            rl_history.setVisibility(View.VISIBLE);
            rv_search_result.setVisibility(View.GONE);
            rl_no_search.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        et_search.setText(searchHistoryList.get(position));
    }
}
