package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.activities.UpdateAddressActivity;
import com.triplebro.domineer.takeoutfood.models.AddressInfo;

import java.util.List;

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<AddressInfo> addressInfoList;

    public AddressAdapter(Context context, List<AddressInfo> addressInfoList) {
        this.context = context;
        this.addressInfoList = addressInfoList;
    }

    public void setAddressInfoList(List<AddressInfo> addressInfoList) {
        this.addressInfoList = addressInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addressInfoList.size();
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

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_address,null);
            viewHolder.tv_address_name = convertView.findViewById(R.id.tv_address_name);
            viewHolder.tv_address = convertView.findViewById(R.id.tv_address);
            viewHolder.tv_address_postcode = convertView.findViewById(R.id.tv_address_postcode);
            viewHolder.bt_change_address_info = convertView.findViewById(R.id.bt_change_address_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_address_name.setText(addressInfoList.get(position).getName());
        viewHolder.tv_address.setText(addressInfoList.get(position).getLocation());
        viewHolder.tv_address_postcode.setText(String.valueOf(addressInfoList.get(position).getZip_code()));
        viewHolder.bt_change_address_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAddressActivity.class);
                intent.putExtra("AddressInfo",addressInfoList.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{

        private TextView tv_address_name;
        private TextView tv_address;
        private TextView tv_address_postcode;
        private Button bt_change_address_info;

    }
}
