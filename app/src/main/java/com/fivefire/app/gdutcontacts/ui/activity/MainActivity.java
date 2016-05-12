package com.fivefire.app.gdutcontacts.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;

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
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
