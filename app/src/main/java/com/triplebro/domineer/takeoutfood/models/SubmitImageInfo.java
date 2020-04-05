package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class SubmitImageInfo implements Serializable {

    private int submit_id;

    private String submit_image;

    public int getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(int submit_id) {
        this.submit_id = submit_id;
    }

    public String getSubmit_image() {
        return submit_image;
    }

    public void setSubmit_image(String submit_image) {
        this.submit_image = submit_image;
    }

    @Override
    public String toString() {
        return "SubmitImageInfo{" +
                "submit_id=" + submit_id +
                ", submit_image='" + submit_image + '\'' +
                '}';
    }
}
