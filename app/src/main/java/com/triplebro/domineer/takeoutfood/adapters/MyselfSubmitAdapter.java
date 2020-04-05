package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.SubmitInfo;
import com.triplebro.domineer.takeoutfood.providers.DatabaseOP;

import java.util.List;

public class MyselfSubmitAdapter extends BaseAdapter {

    private Context context;
    private List<SubmitInfo> data;
    private List<String> submitImageList;

    public MyselfSubmitAdapter(Context context, List<SubmitInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_myself_submit, null);
            viewHolder.tv_submit = convertView.findViewById(R.id.tv_submit);
            viewHolder.rv_photo_wall = convertView.findViewById(R.id.rv_photo_wall);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_submit.setText(data.get(position).getSubmit_content());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewHolder.rv_photo_wall.setLayoutManager(gridLayoutManager);
        DatabaseOP databaseOP = new DatabaseOP(context);
        submitImageList = databaseOP.getSubmitImageInfoList(data.get(position).getSubmit_id());
        viewHolder.rv_photo_wall.setAdapter(new PhotoWallAdapter(context,submitImageList));
        return convertView;
    }



    private class ViewHolder{
        private TextView tv_submit;
        private RecyclerView rv_photo_wall;
    }
}