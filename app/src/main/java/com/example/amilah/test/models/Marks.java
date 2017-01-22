package com.example.amilah.test.models;

/**
 * Created by amilah on 29-Dec-16.
 */

public class Marks {
    private int mID;
    private int subID;
    private int sID;
    private String marks;
    private Subject subject;

    public Marks(int mID, int sID, int subID, String marks) {
        this.mID = mID;
        this.sID = sID;
        this.subID = subID;
        this.marks = marks;
    }

    public Marks() {
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public int getSubID() {
        return subID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setSubjectID(Subject subject)
    {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }
}
