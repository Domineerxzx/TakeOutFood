package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.controllers.OrderManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.views.MyListView;

import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,12:19
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class WaitPayOrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfo> waitPayOrderInfoList;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private OrderManager orderManager;
    private OrderContentAdapter orderContentAdapter;
    private SharedPreferences userInfo;
    private String phone_number;

    public WaitPayOrderAdapter(Context context, List<OrderInfo> waitPayOrderInfoList) {
        this.context = context;
        this.waitPayOrderInfoList = waitPayOrderInfoList;
    }

    public List<OrderInfo> getWaitPayOrderInfoList() {
        return waitPayOrderInfoList;
    }

    public void setWaitPayOrderInfoList(List<OrderInfo> waitPayOrderInfoList) {
        this.waitPayOrderInfoList = waitPayOrderInfoList;
    }

    @Override
    public int getCount() {
        return waitPayOrderInfoList.size();
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_wait_pay_order, null);
            viewHolder.lv_wait_pay_order_item = convertView.findViewById(R.id.lv_wait_pay_order_item);
            viewHolder.tv_pay = convertView.findViewById(R.id.tv_pay);
            viewHolder.tv_cancel_order = convertView.findViewById(R.id.tv_cancel_order);
            viewHolder.tv_count_price = convertView.findViewById(R.id.tv_count_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        orderManager = new OrderManager(context);
        shoppingCartInfoList = orderManager.getOrderContentInfoList(waitPayOrderInfoList.get(position).get_id());
        int count_price = orderManager.getOrderCountPrice(shoppingCartInfoList);
        viewHolder.tv_count_price.setText(String.valueOf(count_price));
        orderContentAdapter = new OrderContentAdapter(context, shoppingCartInfoList);
        viewHolder.lv_wait_pay_order_item.setAdapter(orderContentAdapter);
        viewHolder.tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderManager.updateWaitPayToWaitSend(waitPayOrderInfoList.get(position).get_id());
                waitPayOrderInfoList = orderManager.getStateOrderInfoList(phone_number, ProjectProperties.ORDER_STATE_WAIT_PAY);
                notifyDataSetChanged();
            }
        });
        viewHolder.tv_cancel_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "取消订单功能目前暂未开放", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private MyListView lv_wait_pay_order_item;
        private TextView tv_count_price;
        private TextView tv_cancel_order;
        private TextView tv_pay;
    }
}
