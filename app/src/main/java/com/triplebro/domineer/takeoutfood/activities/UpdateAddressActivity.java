package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.AddressInfo;
import com.triplebro.domineer.takeoutfood.controllers.LocationManager;

public class UpdateAddressActivity extends Activity implements View.OnClickListener{

    private ImageView iv_close_update_address;
    private EditText et_address_name;
    private EditText et_address_area_city;
    private EditText et_address_detailed;
    private EditText et_address_postcode;
    private EditText et_address_telephone;
    private Button bt_save_address;
    private AddressInfo addressInfo;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_update_address.setOnClickListener(this);
        bt_save_address.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        addressInfo = (AddressInfo)intent.getSerializableExtra("AddressInfo");
        et_address_name.setText(addressInfo.getName());
        et_address_area_city.setText(addressInfo.getCity());
        et_address_detailed.setText(addressInfo.getLocation());
        et_address_postcode.setText(String.valueOf(addressInfo.getZip_code()));
        et_address_telephone.setText(addressInfo.getMobile());
        locationManager = new LocationManager(this);
    }

    private void initView() {
        iv_close_update_address = (ImageView) findViewById(R.id.iv_close_update_address);
        et_address_name = (EditText) findViewById(R.id.et_address_name);
        et_address_area_city = (EditText) findViewById(R.id.et_address_area_city);
        et_address_detailed = (EditText) findViewById(R.id.et_address_detailed);
        et_address_postcode = (EditText) findViewById(R.id.et_address_postcode);
        et_address_telephone = (EditText) findViewById(R.id.et_address_telephone);
        bt_save_address = (Button) findViewById(R.id.bt_save_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_update_address:
                finish();
                break;
            case R.id.bt_save_address:
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setPhone_number(getSharedPreferences("userInfo",MODE_PRIVATE).getString("phone_number",""));
                String address_name = et_address_name.getText().toString().trim();
                String city = et_address_area_city.getText().toString().trim();
                String address_detailed = et_address_detailed.getText().toString().trim();
                int zip_code = Integer.parseInt(et_address_postcode.getText().toString());
                String mobile = et_address_telephone.getText().toString().trim();
                if(address_name.length() == 0){
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(city.length() == 0){
                    Toast.makeText(this, "城市不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(address_detailed.length() == 0){
                    Toast.makeText(this, "详细地址信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(zip_code == 0){
                    Toast.makeText(this, "邮编不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobile.length() == 0){
                    Toast.makeText(this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                addressInfo.setName(address_name);
                addressInfo.setCity(city);
                addressInfo.setLocation(address_detailed);
                addressInfo.setZip_code(zip_code);
                addressInfo.setMobile(mobile);
                locationManager.updateAddressInfo(addressInfo);
                finish();
                break;
        }
    }
}
