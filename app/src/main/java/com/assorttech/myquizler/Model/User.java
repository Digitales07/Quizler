package com.assorttech.myquizler.Model;

public class User {
    private String mUserName;
    private String mImage;

    public User(String mUserName, String mImage) {
        this.mUserName = mUserName;
        this.mImage = mImage;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
