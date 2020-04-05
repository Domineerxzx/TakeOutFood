package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class AddressInfo implements Serializable {

    private int _id;
    private String phone_number;
    private String name;
    private String city;
    private String location;
    private int zip_code;
    private String mobile;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "phone_number='" + phone_number + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", zip_code=" + zip_code +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
