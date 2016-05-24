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
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;
import com.fivefire.app.gdutcontacts.utils.CheckInfo;
import com.fivefire.app.gdutcontacts.utils.DataOperate;

import cn.bmob.v3.Bmob;


public class RegisterActivity extends AppCompatActivity {
    private ClearEditText edt_pho,edt_sno,edt_name,edt_password1,edt_password2;
    private Button btn_register;
    private User user;
    private String mobile;
    private String sno;
    private String name;
    private String password1;
    private String password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bmob.initialize(RegisterActivity.this,"58d2bb059cc1244e252cea21b4313d0c");
        init();


    }
    private void init(){
        edt_pho = (ClearEditText)findViewById(R.id.register_pho);
        edt_sno = (ClearEditText)findViewById(R.id.register_sno);
        edt_name = (ClearEditText)findViewById(R.id.register_name);
        edt_password1 = (ClearEditText)findViewById(R.id.register_password1);
        edt_password2 = (ClearEditText)findViewById(R.id.register_password2);
        btn_register = (Button)findViewById(R.id.register_btn_register);

        btn_register.setOnClickListener(new RegisterAction());
    }

    class RegisterAction implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            mobile = edt_pho.getText().toString();
            sno = edt_sno.getText().toString();
            name = edt_name.getText().toString();
            password1 = edt_password1.getText().toString();
            password2 = edt_password2.getText().toString();

            int tag = 1;
            if(!CheckInfo.isMobile(mobile)){
                Toast.makeText(RegisterActivity.this,"请正确输入电话号码",Toast.LENGTH_SHORT).show();
                tag = 0;
            }

            if(!CheckInfo.isSno(sno)) {
                Toast.makeText(RegisterActivity.this, "请输入正确学号", Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if(name.equals("")) {
                Toast.makeText(RegisterActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if(!password1.equals(password2)) {
                Toast.makeText(RegisterActivity.this, "请确保密码一致", Toast.LENGTH_SHORT).show();
                tag = 0;
            }
            if(tag==1)
                if(CheckInfo.isNetworkAvailable(RegisterActivity.this)) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("信息确认")
                            .setMessage("电话：" + mobile + "\n" + "学号：" + sno + "\n" + "姓名：" + name)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定",new EnsureAction())
                            .create()
                            .show();
                }else {
                    Toast.makeText(RegisterActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                }
        }
    }

    class EnsureAction implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            user = new User();
            user.setPhone(mobile);
            user.setSno(sno);
            user.setName(name);
            user.setPassword(password1);
            user.setTag(3);
            DataOperate dataOperate = new DataOperate();
            dataOperate.add(RegisterActivity.this,user);
            Toast.makeText(RegisterActivity.this,"数据发送中...",Toast.LENGTH_SHORT).show();
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
