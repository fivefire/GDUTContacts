package com.fivefire.app.gdutcontacts.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.VerifyAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.utils.DataOperate;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class VerifyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "VerifyActivity";
    private RecyclerView mRecyclerView;

    private VerifyAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        Bmob.initialize(this, "58d2bb059cc1244e252cea21b4313d0c");
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        setupActionBar();
        mAdapter = new VerifyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setOnRefreshListener(this);
        getData();
    }

    private void getData() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("Tag", 3);
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                mAdapter.setData(list);
                Log.d(TAG, "" + list.size());
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(VerifyActivity.this, "获取失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.verify);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_verify;
    }
}
