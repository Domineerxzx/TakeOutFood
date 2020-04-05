package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class FoodInfo implements Serializable {

    private int food_id;

    private String food_name;

    private int price;

    private String food_image;

    private int type_generalize_id;

    private int type_concrete_id;

    private String phone_number;

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public int getType_generalize_id() {
        return type_generalize_id;
    }

    public void setType_generalize_id(int type_generalize_id) {
        this.type_generalize_id = type_generalize_id;
    }

    public int getType_concrete_id() {
        return type_concrete_id;
    }

    public void setType_concrete_id(int type_concrete_id) {
        this.type_concrete_id = type_concrete_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
