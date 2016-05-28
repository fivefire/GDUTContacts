package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.ContactsAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.utils.DBUtils;
import com.fivefire.app.gdutcontacts.utils.DataOperate;
import com.fivefire.app.gdutcontacts.widget.dialpad.NineKeyDialpad;
import com.fivefire.app.gdutcontacts.widget.dialpad.OnQueryTextListener;
import com.fivefire.app.gdutcontacts.widget.dialpad.animation.OnAnimationListenerAdapter;
import com.fivefire.app.gdutcontacts.widget.dialpad.query.IQuery;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity implements OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private static final int TYPE_ADMIN = 1;

    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerView;
    private NineKeyDialpad mNineKeyDialpad;
    private IQuery mQuery;//查询器
    private List<User> mUserList;
    private ContactsAdapter mAdapter;//Rv的Adapter
    List<User> list = new ArrayList<>();
    private FloatingActionButton mShowButton;
    private SQLiteDatabase db;

    private User mLoginUser;//当前登录的用户

    private static boolean isAdmin = false;

    final String sql="CREATE TABLE "+"UserMassage"+" (" +
            "Phone  varchar(11) NOT NULL ," +
            "Sphone  varchar(11) NULL ," +
            "Name  varchar(20) NOT NULL ," +
            "Sno  varchar(10) NULL ," +
            "Grade  int NULL ," +
            "Dno  varchar(10) NULL ," +
            "AName  varchar(10) NULL ," +
            "Note  varchar(50) NULL ," +
            "PRIMARY KEY (`Phone`)" +
            ")";

    @SuppressWarnings("unchecked")
    private Handler handler = new Handler(Looper.getMainLooper()){
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
        //先检查Intent
        Intent intent = getIntent();
        mLoginUser = (User) intent.getSerializableExtra("user");
        if (mLoginUser == null) {
            Toast.makeText(this, "非正常启动，请重新登录！", Toast.LENGTH_SHORT).show();
//            finish();
        } else {
            isAdmin = mLoginUser.getTag().intValue() == TYPE_ADMIN;
        }

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
        loadData();

        setupNavigationViewHeader();

        insertDatabase();
    }

    private void setupNavigationViewHeader() {
        if (mLoginUser != null) {
            TextView name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_name);
            name.setText(mLoginUser.getName());
            TextView phone = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_phone);
            phone.setText(mLoginUser.getPhone());
        }
    }

    private void loadData() {
        DataOperate operate = new DataOperate();
        operate.getAllUsers(this, handler);
    }

    private void insertDatabase()
    {
        /*final String path=this.getFilesDir().getPath();
        final String filename="User.db";
        sqLiteUtils = new SQLiteUtils(MainActivity.this);
        File dir = new File(path);
        if (!dir.exists())
        {
            if (!dir.mkdir())
            {
                showToast("路径创建失败");
            }

        }
        db = SQLiteDatabase.openOrCreateDatabase(path+"/"+filename,null);

        db.execSQL(sql);*/
        db= this.openOrCreateDatabase("User.db",MODE_PRIVATE,null);
        try
        {
            db.execSQL(sql);
            Handler handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what==1){
                        list=(List<User>) msg.obj;
                        Log.e("size",list.size()+"");
                        for (int i=0;i<list.size();i++)
                        {
                            DBUtils.insertAll(db,list.get(i));
                        }
                        Log.e("massage","插入完成");
                    }
                }
            };
            DataOperate dataOperate = new DataOperate();
            dataOperate.getAllUsers(this,handler);


            /*InputStream inputStream = this.getApplication().getResources().openRawResource(R.raw.temp);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[inputStream.available()];
            inputStream.read(buf);
            fos.write(buf);
            fos.flush();
            fos.close();
            inputStream.close();*/


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
        mNavigationView.getMenu().findItem(R.id.nav_verify).setVisible(isAdmin);
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
        if (users != null) {
            for (User user : users) {
                Log.d(TAG, user.getName() + user.getPhone() + newText);
            }
            mAdapter.animateTo(users);
        }
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
