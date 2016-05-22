package com.fivefire.app.gdutcontacts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JasonChou on 2016/5/20.
 * 用于检验注册信息
 */
public class CheckInfo {

    public static boolean isMobile(String s){
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(s);
        if(matcher.matches())
            return true;
        else
            return false;
    }

    public  static boolean isSno(String s){
        Pattern pattern = Pattern.compile("3[0-9]{9}");
        Matcher matcher = pattern.matcher(s);
        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return  false;
    }

    /**
     * 修改密码时，判断两次输入的密码是否一致
     */

    public static boolean confirmPassword(String new_password,String confirm_password){
        if(new_password.equals(confirm_password)){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * 修改密码时，判断旧密码是否正确
     */

    public static boolean confirmOldPassword(String old_password){
        return false;
    }
}
