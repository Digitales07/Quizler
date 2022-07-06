package com.assorttech.myquizler.Model;

public class UserDataModel {

    String user_display_name, user_name, user_phone_no, user_gender,user_image,user_city,user_favourite_subject,user_nick_name;

    public UserDataModel() {

    }

    public UserDataModel(String user_display_name, String user_name, String user_phone_no, String user_gender, String user_image,String user_city,String user_favourite_subject,String user_nick_name) {
        this.user_display_name = user_display_name;
        this.user_name = user_name;
        this.user_phone_no = user_phone_no;
        this.user_gender = user_gender;
        this.user_image = user_image;
        this.user_city=user_city;
        this.user_favourite_subject=user_favourite_subject;
        this.user_nick_name=user_nick_name;
    }
    public UserDataModel(String user_name, String user_image){
        this.user_name = user_name;
        this.user_image = user_image;

    }
    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_favourite_subject() {
        return user_favourite_subject;
    }

    public void setUser_favourite_subject(String user_favourite_subject) {
        this.user_favourite_subject = user_favourite_subject;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_display_name() {
        return user_display_name;
    }

    public void setUser_display_name(String user_display_name) {
        this.user_display_name = user_display_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone_no() {
        return user_phone_no;
    }

    public void setUser_phone_no(String user_phone_no) {
        this.user_phone_no = user_phone_no;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
}
