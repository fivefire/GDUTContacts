package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.BaseFragment;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseFragment.BaseActivity {
    private Toolbar mToolbar;
    private Button Search;

    @Override
    protected void setupView(Bundle savedInstanceState) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setTitle(getString(R.string.app_name));
        }
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");

        Search = (Button)findViewById(R.id.Search);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
