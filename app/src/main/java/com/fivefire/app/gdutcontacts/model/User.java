package com.fivefire.app.gdutcontacts.model;

import com.fivefire.app.gdutcontacts.widget.dialpad.query.IUser;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by djd14 on 2016/5/12.
 */
public class User extends BmobObject implements Serializable, IUser {
    String Phone;//手机
    String Sphone;//短号
    String Name;//姓名
    String Sno;//序号
    Number Grade;//年级
    String Password;//密码
    String Dno;//宿舍号
    Number Tag;//1 is admin 2 is common user whitch has velify  ,3 is not;
    String Aname;//学院名
    String Note;//备注

    public User(String name, String phone) {
        Phone = phone;
        Name = name;
    }

    public User() {

    }

    public User(String tableName, String note, String aname, String dno, Number tag, String password, Number grade, String sno, String name, String sphone, String phone) {
        super(tableName);//the  user class ,the same the table
        this.setTableName("User");
        Note = note;
        Aname = aname;
        Dno = dno;
        Tag = tag;
        Password = password;
        Grade = grade;
        Sno = sno;
        Name = name;
        Sphone = sphone;
        Phone = phone;
    }

    public User(String note, String aname, String dno, Number tag, String password, Number grade, String sno, String name, String sphone, String phone) {
        this.setTableName("User");
        Note = note;
        Aname = aname;
        Dno = dno;
        Tag = tag;
        Password = password;
        Grade = grade;
        Sno = sno;
        Name = name;
        Sphone = sphone;
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getAname() {
        return Aname;
    }

    public void setAname(String aname) {
        Aname = aname;
    }

    public Number getTag() {
        return Tag;
    }

    public void setTag(Number tag) {
        Tag = tag;
    }

    public String getDno() {
        return Dno;
    }

    public void setDno(String dno) {
        Dno = dno;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Number getGrade() {
        return Grade;
    }

    public void setGrade(Number grade) {
        Grade = grade;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSphone() {
        return Sphone;
    }

    public void setSphone(String sphone) {
        Sphone = sphone;
    }
}