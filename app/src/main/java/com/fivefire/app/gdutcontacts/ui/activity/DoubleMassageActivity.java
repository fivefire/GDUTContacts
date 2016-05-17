package com.fivefire.app.gdutcontacts.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.DoubleMassageAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;

import java.util.ArrayList;

public class DoubleMassageActivity extends BaseActivity {
    private ListView DoubleMassage;
    private ArrayList<User> list;
    private Toolbar mtoolbar;
    @Override
    protected void initView() {
        DoubleMassage = (ListView)findViewById(R.id.DoubleMassage);
        mtoolbar =(Toolbar)findViewById(R.id.toolbar);
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {

        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            setTitle(getString(R.string.app_name));
        }

        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        list=(ArrayList<User>)bundle.getSerializable("data");
        DoubleMassageAdapter doubleMassageAdapter = new DoubleMassageAdapter(DoubleMassageActivity.this,list);
        DoubleMassage.setAdapter(doubleMassageAdapter);
        DoubleMassage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = list.get(position);
                Intent intent = new Intent(DoubleMassageActivity.this, UserMassageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User",user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_double_massage;
    }
}
