package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.OrderInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.controllers.OrderManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.views.MyListView;

import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,6:40
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class AllOrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderInfo> allOrderInfoList;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private OrderManager orderManager;
    private OrderContentAdapter orderContentAdapter;

    public AllOrderAdapter(Context context, List<OrderInfo> allOrderInfoList) {
        this.context = context;
        this.allOrderInfoList = allOrderInfoList;
    }

    public List<OrderInfo> getAllOrderInfoList() {
        return allOrderInfoList;
    }

    public void setAllOrderInfoList(List<OrderInfo> allOrderInfoList) {
        this.allOrderInfoList = allOrderInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return allOrderInfoList.size();
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_all_order, null);
            viewHolder.lv_all_order_item = convertView.findViewById(R.id.lv_all_order_item);
            viewHolder.tv_order_id_content = convertView.findViewById(R.id.tv_order_id_content);
            viewHolder.tv_order_state = convertView.findViewById(R.id.tv_order_state);
            viewHolder.tv_count_price = convertView.findViewById(R.id.tv_count_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_order_id_content.setText(String.valueOf(allOrderInfoList.get(position).get_id()));
        switch (allOrderInfoList.get(position).getOrder_state()) {
            case ProjectProperties.ORDER_STATE_WAIT_PAY:
                viewHolder.tv_order_state.setText("待付款");
                break;
            case ProjectProperties.ORDER_STATE_WAIT_SEND:
                viewHolder.tv_order_state.setText("待发货");
                break;
            case ProjectProperties.ORDER_STATE_WAIT_GET:
                viewHolder.tv_order_state.setText("待收货");
                break;
            case ProjectProperties.ORDER_STATE_WAIT_EVALYATE:
                viewHolder.tv_order_state.setText("待评价");
                break;
            case ProjectProperties.ORDER_STATE_CANCEL:
                viewHolder.tv_order_state.setText("已取消");
                break;
            case ProjectProperties.ORDER_STATE_DONE:
                viewHolder.tv_order_state.setText("已完成");
                break;
        }
        orderManager = new OrderManager(context);
        shoppingCartInfoList = orderManager.getOrderContentInfoList(allOrderInfoList.get(position).get_id());
        int count_price = orderManager.getOrderCountPrice(shoppingCartInfoList);
        viewHolder.tv_count_price.setText(String.valueOf(count_price));
        orderContentAdapter = new OrderContentAdapter(context, shoppingCartInfoList);
        viewHolder.lv_all_order_item.setAdapter(orderContentAdapter);
        return convertView;
    }

    private class ViewHolder {
        private MyListView lv_all_order_item;
        private TextView tv_order_id_content;
        private TextView tv_order_state;
        private TextView tv_count_price;
    }
}
