package com.triplebro.domineer.takeoutfood.models;

public class CollectionFoodInfo {

    private int _id;
    private String phone_number;
    private int food_id;

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

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    @Override
    public String toString() {
        return "CollectionFoodInfo{" +
                "_id=" + _id +
                ", phone_number='" + phone_number + '\'' +
                ", food_id=" + food_id +
                '}';
    }
}
