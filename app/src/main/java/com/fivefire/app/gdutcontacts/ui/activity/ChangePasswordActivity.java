package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;
import com.fivefire.app.gdutcontacts.utils.CheckInfo;
import com.fivefire.app.gdutcontacts.utils.DBUtils;
import com.fivefire.app.gdutcontacts.utils.DataOperate;
import com.fivefire.app.gdutcontacts.utils.UserMessage;


public class ChangePasswordActivity extends BaseActivity {

    private ClearEditText edt_old_pass,edt_new_pass,edt_ensure_pass;
    private Button btn_ensure;
    private String old_password;
    private String new_password;
    private String ensure_password;
    private boolean isupdate=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                isupdate=true;
            }
            if(isupdate){
                showToast("密码修改成功");
                SharedPreferences.Editor editor=ChangePasswordActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("pass",new_password);
                editor.commit();
            }
            else{
                showToast("密码修改失败");
            }
        }
    };

    @Override
    protected void initView() {
        edt_old_pass = (ClearEditText)findViewById(R.id.old_password);
        edt_new_pass = (ClearEditText)findViewById(R.id.new_password);
        edt_ensure_pass = (ClearEditText)findViewById(R.id.confirm_password);
        btn_ensure = (Button)findViewById(R.id.btn_confirm);

        btn_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = 1;
                old_password = edt_old_pass.getText().toString();
                new_password = edt_new_pass.getText().toString();
                ensure_password = edt_ensure_pass.getText().toString();

                if(!CheckInfo.confirmPassword(new_password,ensure_password)){
                    showToast("两次输入的新密码不一致");
                    tag = 0;
                }
                if(!CheckInfo.isNetworkAvailable(ChangePasswordActivity.this)){
                    showToast("当前网络不可用");
                    tag = 0;
                }
                Log.d("test",UserMessage.getUserPass(ChangePasswordActivity.this));
                boolean flag=old_password.equals(UserMessage.getUserPass(ChangePasswordActivity.this));
                if(tag==1&&!flag){
                        showToast("旧密码错误");
                        tag = 0;
                }
                else if(tag==1&&flag){

                    DataOperate dataOperate = new DataOperate();
                    User user=new User();
                    user.setPassword(new_password);
                    dataOperate.update(ChangePasswordActivity.this,user,UserMessage.getObject(ChangePasswordActivity.this),handler);
                }

            }

        });
    }

    @Override
    protected void setupView(Bundle savedInstanceState) {
        setupActionBar();
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.change_password);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_password;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
