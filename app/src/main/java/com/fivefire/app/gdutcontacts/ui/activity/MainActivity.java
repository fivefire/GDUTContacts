package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.ContactsAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.utils.DataOperate;
import com.fivefire.app.gdutcontacts.widget.dialpad.NineKeyDialpad;
import com.fivefire.app.gdutcontacts.widget.dialpad.OnQueryTextListener;
import com.fivefire.app.gdutcontacts.widget.dialpad.animation.OnAnimationListenerAdapter;
import com.fivefire.app.gdutcontacts.widget.dialpad.query.IQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerView;
    private NineKeyDialpad mNineKeyDialpad;
    private IQuery mQuery;//查询器
    private List<User> mUserList;
    private ContactsAdapter mAdapter;//Rv的Adapter

    private FloatingActionButton mShowButton;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<User> result = (List<User>) msg.obj;
                Log.d(TAG, "receive result, size is " + result.size());
                mAdapter.setData(new ArrayList<>(result));
                mUserList = (List<User>) msg.obj;
            }
        }
    };

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer,
                R.string.close_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mNineKeyDialpad = (NineKeyDialpad) findViewById(R.id.nine_key_dialpad);
        mShowButton = (FloatingActionButton) findViewById(R.id.bt_show);
        insertDatabase();
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
        mAdapter = new ContactsAdapter(this, mNineKeyDialpad);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mNineKeyDialpad.setRecyclerView(mRecyclerView);
        mNineKeyDialpad.setOnQueryTextListener(this);
        mNineKeyDialpad.setOnAnimationListener(new OnAnimationListenerAdapter() {
            @Override
            public void onAnimationFinish(boolean isShown) {
                if (!isShown)
                    mShowButton.show();
            }
        });

        mShowButton.hide();
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNineKeyDialpad.show();
                mShowButton.hide();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        getData();
        mNavigationView.getHeaderView(0).findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChangeMeassageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        DataOperate operate = new DataOperate();
        operate.queryAllVerified(this, handler);
    }

    private void insertDatabase()
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        Log.d(TAG, "onOpinionSelected");
        switch (item.getItemId()){
            case R.id.nav_change_password:
                intent=new Intent(this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_verify:
                intent = new Intent(this, VerifyActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_exit:
                finish();
                break;
            case R.id.action_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_classified_query:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
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

    @Override
    @SuppressWarnings("unchecked")
    public void onQueryTextChange(String newText) {
        List<User> users = (List<User>) mQuery.filter(mUserList, newText);
        for (User user : users) {
            Log.d(TAG, user.getName() + user.getPhone() + newText);
        }
        mAdapter.animateTo(users);
    }

    @Override
    public void setQuery(IQuery query) {
        mQuery = query;
    }

    @Override
    public IQuery getQuery() {
        return mQuery;
    }

}
