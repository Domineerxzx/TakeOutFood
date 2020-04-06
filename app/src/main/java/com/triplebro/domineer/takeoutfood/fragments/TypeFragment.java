package com.triplebro.domineer.takeoutfood.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.ContentActivity;
import com.triplebro.domineer.takeoutfood.activities.SearchActivity;
import com.triplebro.domineer.takeoutfood.adapters.TypeContentAdapter;
import com.triplebro.domineer.takeoutfood.adapters.TypeNameAdapter;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.controllers.TypeManager;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TypeFragment extends Fragment implements AdapterView.OnItemClickListener,OnItemClickListener,View.OnClickListener {

    private View fragment_type;
    private ListView lv_type_name;
    private RecyclerView rv_type_content;
    private TypeManager typeManager;
    private List<TypeGeneralizeInfo> generalizeTypeList;
    private List<TypeConcreteInfo> concreteTypeList;
    private RelativeLayout rl_search;
    private ImageView iv_search;
    private TextView tv_search;
    private ImageView iv_scan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_type = inflater.inflate(R.layout.fragment_type, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_type;
    }

    private void setOnClickListener() {
        rl_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    private void initData() {
        typeManager = new TypeManager(getActivity());
        generalizeTypeList = typeManager.getGeneralizeTypeList();
        TypeNameAdapter typeNameAdapter = new TypeNameAdapter(getActivity(), generalizeTypeList, rv_type_content);
        lv_type_name.setAdapter(typeNameAdapter);
        concreteTypeList = typeManager.getConcreteTypeList(1);
        TypeContentAdapter typeContentAdapter = new TypeContentAdapter(getActivity(), concreteTypeList);
        rv_type_content.setAdapter(typeContentAdapter);
        typeContentAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        lv_type_name = fragment_type.findViewById(R.id.lv_type_name);
        lv_type_name.setOnItemClickListener(this);
        rv_type_content = fragment_type.findViewById(R.id.rv_type_content);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_type_content.setLayoutManager(gridLayoutManager);
        rl_search = (RelativeLayout) fragment_type.findViewById(R.id.rl_search);
        iv_search = (ImageView) fragment_type.findViewById(R.id.iv_search);
        tv_search = (TextView) fragment_type.findViewById(R.id.tv_search);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        concreteTypeList = typeManager.getConcreteTypeList(generalizeTypeList.get(position).getType_generalize_id());
        System.out.println(generalizeTypeList.get(position).getType_generalize_id()+"-------------------------------------------------------------------------------------");
        TypeContentAdapter typeContentAdapter = new TypeContentAdapter(getActivity(), concreteTypeList);
        rv_type_content.setAdapter(typeContentAdapter);
        typeContentAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("type",concreteTypeList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_search:
            case R.id.iv_search:
            case R.id.tv_search:
                Intent search = new Intent(getActivity(), SearchActivity.class);
                startActivity(search);
                break;
        }
    }
}
