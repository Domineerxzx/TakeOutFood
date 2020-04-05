package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;

import java.util.List;

public class SizeInfoAdapter extends BaseAdapter {

    private Context context;

    private List<FoodSizeInfo> foodSizeInfoList;

    public SizeInfoAdapter(Context context, List<FoodSizeInfo> foodSizeInfoList) {
        this.context = context;
        this.foodSizeInfoList = foodSizeInfoList;
    }

    public List<FoodSizeInfo> getFoodSizeInfoList() {
        return foodSizeInfoList;
    }

    public void setFoodSizeInfoList(List<FoodSizeInfo> foodSizeInfoList) {
        this.foodSizeInfoList = foodSizeInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodSizeInfoList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SizeInfoAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new SizeInfoAdapter.ViewHolder();
            convertView = View.inflate(context, R.layout.item_size_info, null);
            viewHolder.et_size_name = convertView.findViewById(R.id.et_size_name);
            viewHolder.et_size_count = convertView.findViewById(R.id.et_size_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SizeInfoAdapter.ViewHolder) convertView.getTag();
        }
        if (foodSizeInfoList.get(position).getSize_name() != null) {
            viewHolder.et_size_name.setText(foodSizeInfoList.get(position).getSize_name());
        }
        if (foodSizeInfoList.get(position).getSize_count() != 0) {
            viewHolder.et_size_count.setText(String.valueOf(foodSizeInfoList.get(position).getSize_count()));
        }
        viewHolder.et_size_name.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        viewHolder.et_size_count.setImeOptions(EditorInfo.IME_ACTION_DONE);
        viewHolder.et_size_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String size_name = viewHolder.et_size_name.getText().toString();
                if(size_name.length() == 0){
                    Toast.makeText(context, "尺码名称不能为空", Toast.LENGTH_SHORT).show();
                    return false;
                }
                foodSizeInfoList.get(position).setSize_name(size_name);
                viewHolder.et_size_count.requestFocus();
                return false;
            }
        });
        viewHolder.et_size_count.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int size_count = Integer.parseInt(viewHolder.et_size_count.getText().toString());
                if(size_count == 0){
                    Toast.makeText(context, "库存不能为零", Toast.LENGTH_SHORT).show();
                    return false;
                }
                foodSizeInfoList.get(position).setSize_count(size_count);
                viewHolder.et_size_count.clearFocus();
                return false;
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private EditText et_size_name;
        private EditText et_size_count;
    }
}
