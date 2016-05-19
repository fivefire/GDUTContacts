package com.fivefire.app.gdutcontacts.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;

public class RegisterActivity extends Activity {
    private ClearEditText phoneEdText,snoEdText,nameEdText,passEdtext1,passEdText2;
    private Button btn_register,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    private void init(){
        phoneEdText = (ClearEditText)findViewById(R.id.register_phone);
        snoEdText = (ClearEditText)findViewById(R.id.register_sno);
        nameEdText = (ClearEditText)findViewById(R.id.register_name);
        passEdtext1 = (ClearEditText)findViewById(R.id.register_password1);
        passEdText2 = (ClearEditText)findViewById(R.id.register_password2);
        btn_register = (Button)findViewById(R.id.register_ensure);
        btn_register = (Button)findViewById(R.id.register_cancel);

        btn_register.setOnClickListener(new RegisterAction());
        btn_cancel.setOnClickListener(new RegisterAction());
    }

    class RegisterAction implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.register_ensure :

                    break;
                case R.id.register_cancel :

                    break;
                default:
                    break;
            }
        }
    }
}
