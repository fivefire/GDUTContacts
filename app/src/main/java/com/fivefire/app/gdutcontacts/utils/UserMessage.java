package com.fivefire.app.gdutcontacts.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fivefire.app.gdutcontacts.model.User;

/**
 * Created by x on 2016/5/26.
 */



public class UserMessage{

    public static void saveUser(User user, Context context){
        SharedPreferences.Editor editor=context.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
        editor.putString("pass",user.getPassword());
        editor.putString("phone",user.getPhone());
       editor.putString("id",user.getObjectId());
         editor.putString("name",user.getName());
        editor.putString("sno",user.getSno());
        if(user.getGrade()==null){
            editor.putInt("grade",0);
        }else{
            editor.putInt("grade",(user.getGrade()).intValue());
        }
        editor.putString("academy",user.getAname());
        editor.putString("dno",user.getDno());
        editor.putString("sphone",user.getSphone());
        editor.putString("note",user.getNote());
        editor.putInt("tag",(user.getTag()).intValue());
        editor.commit();
    }

    public static String getUserPhone(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("phone","");
    }

    public static String getUserName(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("name","");
    }
    public static String getUserSno(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("sno","");
    }
    public static String getUserGrade(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getInt("grade",0)+"";
    }
    public static String getUserAcademy(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("academy","");
    }
    public static String getUserDno(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("dno","");
    }
    public static String getUserSphone(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("sphone","");
    }
    public static String getUserNote(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("note","");
    }
    public static String getUserPass(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("pass","");
    }
    public static String getObject(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getString("id","");
    }

    public static String getTag(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return pref.getInt("tag",0)+"";
    }


}
