package com.fivefire.app.gdutcontacts.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fivefire.app.gdutcontacts.model.User;

import com.fivefire.app.gdutcontacts.model.User;


import java.util.ArrayList;

/**
 * 数据操作类
 * Created by Landy on 2016/5/17.
 */
public class DBUtils {
    public static String[] getAllName(Cursor cursor)
    {
        int i = 0;
        String[] temp = new String[cursor.getCount()];
        while (cursor.moveToNext())
        {
            temp[i]=cursor.getString(2);
            i++;
        }
        return temp;
    }
    public static User SearchUserByKey(String key, SQLiteDatabase db)
    {
        User user=null;
        Cursor SearchUser = db.rawQuery("select * from UserMassage where SPhone like?", new String[]{ key});
        int count = SearchUser.getCount();
        if (count == 0) {
            SearchUser = db.rawQuery("select * from UserMassage where Phone like?", new String[]{ key});
            count = SearchUser.getCount();
        }
        if (count == 0) {
            SearchUser = db.rawQuery("select * from UserMassage where Name like?", new String[]{key});
            count = SearchUser.getCount();
        }
        if (count == 0) {
            return null;
        } else if (count == 1) {
            user=new User();
            while (SearchUser.moveToNext()) {
                user.setPhone(SearchUser.getString(0));
                user.setSphone(SearchUser.getString(1));
                user.setName(SearchUser.getString(2));
                user.setSno(SearchUser.getString(3));
                user.setGrade(SearchUser.getInt(4));
                user.setDno(SearchUser.getString(5));
                user.setAname(SearchUser.getString(6));
            }
        }
        SearchUser.close();
        return user;
    }
    public static ArrayList<User> SearchUserByANameOrGrade(String key,String massage,SQLiteDatabase db)
    {
        ArrayList<User> list = new ArrayList<>();
        Cursor SearchUser;
        if (massage.equals("AName"))
        {
            SearchUser = db.rawQuery("select * from UserMassage where AName like ?",new String[]{key});
        }
        else
        {
            SearchUser = db.rawQuery("select * from UserMassage where Grade like ?",new String[]{key});
        }
        while (SearchUser.moveToNext())
        {
            User user = new User();
            user.setPhone(SearchUser.getString(0));
            user.setSphone(SearchUser.getString(1));
            user.setName(SearchUser.getString(2));
            user.setSno(SearchUser.getString(3));
            user.setGrade(SearchUser.getInt(4));
            user.setDno(SearchUser.getString(5));
            user.setAname(SearchUser.getString(6));
            list.add(user);
        }
        SearchUser.close();
        return list;
    }
    public static void insertAll(SQLiteDatabase db,User user)
    {
        String sql = "insert into "+ "UserMassage" + "(Phone,Sphone,Name,Sno,Grade,Dno,AName,Note) values(?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new String[]{user.getPhone(),user.getSphone(),user.getName(),user.getSno(),user.getGrade()+"",user.getDno(),user.getAname(),user.getNote()});
        //db.close();
    }
    public static void UpdateAll(SQLiteDatabase db,User user)
    {
        User instant;
        instant = SearchUserByKey(user.getName(),db);
        Log.e("User",user.getName());
        //Log.e("Name",instant.getName());
        if (instant==null)
        {
            String sql = "insert into "+ "UserMassage" + "(Phone,Sphone,Name,Sno,Grade,Dno,AName,Note) values(?,?,?,?,?,?,?,?)";
            db.execSQL(sql,new String[]{user.getPhone(),user.getSphone(),user.getName(),user.getSno(),user.getGrade()+"",user.getDno(),user.getAname(),user.getNote()});
        }
    }
}
