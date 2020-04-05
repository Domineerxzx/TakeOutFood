package com.triplebro.domineer.takeoutfood.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.controllers.ShoppingCartManager;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.TwoButtonDialog;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private ShoppingCartManager shoppingCartManager;
    private String phone_number;
    private TextView tv_tip;
    private ListView lv_shopping_cart;
    private RelativeLayout rl_pay;

    public ShoppingCartAdapter(Context context, List<ShoppingCartInfo> shoppingCartInfoList, ShoppingCartManager shoppingCartManager, String phone_number, TextView tv_tip, ListView lv_shopping_cart, RelativeLayout rl_pay) {
        this.context = context;
        this.shoppingCartInfoList = shoppingCartInfoList;
        this.shoppingCartManager = shoppingCartManager;
        this.phone_number = phone_number;
        this.tv_tip = tv_tip;
        this.lv_shopping_cart = lv_shopping_cart;
        this.rl_pay = rl_pay;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shopping_cart, null);
            viewHolder.iv_shopping_cart = convertView.findViewById(R.id.iv_shopping_cart);
            viewHolder.tv_shopping_cart_name = convertView.findViewById(R.id.tv_shopping_cart_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            viewHolder.tv_size = convertView.findViewById(R.id.tv_size);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_count);
            viewHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        File file = new File(shoppingCartInfoList.get(position).getFood_image());
        if (file.length() > 0) {
            Glide.with(context).load(file).into(viewHolder.iv_shopping_cart);
        }else{
            OssHandler ossHandler = new OssHandler(context, viewHolder.iv_shopping_cart);
            DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+shoppingCartInfoList.get(position).getFood_image());
        }
        viewHolder.tv_shopping_cart_name.setText(shoppingCartInfoList.get(position).getFood_name());
        viewHolder.tv_count.setText(String.valueOf(shoppingCartInfoList.get(position).getCount()));
        viewHolder.tv_price.setText(String.valueOf(shoppingCartInfoList.get(position).getPrice()));
        viewHolder.tv_size.setText(shoppingCartInfoList.get(position).getSize_name());
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
                twoButtonDialog.show("删除商品", "确定要将该商品从购物车中删除吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShoppingCartInfo remove = shoppingCartInfoList.remove(position);
                        shoppingCartManager.deleteFood(remove.getFood_id(), remove);
                        notifyDataSetChanged();
                        if (phone_number.length() == 0) {
                            tv_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("还没登录呢，查不到信息哦！！！");
                            lv_shopping_cart.setVisibility(View.GONE);
                            rl_pay.setVisibility(View.GONE);
                        } else if (shoppingCartInfoList.size() == 0) {
                            tv_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("购物车空空如也，快去买点东西装满它吧！！！");
                            lv_shopping_cart.setVisibility(View.GONE);
                            rl_pay.setVisibility(View.GONE);
                        } else {
                            lv_shopping_cart.setVisibility(View.VISIBLE);
                            rl_pay.setVisibility(View.VISIBLE);
                            tv_tip.setVisibility(View.GONE);
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, ((Activity) context).getFragmentManager());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_shopping_cart;
        private TextView tv_shopping_cart_name;
        private TextView tv_price;
        private TextView tv_count;
        private TextView tv_size;
        private ImageView iv_delete;
    }
}
