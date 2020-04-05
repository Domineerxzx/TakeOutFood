package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;

import java.util.List;

public class SizeChooseAdapter extends RecyclerView.Adapter<SizeChooseAdapter.ViewHolder> {

    private Context context;

    private List<FoodSizeInfo> foodSizeInfoList;

    private OnItemClickListener onItemClickListener;

    public SizeChooseAdapter(Context context, List<FoodSizeInfo> foodSizeInfoList) {
        this.context = context;
        this.foodSizeInfoList = foodSizeInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SizeChooseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_size, viewGroup, false);
        SizeChooseAdapter.ViewHolder viewHolder = new SizeChooseAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SizeChooseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_size.setText(foodSizeInfoList.get(i).getSize_name());
    }

    @Override
    public int getItemCount() {
        return foodSizeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            tv_size = itemView.findViewById(R.id.tv_size);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getPosition());
        }
    }
}
