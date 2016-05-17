package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.utils.DBUtils;

import java.util.ArrayList;
import java.util.Map;


/**
 * 分类页面
 */
public class ClassifyAcativity extends BaseActivity {
    SQLiteDatabase db;
    private String[] Contenx;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    String massage;
    @Override
    protected void setupView(Bundle savedInstanceState) {

        String path = this.getFilesDir().getPath();
        String filename = "temp.db";
        db = SQLiteDatabase.openOrCreateDatabase(path + "/" + filename, null);

        final Intent intent=getIntent();
        massage = intent.getStringExtra("Message");
        Resources resources =getResources();
        if (massage.equals("AName"))
            Contenx=resources.getStringArray(R.array.Academy);
        else if (massage.equals("Grade"))
            Contenx=resources.getStringArray(R.array.Grades);
        arrayAdapter =  new ArrayAdapter<>(ClassifyAcativity.this,R.layout.list,Contenx);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 数据操作
                 */
                ArrayList<User> list = new ArrayList<User>();
                list = DBUtils.SearchUserByANameOrGrade(Contenx[position],massage,db);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",list);
                Intent i = new Intent(ClassifyAcativity.this,DoubleMassageActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
   protected void initView() {
        listView = (ListView)findViewById(R.id.listView);
   }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_classify_acativity;
    }
}
