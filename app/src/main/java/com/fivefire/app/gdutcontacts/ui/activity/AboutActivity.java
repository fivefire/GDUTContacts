package com.fivefire.app.gdutcontacts.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void checkUpdate(View view) {
        Toast.makeText(this,"已经是最新版本了！",Toast.LENGTH_SHORT).show();
    }
}
