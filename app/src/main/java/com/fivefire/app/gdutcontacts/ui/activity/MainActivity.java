package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private Button Search;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer,
                R.string.close_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        Search = (Button)findViewById(R.id.Search);
        InsertDatabase();
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setTitle(getString(R.string.app_name));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            mDrawerToggle.syncState();
        }
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    private void InsertDatabase()
    {

        final String path=this.getFilesDir().getPath();
        final String filename="temp.db";
        File dir = new File(path);
        if (!dir.exists())
        {
            if (!dir.mkdir())
            {
                showToast("路径创建失败");
            }
        }
        File file = new File(path+"/"+filename);
        try
        {
            InputStream inputStream = this.getApplication().getResources().openRawResource(R.raw.temp);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[inputStream.available()];
            inputStream.read(buf);
            fos.write(buf);
            fos.flush();
            fos.close();
            inputStream.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        Log.d(TAG, "onOpinionSelected");
        switch (item.getItemId()){
            case R.id.nav_change_password:
                break;
            case R.id.nav_verify:
                Intent intent = new Intent(this, VerifyActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_exit:
                finish();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
