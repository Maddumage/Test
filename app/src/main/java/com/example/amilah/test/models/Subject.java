package com.example.amilah.test.models;

/**
 * Created by amilah on 27-Dec-16.
 */

public class Subject {
    String subName;
    int subID;
    int sid;
    int position;

    @Override
    public String toString() {
        return subName;
    }

    public Subject(int pos, String sName, int sid) {
        this.position = pos;
        this.subName = sName;
        this.sid = sid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Subject(String subName, int subID, int sid) {
        this.subName = subName;
        this.subID = subID;
        this.sid = sid;
    }

    public Subject() {
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getSubID() {
        return subID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
