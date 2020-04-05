package com.triplebro.domineer.takeoutfood.models;

public class TypeGeneralizeInfo {

    private int type_generalize_id;
    private String type_generalize_name;

    public int getType_generalize_id() {
        return type_generalize_id;
    }

    public void setType_generalize_id(int type_generalize_id) {
        this.type_generalize_id = type_generalize_id;
    }

    public String getType_generalize_name() {
        return type_generalize_name;
    }

    public void setType_generalize_name(String type_generalize_name) {
        this.type_generalize_name = type_generalize_name;
    }

    @Override
    public String toString() {
        return "TypeGeneralizeInfo{" +
                "type_generalize_id=" + type_generalize_id +
                ", type_generalize_name='" + type_generalize_name + '\'' +
                '}';
    }
}
