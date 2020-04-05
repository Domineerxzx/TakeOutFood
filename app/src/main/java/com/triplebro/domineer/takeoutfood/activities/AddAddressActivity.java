package com.triplebro.domineer.takeoutfood.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.AddressInfo;
import com.triplebro.domineer.takeoutfood.controllers.LocationManager;

public class AddAddressActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_add_address;
    private LocationManager locationManager;
    private EditText et_address_name;
    private EditText et_address_area_city;
    private EditText et_address_detailed;
    private EditText et_address_postcode;
    private EditText et_address_telephone;
    private Button bt_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_add_address.setOnClickListener(this);
        bt_add_address.setOnClickListener(this);
    }

    private void initData() {
        locationManager = new LocationManager(this);
        /*locationManager.uploadAddressInfo();*/
    }

    private void initView() {
        iv_close_add_address = (ImageView) findViewById(R.id.iv_close_add_address);
        et_address_name = (EditText) findViewById(R.id.et_address_name);
        et_address_area_city = (EditText) findViewById(R.id.et_address_area_city);
        et_address_detailed = (EditText) findViewById(R.id.et_address_detailed);
        et_address_postcode = (EditText) findViewById(R.id.et_address_postcode);
        et_address_telephone = (EditText) findViewById(R.id.et_address_telephone);
        bt_add_address = (Button) findViewById(R.id.bt_add_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_add_address:
                finish();
                break;
            case R.id.bt_add_address:
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
                locationManager.uploadAddressInfo(addressInfo);
                finish();
                break;
        }
    }
}
