package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class TypeConcreteInfo implements Serializable {

    private int type_concrete_id;
    private int type_generalize_id;
    private String type_concrete_name;
    private String type_concrete_image;

    public int getType_concrete_id() {
        return type_concrete_id;
    }

    public void setType_concrete_id(int type_concrete_id) {
        this.type_concrete_id = type_concrete_id;
    }

    public int getType_generalize_id() {
        return type_generalize_id;
    }

    public void setType_generalize_id(int type_generalize_id) {
        this.type_generalize_id = type_generalize_id;
    }

    public String getType_concrete_name() {
        return type_concrete_name;
    }

    public void setType_concrete_name(String type_concrete_name) {
        this.type_concrete_name = type_concrete_name;
    }

    public String getType_concrete_image() {
        return type_concrete_image;
    }

    public void setType_concrete_image(String type_concrete_image) {
        this.type_concrete_image = type_concrete_image;
    }

    @Override
    public String toString() {
        return "TypeConcreteInfo{" +
                "type_concrete_id=" + type_concrete_id +
                ", type_generalize_id=" + type_generalize_id +
                ", type_concrete_name='" + type_concrete_name + '\'' +
                ", type_concrete_image='" + type_concrete_image + '\'' +
                '}';
    }
}
