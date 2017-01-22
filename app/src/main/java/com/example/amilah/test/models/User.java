package com.example.amilah.test.models;

/**
 * Created by amilah on 20-Dec-16.
 */

public class User {

    int uid ;
    String uname ;
    String pword ;
    String img;

    public User(){}

    public User(int uid, String uname, String pword,String img)
    {
        this.uid = uid;
        this.uname = uname;
        this.pword = pword;
        this.img = img;
    }

    public User(int uid, String uname, String pword)
    {
        this.uid = uid;
        this.uname = uname;
        this.pword = pword;
    }
    public User(String uName, String pWord) {
        this.uname = uName;
        this.pword = pWord;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public String getPword() {
        return pword;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }
}
