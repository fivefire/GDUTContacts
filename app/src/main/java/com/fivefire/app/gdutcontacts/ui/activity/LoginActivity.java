package com.fivefire.app.gdutcontacts.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;

public class LoginActivity extends Activity {
    private ClearEditText loginUser,loginPassword;
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
    }

}
