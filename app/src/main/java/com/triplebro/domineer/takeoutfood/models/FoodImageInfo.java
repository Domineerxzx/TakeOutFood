package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

/**
 * @author Domineer
 * @data 2019/4/10,5:04
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class FoodImageInfo implements Serializable {

    private int _id;
    private int food_id;
    private String food_image;
    private String phone_number;

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

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "FoodImageInfo{" +
                "_id=" + _id +
                ", food_id=" + food_id +
                ", food_image='" + food_image + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
