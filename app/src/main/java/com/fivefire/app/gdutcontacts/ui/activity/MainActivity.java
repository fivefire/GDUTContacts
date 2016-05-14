package com.fivefire.app.gdutcontacts.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    private Toolbar mToolbar;

    @Override
    protected void setupView(Bundle savedInstanceState) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setTitle(getString(R.string.app_name));
        }
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
