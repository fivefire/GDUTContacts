package com.fivefire.app.gdutcontacts.utils;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Created by JasonChou on 2016/5/19.
 * 专门管理Activity的进栈出栈，
 * 当我们想通过按返回键时退多个Activity，则使用该工具，
 * 本类采用单例模式
 */
public class ActivityManager {
    private static ActivityManager instance;
    private Stack<Activity> activityStack;

    private ActivityManager(){

    }

    public static ActivityManager getInstance(){
        if(instance==null){
            instance = new ActivityManager();
        }
        return instance;
    }

    public void pushOneActivity(Activity activity){
        if(activityStack==null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        Log.d("MyActivityManager", "Size"+activityStack.size());
    }

    public Activity getTopActivity(){
        return activityStack.lastElement();
    }

    public void popOneActivity(Activity activity){
        if(activityStack!=null&&activityStack.size()>0){
            if(activity!=null){
                activity.finish();
                activityStack.remove(activity);
                activity=null;
                System.out.println("this activity is finished");
            }
        }
    }

    public void popActivity(int number) {
        if (activityStack != null) {
            while (number > 0) {
                Activity activity = getTopActivity();
                if (activity == null) break;
                popOneActivity(activity);
                number--;
            }
        }
    }
    public void finishAllActivity(){
        if(activityStack!=null){
            while(activityStack.size()>0){
                Activity activity =getTopActivity();
                if(activity==null)break;
                popOneActivity(activity);
            }
        }
    }
}
