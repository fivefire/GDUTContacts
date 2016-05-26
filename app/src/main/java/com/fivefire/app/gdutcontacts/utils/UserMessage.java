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
       /*  editor.putString("name",user.getName());
        editor.putString("sno",user.getSno());
        editor.putInt("grade",(Integer) user.getGrade());
        editor.putString("academy",user.getAname());
        editor.putString("dno",user.getDno());
        editor.putString("sphone",user.getSphone());
        editor.putString("note",user.getNote());*/
        editor.commit();
    }

    public static String getUserPhone(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("phone","");
        return pho;
    }

    public static String getUserName(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("name","");
        return pho;
    }
    public static String getUserSno(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("sno","");
        return pho;
    }
    public static String getUserGrade(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("grade","");
        return pho;
    }
    public static String getUserAcademy(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("academy","");
        return pho;
    }
    public static String getUserDno(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("dno","");
        return pho;
    }
    public static String getUserSphone(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("sphone","");
        return pho;
    }
    public static String getUserNote(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("note","");
        return pho;
    }
    public static String getUserPass(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("pass","");
        return pho;
    }
    public static String getObject(Context context){
        SharedPreferences pref=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String pho=pref.getString("id","");
        Log.d("Tag", pho);
        return pho;
    }

}
