package com.example.amilah.test.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amilah on 19-Dec-16.
 */

public class Student implements Parcelable{

    int sid;
    public String sname;
    String dob;
    public String address;
    String contact;
    String email;
    int uid;

    public Student(){}

    public Student(String sname, String dob, String address, String contact, String email)
    {
        this.sname = sname;
        this.dob = dob;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }
    public Student(int sid, String sname, String dob, String address, String contact, String email)
    {
        this.sid = sid;
        this.sname = sname;
        this.dob = dob;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.uid = uid;
    }

    public Student(int i, String string, String string1) {
    }


    protected Student(Parcel in) {
        sid = in.readInt();
        sname = in.readString();
        dob = in.readString();
        address = in.readString();
        contact = in.readString();
        email = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDob() {
        return dob;
    }

    public int getUid() {
        return uid;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sid);
        dest.writeString(sname);
        dest.writeString(dob);
        dest.writeString(address);
        dest.writeString(contact);
        dest.writeString(email);
    }
}
