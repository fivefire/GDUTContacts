package com.fivefire.app.gdutcontacts.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;
import com.fivefire.app.gdutcontacts.utils.CheckInfo;
import com.fivefire.app.gdutcontacts.utils.DataOperate;

import cn.bmob.v3.Bmob;

public class LoginActivity extends Activity {
    private ClearEditText loginUser,loginPassword;
    private Button btn_signIn,btn_regisetr,btn_fgPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(LoginActivity.this,"58d2bb059cc1244e252cea21b4313d0c");
        init();

    }

    public void init() {
        Drawable d1 = getResources().getDrawable(R.drawable.login_user);
        Drawable d2 = getResources().getDrawable(R.drawable.login_password);
        d1.setBounds(0, 0, 42, 42);
        d2.setBounds(0, 0, 42, 42);
        loginUser = (ClearEditText) findViewById(R.id.login_username);
        loginUser.setCompoundDrawables(d1, null, null, null);
        loginPassword = (ClearEditText) findViewById(R.id.login_password);
        loginPassword.setCompoundDrawables(d2, null, null, null);

        btn_signIn = (Button) findViewById(R.id.signIn);
        btn_signIn.setOnClickListener(new LoginAction());

        btn_regisetr = (Button) findViewById(R.id.login_register);
        btn_regisetr.setOnClickListener(new LoginAction());

        btn_fgPassword = (Button)findViewById(R.id.login_forgot_pass);
        btn_fgPassword.setOnClickListener(new LoginAction());
    }
    class LoginAction implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signIn :
                    String account = loginUser.getText().toString();
                    String password = loginPassword.getText().toString();
                    if (CheckInfo.isNetworkAvailable(LoginActivity.this)){

                        if (!account.equals("")&&!password.equals("")){
                            if (CheckInfo.isMobile(account)){
                                DataOperate dataOperate = new DataOperate();
                                boolean isExist = dataOperate.login(LoginActivity.this,account,password);
                                if(isExist){ //if account and password is right then go to the MainActivity
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this,"账户或密码不正确",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this,"账号不正确",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this,"请填写账号信息",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(LoginActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.login_forgot_pass:
                    Intent intent1 = new Intent();
                    intent1.setClass(LoginActivity.this,FgPasswordActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.login_register:
                    Intent intent2 = new Intent();
                    intent2.setClass(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent2);
                    break;
                default:
                    Toast.makeText(getApplication(),"Error",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onRestart() {
        Log.d("onRestar()","-----------LoginActivity");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("onResume()","-----------LoginActivity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("onPause()","-----------LoginActivity");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("onStop()","-----------LoginActivity");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("onStart()","-----------LoginActivity");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.d("onDestory()","-----------LoginActivity");
        super.onDestroy();
    }
}
