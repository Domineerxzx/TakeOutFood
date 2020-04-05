package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    private int _id;
    private String phone_number;
    private int order_state;

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

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "_id='" + _id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", order_state=" + order_state +
                '}';
    }

}
