package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.BaseFragment;

import cn.bmob.v3.Bmob;

/**
 * 分类页面
 */
public class ClassifyAcativity extends BaseFragment.BaseActivity {
    private Toolbar mtoolbar;
    private String[] Contenx;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void setupView(Bundle savedInstanceState) {

        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            setTitle(getString(R.string.app_name));
        }

        Intent intent=getIntent();
        String massage = intent.getStringExtra("Message");
        Resources resources =getResources();
        if (massage.equals("AName"))
            Contenx=resources.getStringArray(R.array.Academy);
        else if (massage.equals("Grade"))
            Contenx=resources.getStringArray(R.array.Grades);
        arrayAdapter =  new ArrayAdapter<String>(ClassifyAcativity.this,R.layout.list,Contenx);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 数据操作
                 */
                Intent i = new Intent(ClassifyAcativity.this,UserMassageActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
   protected void initView() {
        mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
        listView = (ListView)findViewById(R.id.listView);

   }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_classify_acativity;
    }
}
