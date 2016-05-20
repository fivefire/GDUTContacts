package com.fivefire.app.gdutcontacts.utils;

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
}
