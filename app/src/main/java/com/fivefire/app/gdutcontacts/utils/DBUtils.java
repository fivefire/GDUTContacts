package com.fivefire.app.gdutcontacts.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fivefire.app.gdutcontacts.model.User;


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
}
