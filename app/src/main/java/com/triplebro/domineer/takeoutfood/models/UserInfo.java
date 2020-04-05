package com.triplebro.domineer.takeoutfood.models;

public class UserInfo {

    private String phone_number;
    private String password;
    private String nickname;
    private String user_head;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", user_head='" + user_head + '\'' +
                '}';
    }
}
