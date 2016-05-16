package com.fivefire.app.gdutcontacts.ui.common;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 *  新建的Activity尽量继承这个类
 * Created by MicroStudent on 2016/4/5.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    public BaseActivity() {
        this.mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentViewId());
        initView();
        setupView(savedInstanceState);
    }
    //配置view
    protected abstract void setupView(Bundle savedInstanceState);
    //初始化view
    protected abstract void initView();
    //设置layoutID
    protected abstract int getContentViewId();
    //显示Toast
    protected void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}