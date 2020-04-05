package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.adapters.SizeInfoAdapter;
import com.triplebro.domineer.takeoutfood.adapters.SubmitAdapter;
import com.triplebro.domineer.takeoutfood.controllers.ChangeFoodManager;
import com.triplebro.domineer.takeoutfood.handlers.OssHandler;
import com.triplebro.domineer.takeoutfood.interfaces.OnItemClickListener;
import com.triplebro.domineer.takeoutfood.models.FoodInfo;
import com.triplebro.domineer.takeoutfood.models.FoodSizeInfo;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;
import com.triplebro.domineer.takeoutfood.properties.ProjectProperties;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.ChooseUserHeadDialogUtil;
import com.triplebro.domineer.takeoutfood.utils.dialogUtils.SingleChooseDialog;
import com.triplebro.domineer.takeoutfood.utils.imageUtils.RealPathFromUriUtils;
import com.triplebro.domineer.takeoutfood.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

public class ChangeFoodInfoActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private ImageView iv_close_change_food;
    private EditText et_food_name;
    private EditText et_food_price;
    private TextView tv_food_generalize_type_content;
    private TextView tv_food_concrete_type_content;
    private ImageView iv_food_generalize_type;
    private ImageView iv_food_concrete_type;
    private RecyclerView rv_food_image_content;
    private ImageView iv_food_image_show;
    private ImageView iv_delete_food_image_show;
    private ListView lv_food_size;
    private ImageView iv_add_food_size;
    private ImageView iv_delete_food_size;
    private Button bt_change_food;
    private long timeStamp;
    private String chooseFrom;
    private SharedPreferences adminInfo;
    private String phone_number;
    private ChangeFoodManager changeFoodManager;
    private String image_show;
    private SubmitAdapter submitAdapter;
    private TypeGeneralizeInfo chooseTypeGeneralizeInfo;
    private TypeConcreteInfo chooseTypeConcreteInfo;
    private List<FoodSizeInfo> foodSizeInfoList;
    private SizeInfoAdapter sizeInfoAdapter;
    private FoodInfo foodInfo;
    private TypeGeneralizeInfo foodGeneralizeTypeInfo;
    private TypeConcreteInfo foodConcreteTypeInfo;
    private List<String> submitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_food_info);
        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_change_food = findViewById(R.id.iv_close_change_food);
        et_food_name = findViewById(R.id.et_food_name);
        et_food_price = findViewById(R.id.et_food_price);
        tv_food_generalize_type_content = findViewById(R.id.tv_food_generalize_type_content);
        tv_food_concrete_type_content = findViewById(R.id.tv_food_concrete_type_content);
        iv_food_generalize_type = findViewById(R.id.iv_food_generalize_type);
        iv_food_concrete_type = findViewById(R.id.iv_food_concrete_type);
        rv_food_image_content = findViewById(R.id.rv_food_image_content);
        iv_food_image_show = findViewById(R.id.iv_food_image_show);
        iv_delete_food_image_show = findViewById(R.id.iv_delete_food_image_show);
        iv_delete_food_image_show.bringToFront();
        iv_food_image_show.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lv_food_size = findViewById(R.id.lv_food_size);
        iv_add_food_size = findViewById(R.id.iv_add_food_size);
        iv_delete_food_size = findViewById(R.id.iv_delete_food_size);
        bt_change_food = findViewById(R.id.bt_change_food);
    }

    private void initData() {
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        Intent intent = getIntent();
        foodInfo = (FoodInfo) intent.getSerializableExtra("foodInfo");
        changeFoodManager = new ChangeFoodManager(this);
        File file = new File(foodInfo.getFood_image());
        if (file.length()>0) {
            Glide.with(this).load(file).into(iv_food_image_show);
        }else{
            OssHandler ossHandler = new OssHandler(this, iv_food_image_show);
            DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+foodInfo.getFood_image());
        }
        image_show = foodInfo.getFood_image();
        et_food_name.setText(foodInfo.getFood_name());
        et_food_price.setText(String.valueOf(foodInfo.getPrice()));
        rv_food_image_content.setLayoutManager(new GridLayoutManager(this, 3));
        foodGeneralizeTypeInfo = changeFoodManager.getFoodGeneralizeTypeInfo(foodInfo.getType_generalize_id());
        foodConcreteTypeInfo = changeFoodManager.getFoodConcreteTypeInfo(foodInfo.getType_concrete_id());
        if (foodGeneralizeTypeInfo != null) {
            tv_food_generalize_type_content.setText(foodGeneralizeTypeInfo.getType_generalize_name());
        }
        if (foodConcreteTypeInfo != null) {
            tv_food_concrete_type_content.setText(foodConcreteTypeInfo.getType_concrete_name());
        }
        submitList = changeFoodManager.getFoodImageList(foodInfo.getFood_id());
        submitList.add("");
        submitAdapter = new SubmitAdapter(this, submitList);
        rv_food_image_content.setAdapter(submitAdapter);
        chooseFrom = "image_show";
        chooseTypeGeneralizeInfo = foodGeneralizeTypeInfo;
        chooseTypeConcreteInfo = foodConcreteTypeInfo;
        foodSizeInfoList = changeFoodManager.getFoodSizeInfoList(foodInfo.getFood_id());
        sizeInfoAdapter = new SizeInfoAdapter(this, foodSizeInfoList);
        lv_food_size.setAdapter(sizeInfoAdapter);
    }

    private void setOnClickListener() {
        iv_close_change_food.setOnClickListener(this);
        tv_food_generalize_type_content.setOnClickListener(this);
        tv_food_concrete_type_content.setOnClickListener(this);
        iv_food_generalize_type.setOnClickListener(this);
        iv_food_concrete_type.setOnClickListener(this);
        iv_add_food_size.setOnClickListener(this);
        iv_delete_food_size.setOnClickListener(this);
        iv_food_image_show.setOnClickListener(this);
        iv_delete_food_image_show.setOnClickListener(this);
        bt_change_food.setOnClickListener(this);
        submitAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_change_food:
                finish();
                break;
            case R.id.tv_food_generalize_type_content:
            case R.id.iv_food_generalize_type:
                final List<TypeGeneralizeInfo> foodGeneralizeType = changeFoodManager.getFoodGeneralizeType();
                final String[] chooseGeneralize = new String[foodGeneralizeType.size()];
                int i = 0;
                for (TypeGeneralizeInfo typeGeneralizeInfo : foodGeneralizeType) {
                    chooseGeneralize[i] = typeGeneralizeInfo.getType_generalize_name();
                    i++;
                }
                SingleChooseDialog chooseGeneralizeDialog = new SingleChooseDialog();
                chooseGeneralizeDialog.show("请选择概括类别", chooseGeneralize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_food_generalize_type_content.setText(chooseGeneralize[which]);
                        chooseTypeGeneralizeInfo = foodGeneralizeType.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.tv_food_concrete_type_content:
            case R.id.iv_food_concrete_type:
                if (chooseTypeGeneralizeInfo.getType_generalize_id() == 0) {
                    Toast.makeText(this, "还没有选择概括类别呢", Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<TypeConcreteInfo> foodConcreteType = changeFoodManager.getFoodConcreteType(chooseTypeGeneralizeInfo.getType_generalize_id());
                if (foodConcreteType.size() == 0) {
                    Toast.makeText(this, "目前该概括类别尚无详细类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] chooseConcrete = new String[foodConcreteType.size()];
                int j = 0;
                for (TypeConcreteInfo typeConcreteInfo : foodConcreteType) {
                    chooseConcrete[j] = typeConcreteInfo.getType_concrete_name();
                    j++;
                }
                SingleChooseDialog chooseConcreteDialog = new SingleChooseDialog();
                chooseConcreteDialog.show("请选择详细类别", chooseConcrete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_food_concrete_type_content.setText(chooseConcrete[which]);
                        chooseTypeConcreteInfo = foodConcreteType.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.iv_add_food_size:
                FoodSizeInfo foodSizeInfo = new FoodSizeInfo();
                foodSizeInfoList.add(foodSizeInfo);
                sizeInfoAdapter.setFoodSizeInfoList(foodSizeInfoList);
                break;
            case R.id.iv_delete_food_size:
                foodSizeInfoList = sizeInfoAdapter.getFoodSizeInfoList();
                foodSizeInfoList.remove(foodSizeInfoList.size() - 1);
                sizeInfoAdapter.setFoodSizeInfoList(foodSizeInfoList);
                break;
            case R.id.iv_food_image_show:
                adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
                phone_number = adminInfo.getString("phone_number", "");
                timeStamp = System.currentTimeMillis();
                ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, phone_number, timeStamp);
                chooseFrom = "image_show";
                break;
            case R.id.iv_delete_food_image_show:
                Glide.with(this).load(R.drawable.submit).into(iv_food_image_show);
                image_show = "";
                iv_delete_food_image_show.setVisibility(View.GONE);
                break;
            case R.id.bt_change_food:
                String food_name = et_food_name.getText().toString().trim();
                String food_price = et_food_price.getText().toString().trim();
                int type_generalize_id = chooseTypeGeneralizeInfo.getType_generalize_id();
                int type_concrete_id = chooseTypeConcreteInfo.getType_concrete_id();
                if(food_name.length() == 0){
                    Toast.makeText(this, "商品名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(food_price.length() == 0){
                    Toast.makeText(this, "商品价格不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(type_generalize_id == 0){
                    Toast.makeText(this, "请选择概括类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(type_concrete_id == 0){
                    Toast.makeText(this, "请选择详细类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(image_show.length() == 0){
                    Toast.makeText(this, "请选择商品展示图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeFoodManager.deleteFood(foodInfo.getFood_id());
                ContentValues foodInfo = new ContentValues();
                foodInfo.put("food_name", food_name);
                foodInfo.put("type_generalize_id", type_generalize_id);
                foodInfo.put("type_concrete_id", type_concrete_id);
                foodInfo.put("price", Integer.parseInt(food_price));
                foodInfo.put("food_image", image_show);
                foodInfo.put("phone_number", phone_number);
                int food_id = changeFoodManager.addFoodInfo(foodInfo);
                if (food_id != -1) {
                    foodSizeInfoList = sizeInfoAdapter.getFoodSizeInfoList();
                    changeFoodManager.addFoodSizeInfo(food_id, foodSizeInfoList, phone_number);
                    List<String> foodImageList = submitAdapter.getData();
                    changeFoodManager.addFoodImageInfo(food_id, foodImageList, phone_number);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        timeStamp = System.currentTimeMillis();
        ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, phone_number, timeStamp);
        chooseFrom = "image_content";
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isCheck = true;
        String s = "";
        switch (requestCode) {
            case ProjectProperties.FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    s = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                } else {
                    isCheck = false;
                }
                break;
            case ProjectProperties.FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    s = getFilesDir() + File.separator + "images" + File.separator + phone_number + timeStamp + ".jpg";
                } else {
                    isCheck = false;
                }
                break;
            default:
                break;
        }
        if (isCheck) {
            if (chooseFrom.equals("image_content")) {
                List<String> strings = submitAdapter.getData();
                if (strings.size() == 0) {
                    strings.add(s);
                } else {
                    strings.remove(strings.size() - 1);
                    strings.add(s);
                }
                if (strings.size() != 9) {
                    strings.add("");
                }
                submitAdapter.setData(strings);
            } else {
                Glide.with(this).load(s).into(iv_food_image_show);
                image_show = s;
                iv_delete_food_image_show.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
