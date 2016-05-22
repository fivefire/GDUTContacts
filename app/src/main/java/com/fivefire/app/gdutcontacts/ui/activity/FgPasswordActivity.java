package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;
import com.fivefire.app.gdutcontacts.utils.CheckInfo;

import cn.bmob.v3.Bmob;

public class FgPasswordActivity extends AppCompatActivity {
    private ClearEditText edt_phone,edt_sno,edt_name;
    private Button btn_ensure;
    private String mobile;
    private String sno;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("找回密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bmob.initialize(FgPasswordActivity.this,"58d2bb059cc1244e252cea21b4313d0c");
        init();
    }

    public void init(){

        edt_phone = (ClearEditText)findViewById(R.id.fg_pass_pho);
        edt_sno = (ClearEditText)findViewById(R.id.fg_pass_sno);
        edt_name = (ClearEditText)findViewById(R.id.fg_pass__name);
        btn_ensure = (Button)findViewById(R.id.fg_pass_btn_ensure);

        btn_ensure.setOnClickListener(new FgPassAction());
    }

    class FgPassAction implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            int tag = 1;
            mobile = edt_phone.getText().toString();
            sno = edt_sno.getText().toString();
            name = edt_name.getText().toString();

            if(!CheckInfo.isMobile(mobile)){
                Toast.makeText(FgPasswordActivity.this,"请正确输入手机号",Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if (!CheckInfo.isSno(sno)){
                Toast.makeText(FgPasswordActivity.this,"请正确输入学号",Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if (name.equals("")){
                Toast.makeText(FgPasswordActivity.this,"名字不能为空",Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if(tag == 1){
                if(CheckInfo.isNetworkAvailable(FgPasswordActivity.this)){
                    new AlertDialog.Builder(FgPasswordActivity.this)
                            .setTitle("信息确认")
                            .setMessage("电话：" + mobile + "\n" + "学号：" + sno + "\n" + "姓名：" + name)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定",new FindPassAction())
                            .create()
                            .show();
                }else {
                    Toast.makeText(FgPasswordActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class FindPassAction implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //query the info and if the info is right then return the password to the user
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
