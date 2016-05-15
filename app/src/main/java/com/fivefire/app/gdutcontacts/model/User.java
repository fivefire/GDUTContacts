package com.fivefire.app.gdutcontacts.model;

import java.io.Serializable;

/**
 * 用户信息类
 * Created by Landy on 2016/5/15.
 */
public class User implements Serializable{
    private String Phone;
    private String Sphone;
    private String Name;
    private String Sno;
    private int Grade;
    private String Password;
    private String Dno;
    private int Tag;
    private String Aname;
    private String Note;

    public int getGrade() {
        return Grade;
    }

    public int getTag() {
        return Tag;
    }

    public String getAname() {
        return Aname;
    }

    public String getDno() {
        return Dno;
    }

    public String getName() {
        return Name;
    }

    public String getNote() {
        return Note;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }

    public String getSno() {
        return Sno;
    }

    public String getSphone() {
        return Sphone;
    }

    public void setAname(String aname) {
        Aname = aname;
    }

    public void setDno(String dno) {
        Dno = dno;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public void setSphone(String sphone) {
        Sphone = sphone;
    }

    public void setTag(int tag) {
        Tag = tag;
    }
}
