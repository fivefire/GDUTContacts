package com.fivefire.app.gdutcontacts.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;

public class LoginActivity extends Activity {
    private ClearEditText loginUser,loginPassword;
    private Button btn_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    public void init(){
        Drawable d1 = getResources().getDrawable(R.drawable.login_user);
        Drawable d2 = getResources().getDrawable(R.drawable.login_password);
        d1.setBounds(0,0,42,42);
        d2.setBounds(0,0,42,42);
        loginUser = (ClearEditText) findViewById(R.id.login_username);
        loginUser.setCompoundDrawables(d1,null,null,null);
        loginPassword = (ClearEditText)findViewById(R.id.login_password);
        loginPassword.setCompoundDrawables(d2,null,null,null);

        btn_signIn = (Button)findViewById(R.id.signIn);
        btn_signIn.setOnClickListener(new LoginAction());
    }
    class LoginAction implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signIn :
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_forgot_pass:

                    break;
                case R.id.login_register:

                    break;
                case R.id.login_username:

                    break;
                case R.id.login_password:

                    break;
                default:
                    Toast.makeText(getApplication(),"Error",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
