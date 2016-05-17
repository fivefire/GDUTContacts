package com.fivefire.app.gdutcontacts.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fivefire.app.gdutcontacts.Model.User;

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
        Cursor SearchUser = db.rawQuery("select * from UserMassage where SPhone like?", new String[]{"%" + key + "%"});
        int count = SearchUser.getCount();
        if (count == 0) {
            SearchUser = db.rawQuery("select * from UserMassage where Phone like?", new String[]{"%" + key + "%"});
            count = SearchUser.getCount();
        }
        if (count == 0) {
            SearchUser = db.rawQuery("select * from UserMassage where Name like?", new String[]{"%" + key + "%"});
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
                user.setPassword(SearchUser.getString(5));
                user.setDno(SearchUser.getString(6));
                user.setAname(SearchUser.getString(8));
            }
        }
        SearchUser.close();
        return user;
    }
    public static ArrayList<User> SearchUserByANameOrGrade(String key,String massage,SQLiteDatabase db)
    {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor;
        if (massage.equals("AName"))
        {
            cursor = db.rawQuery("select * from UserMassage where AName like ?",new String[]{key});
        }
        else
        {
            cursor = db.rawQuery("select * from UserMassage where Grade like ?",new String[]{key});
        }
        while (cursor.moveToNext())
        {
            User item = new User();
            item.setPhone(cursor.getString(0));
            item.setSphone(cursor.getString(1));
            item.setName(cursor.getString(2));
            item.setSno(cursor.getString(3));
            item.setGrade(cursor.getInt(4));
            item.setPassword(cursor.getString(5));
            item.setDno(cursor.getString(6));
            item.setAname(cursor.getString(8));
            list.add(item);
        }
        cursor.close();
        return list;
    }

}
