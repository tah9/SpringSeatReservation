package com.example.spring_seatreservation.Bean;


public class MyUser {
    long uid;
    String username;
    String password;
    int type;
    int sex;
    int cid;

    public int getType() {
        return type;
    }

    public MyUser setType(int type) {
        this.type = type;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public MyUser setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public int getCid() {
        return cid;
    }

    public MyUser setCid(int cid) {
        this.cid = cid;
        return this;
    }

    public MyUser(long uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public long getUid() {
        return uid;
    }

    public MyUser setUid(long uid) {
        this.uid = uid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MyUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MyUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
