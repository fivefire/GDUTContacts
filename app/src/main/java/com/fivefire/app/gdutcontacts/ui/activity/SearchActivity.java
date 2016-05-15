package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.AutoTextAdapter;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseFragment;

import cn.bmob.v3.Bmob;

public class SearchActivity extends BaseFragment.BaseActivity {
    private Toolbar mtoolbar;
    private AutoCompleteTextView SearchKey;
    private Button SearchButton;
    String[] allName ={"张三","张五","李四","李世杰","陈华","陈美","ccsad","gdsfd","csadas"};
    AutoTextAdapter autoTextAdapter;
    User user;
    @Override
    protected void setupView(Bundle savedInstanceState) {
        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            setTitle(getString(R.string.app_name));
        }
        autoTextAdapter = new AutoTextAdapter(allName,SearchActivity.this);
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
                user = new User();
                Intent intent = new Intent(SearchActivity.this,UserMassageActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
        SearchKey = (AutoCompleteTextView)findViewById(R.id.SearchKey);
        SearchButton = (Button)findViewById(R.id.SearchButton);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }
}
