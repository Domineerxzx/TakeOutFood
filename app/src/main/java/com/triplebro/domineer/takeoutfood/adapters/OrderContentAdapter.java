package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

/**
 * @author Domineer
 * @data 2019/3/24,10:10
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class OrderContentAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartInfo> shoppingCartInfoList;

    public OrderContentAdapter(Context context, List<ShoppingCartInfo> shoppingCartInfoList) {
        this.context = context;
        this.shoppingCartInfoList = shoppingCartInfoList;
    }

    public List<ShoppingCartInfo> getShoppingCartInfoList() {
        return shoppingCartInfoList;
    }

    public void setShoppingCartInfoList(List<ShoppingCartInfo> shoppingCartInfoList) {
        this.shoppingCartInfoList = shoppingCartInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shoppingCartInfoList.size();
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
            convertView = View.inflate(context, R.layout.item_order_content, null);
            viewHolder.iv_shopping_cart = convertView.findViewById(R.id.iv_shopping_cart);
            viewHolder.tv_shopping_cart_name = convertView.findViewById(R.id.tv_shopping_cart_name);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_count);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            viewHolder.tv_size = convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_shopping_cart_name.setText(shoppingCartInfoList.get(position).getFood_name());
        viewHolder.tv_price.setText(String.valueOf(shoppingCartInfoList.get(position).getPrice()));
        viewHolder.tv_count.setText(String.valueOf(shoppingCartInfoList.get(position).getCount()));
        viewHolder.tv_size.setText(shoppingCartInfoList.get(position).getSize_name());
        File file = new File(shoppingCartInfoList.get(position).getFood_image());
        if (file.length() > 0) {
            Glide.with(context).load(file).into(viewHolder.iv_shopping_cart);
        } else {
            OssHandler ossHandler = new OssHandler(context);
            DownloadUtils.downloadFileFromOss(file, ossHandler, ProjectProperties.BUCKET_NAME, "xuzhanxin/" + shoppingCartInfoList.get(position).getFood_image());
            Glide.with(context).load(file).into(viewHolder.iv_shopping_cart);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_shopping_cart;
        private TextView tv_shopping_cart_name;
        private TextView tv_size;
        private TextView tv_price;
        private TextView tv_count;
    }
}
