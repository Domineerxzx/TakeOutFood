package com.triplebro.domineer.takeoutfood.models;

public class CollectionSubmitInfo {

    private int _id;
    private String phone_number;
    private int submit_id;

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

    public int getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(int submit_id) {
        this.submit_id = submit_id;
    }

    @Override
    public String toString() {
        return "CollectionSubmitInfo{" +
                "_id=" + _id +
                ", phone_number='" + phone_number + '\'' +
                ", submit_id=" + submit_id +
                '}';
    }
}

