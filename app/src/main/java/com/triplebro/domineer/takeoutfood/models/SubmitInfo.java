package com.triplebro.domineer.takeoutfood.models;

import java.io.Serializable;

public class SubmitInfo implements Serializable {

    private int submit_id;
    private String phone_number;
    private String nickname;
    private String user_head;
    private String submit_content;

    public int getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(int submit_id) {
        this.submit_id = submit_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getSubmit_content() {
        return submit_content;
    }

    public void setSubmit_content(String submit_content) {
        this.submit_content = submit_content;
    }

    @Override
    public String toString() {
        return "SubmitInfo{" +
                "submit_id=" + submit_id +
                ", phone_number='" + phone_number + '\'' +
                ", nickname='" + nickname + '\'' +
                ", user_head='" + user_head + '\'' +
                ", submit_content='" + submit_content + '\'' +
                '}';
    }
}
