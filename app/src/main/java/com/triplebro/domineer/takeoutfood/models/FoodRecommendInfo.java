package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class FoodRecommendInfo implements Serializable {

    private int food_id;
    private String food_name;
    private int price;
    private String recommend_image;

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

    public String getRecommend_image() {
        return recommend_image;
    }

    public void setRecommend_image(String recommend_image) {
        this.recommend_image = recommend_image;
    }

    @Override
    public String toString() {
        return "FoodRecommendInfo{" +
                "food_id=" + food_id +
                ", food_name='" + food_name + '\'' +
                ", price=" + price +
                ", recommend_image='" + recommend_image + '\'' +
                '}';
    }
}
