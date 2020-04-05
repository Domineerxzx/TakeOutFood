package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.RecommendAdapter;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.controllers.TypeRecommendManager;

import java.util.List;

public class TypeRecommendActivity extends Activity implements View.OnClickListener,OnItemClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_content;
    private TypeRecommendManager typeRecommendManager;
    private List<FoodInfo> foodList;
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recommend);

        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            tv_title.setText(title);
        }
        int type = intent.getIntExtra("type",-1);
        if (type != -1) {
            typeRecommendManager = new TypeRecommendManager(this);
            foodList = typeRecommendManager.getFoodList(type);
            rv_content.setLayoutManager(new GridLayoutManager(this,2));
            recommendAdapter = new RecommendAdapter(this, foodList);
            rv_content.setAdapter(recommendAdapter);
        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, FoodDetailsActivity.class);
        intent.putExtra("foodInfo", foodList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
