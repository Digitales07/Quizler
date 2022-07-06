package com.assorttech.myquizler.Model;

public class Noti {
        private String from, to, message, type;
        private long time;
        private Boolean seen;

    public Noti(String from,String type) {
        this.from = from;
        this.type = type;

    }

    public Noti() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
