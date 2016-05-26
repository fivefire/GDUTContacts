package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.AutoTextAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.utils.DBUtils;
import com.fivefire.app.gdutcontacts.utils.DataOperate;


import java.util.List;

import cn.bmob.v3.Bmob;

public class SearchActivity extends BaseActivity {

    SQLiteDatabase db;
    private Toolbar mtoolbar;
    private AutoCompleteTextView SearchKey;
    private Button SearchButton, ANameSearch, GradeSearch;
    String[] allName;
    AutoTextAdapter autoTextAdapter;
    User user;
    List<User> userList;
    DataOperate dataOperate;
    @Override
    protected void setupView(Bundle savedInstanceState) {

        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            setTitle(getString(R.string.app_name));
        }

        /*String path = this.getFilesDir().getPath();
        String filename = "User.db";*/

        db = this.openOrCreateDatabase("User.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select * from UserMassage where name like ?", new String[]{"%" + "" + "%"});
        allName = DBUtils.getAllName(cursor);
        dataOperate = new DataOperate();
        autoTextAdapter = new AutoTextAdapter(allName, SearchActivity.this);
        SearchKey.setAdapter(autoTextAdapter);


        SearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SearchKey.setAdapter(autoTextAdapter);
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Searchkey",SearchKey.getText().toString());
                user=DBUtils.SearchUserByKey(SearchKey.getText().toString(),db);
                if (user==null)
                {
                    showToast("未存在该用户");
                }
                else
                {
                    Intent intent = new Intent(SearchActivity.this, UserMassageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User",user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }


            }
        });
        ANameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ClassifyAcativity.class);
                intent.putExtra("Message", "AName");
                startActivity(intent);
            }
        });
        GradeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ClassifyAcativity.class);
                intent.putExtra("Message", "Grade");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this, "58d2bb059cc1244e252cea21b4313d0c");
        SearchKey = (AutoCompleteTextView) findViewById(R.id.SearchKey);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        ANameSearch = (Button) findViewById(R.id.AnameSearch);
        GradeSearch = (Button) findViewById(R.id.GradeSearch);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }


}
