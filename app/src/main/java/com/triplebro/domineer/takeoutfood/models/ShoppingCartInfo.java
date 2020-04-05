package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class ShoppingCartInfo implements Serializable {

    private int _id;
    private int food_id;
    private String size_name;
    private int count;
    private String food_name;
    private String phone_number;
    private String food_image;
    private int price;
    private int isCommit;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(int isCommit) {
        this.isCommit = isCommit;
    }

    @Override
    public String toString() {
        return "ShoppingCartInfo{" +
                "_id=" + _id +
                ", food_id=" + food_id +
                ", size_name='" + size_name + '\'' +
                ", count=" + count +
                ", food_name='" + food_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", food_image='" + food_image + '\'' +
                ", price=" + price +
                ", isCommit=" + isCommit +
                '}';
    }
}
