package edu.school21.cinema.models;

import java.util.Date;

public class Auth {
    private String date;
    private String ip;
    private String user_id;

    public Auth(String date, String ip) {
        this.date = date;
        this.ip = ip;
    }

    public Auth(String date, String ip, String user_id) {
        this.date = date;
        this.ip = ip;
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
