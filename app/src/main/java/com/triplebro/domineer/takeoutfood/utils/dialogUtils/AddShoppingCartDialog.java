package com.triplebro.domineer.takeoutfood.utils.dialogUtils;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.SizeChooseAdapter;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.ShoppingCartInfo;
import com.triplebro.domineer.takeoutfood.database.TakeOutFoodOpenHelper;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;

import java.util.List;

public class AddShoppingCartDialog {

    private FoodSizeInfo foodSizeInfo;
    private static int size_count;

    public void showDialog(final Context context, final FoodInfo foodInfo, final List<FoodSizeInfo> foodSizeInfoList) {
        final Dialog mShareDialog = new Dialog(context, R.style.dialog_shopping_cart);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_shopping_cart, null);
        RecyclerView rv_size_bt = view.findViewById(R.id.rv_size_bt);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_add_shopping_cart = view.findViewById(R.id.tv_add_shopping_cart);
        final TextView tv_size = view.findViewById(R.id.tv_size);
        final TextView tv_food_count = view.findViewById(R.id.tv_food_count);
        final TextView tv_count = view.findViewById(R.id.tv_count);
        Button bt_count_down = view.findViewById(R.id.bt_count_down);
        Button bt_count_up = view.findViewById(R.id.bt_count_up);
        rv_size_bt.setLayoutManager(new GridLayoutManager(context, 5));
        SizeChooseAdapter sizeChooseAdapter = new SizeChooseAdapter(context, foodSizeInfoList);
        rv_size_bt.setAdapter(sizeChooseAdapter);
        sizeChooseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                foodSizeInfo = foodSizeInfoList.get(position);
                tv_food_count.setText(String.valueOf(foodSizeInfoList.get(position).getSize_count()));
                tv_size.setText(foodSizeInfoList.get(position).getSize_name() + "号");
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        if(foodSizeInfo == null){
            foodSizeInfo = foodSizeInfoList.get(0);
            tv_food_count.setText(String.valueOf(foodSizeInfo.getSize_count()));
            tv_size.setText(foodSizeInfo.getSize_name() + "号");
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        tv_add_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {

                    ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
                    shoppingCartInfo.setFood_id(foodInfo.getFood_id());
                    shoppingCartInfo.setFood_name(foodInfo.getFood_name());
                    shoppingCartInfo.setFood_image(foodInfo.getFood_image());
                    String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
                    if (phone_number.length() == 0) {
                        Toast.makeText(context, "东西跑不了，登录回来再买吧！！！", Toast.LENGTH_SHORT).show();
                        mShareDialog.dismiss();
                        return;
                    }
                    shoppingCartInfo.setSize_name(foodSizeInfo.getSize_name());
                    shoppingCartInfo.setCount(Integer.parseInt(tv_count.getText().toString()));
                    shoppingCartInfo.setPhone_number(phone_number);
                    shoppingCartInfo.setPrice(foodInfo.getPrice());
                    shoppingCartInfo.setIsCommit(0);
                    AddShoppingCartDialog.uploadShoppingCartInfo(context, shoppingCartInfo, tv_food_count);
                    /*mShareDialog.dismiss();*/
                }
            }
        });
        bt_count_down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = Integer.valueOf(tv_count.getText().toString());
                if (count == 1) {
                    Toast.makeText(context, "不能再少了！！！", Toast.LENGTH_SHORT).show();
                } else {
                    count = count - 1;
                    tv_count.setText(String.valueOf(count));
                }
            }
        });
        bt_count_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = new Integer(tv_count.getText().toString());
                int i = integer.intValue();
                if (i < Integer.parseInt(tv_food_count.getText().toString())) {
                    i = i + 1;
                    tv_count.setText(String.valueOf(i));
                } else {
                    Toast.makeText(context, "不能再多了，都被你买走了！！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mShareDialog.show();
    }

    private static void uploadShoppingCartInfo(Context context, ShoppingCartInfo shoppingCartInfo, TextView tv_food_count) {

        int count_old = 0;
        TakeOutFoodOpenHelper takeOutFoodOpenHelper = new TakeOutFoodOpenHelper(context);
        SQLiteDatabase db = takeOutFoodOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor foodSizeInfoCursor = db.query("foodSizeInfo", new String[]{"size_count"}, "food_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getFood_id()), shoppingCartInfo.getSize_name()},
                null, null, null);
        if (foodSizeInfoCursor != null && foodSizeInfoCursor.getCount() > 0) {
            while (foodSizeInfoCursor.moveToNext()) {
                size_count = foodSizeInfoCursor.getInt(0);
            }
        }
        if (foodSizeInfoCursor != null) {
            foodSizeInfoCursor.close();
        }
        Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", null,
                "food_id = ? and size_name = ? and isCommit = ?",
                new String[]{String.valueOf(shoppingCartInfo.getFood_id()), shoppingCartInfo.getSize_name(),String.valueOf(0)},
                null, null, null);
        if (shoppingCartInfoCursor != null && shoppingCartInfoCursor.getCount() > 0) {
            while (shoppingCartInfoCursor.moveToNext()) {
                count_old = shoppingCartInfoCursor.getInt(3);
            }
            contentValues.put("count", shoppingCartInfo.getCount() + count_old);
            if (size_count < shoppingCartInfo.getCount()) {
                Toast.makeText(context, "不能再买了，都被你买走了！！！", Toast.LENGTH_SHORT).show();
            } else {
                int shoppingCartUpdateResult = db.update("shoppingCartInfo", contentValues, "food_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getFood_id()), shoppingCartInfo.getSize_name()});
                if (shoppingCartUpdateResult >= 0) {
                    Toast.makeText(context, "加入购物车成功，去付款吧！！！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "出错了，加入购物车失败", Toast.LENGTH_SHORT).show();
                }
                contentValues = new ContentValues();
                size_count = size_count - shoppingCartInfo.getCount();
                contentValues.put("size_count", size_count);
                db.update("foodSizeInfo", contentValues, "food_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getFood_id()), shoppingCartInfo.getSize_name()});
                tv_food_count.setText(String.valueOf(size_count));
            }

        } else {
            contentValues.put("food_id", shoppingCartInfo.getFood_id());
            contentValues.put("size_name", shoppingCartInfo.getSize_name());
            contentValues.put("count", shoppingCartInfo.getCount());
            contentValues.put("food_name", shoppingCartInfo.getFood_name());
            contentValues.put("phone_number", shoppingCartInfo.getPhone_number());
            contentValues.put("food_image", shoppingCartInfo.getFood_image());
            contentValues.put("price", shoppingCartInfo.getPrice());
            contentValues.put("isCommit",shoppingCartInfo.getIsCommit());
            long shoppingCartInsetResult = db.insert("shoppingCartInfo", null, contentValues);
            if (shoppingCartInsetResult >= 0) {
                Toast.makeText(context, "加入购物车成功，去付款吧！！！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "出错了，加入购物车失败", Toast.LENGTH_SHORT).show();
            }
            contentValues = new ContentValues();
            size_count = size_count - shoppingCartInfo.getCount();
            contentValues.put("size_count", size_count);
            db.update("foodSizeInfo", contentValues, "food_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getFood_id()), shoppingCartInfo.getSize_name()});
            tv_food_count.setText(String.valueOf(size_count));
        }
        if (shoppingCartInfoCursor != null) {
            shoppingCartInfoCursor.close();
        }
        db.close();
    }
}
