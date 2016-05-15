package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.provider.Contacts;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseFragment;

import cn.bmob.v3.Bmob;

public class UserMassageActivity extends BaseFragment.BaseActivity {
    private Toolbar mtoolbar;
    private Button Call;
    private Button SendMessage;
    private Button Save;
    private TextView Name;
    private TextView Phone;
    private TextView SPhone;
    private TextView Grade;
    private TextView AName;
    private TextView Dno;

    String num;
    User user;
    @Override
    protected void setupView(Bundle savedInstanceState) {
        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            setTitle(getString(R.string.app_name));
        }


        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = Phone.getText().toString();
                String sphone = SPhone.getText().toString();

                if(!phone.equals("")&&sphone.equals(""))
                {
                    Intent i  = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" +phone));
                    startActivity(i);
                }
                else
                {
                    String[] temp={phone,sphone};
                    Choice_Num_Call(v, temp);
                }

            }
        });
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = Phone.getText().toString();
                String sphone = SPhone.getText().toString();
                if(!phone.equals("")&&sphone.equals(""))
                {
                    Intent i  =new Intent();
                    i.setAction(Intent.ACTION_SENDTO);
                    Uri uri= Uri.parse("smsto:"+phone);
                    i.setData(uri);
                    startActivity(i);

                }
                else
                {
                    String[] temp={phone,sphone};
                    Choice_Num_Send(v,temp);
                }
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri insert = Contacts.People.CONTENT_URI;
                Intent i =new Intent(Intent.ACTION_INSERT,insert);
                i.putExtra(Contacts.Intents.Insert.NAME, Name.getText().toString());
                i.putExtra(Contacts.Intents.Insert.PHONE, Phone.getText().toString());
                i.putExtra(Contacts.Intents.Insert.PHONE_TYPE, "长号");
                i.putExtra(Contacts.Intents.Insert.SECONDARY_PHONE, SPhone.getText().toString());
                i.putExtra(Contacts.Intents.Insert.SECONDARY_PHONE_TYPE, "短号");
                i.putExtra(Contacts.Intents.Insert.POSTAL, Dno.getText().toString());
                i.putExtra(Contacts.Intents.Insert.POSTAL_TYPE, "宿舍");
                startActivity(i);
            }
        });
    }

    @Override
    protected void initView() {
        mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        Bmob.initialize(this,"58d2bb059cc1244e252cea21b4313d0c");
        Call = (Button)findViewById(R.id.Call);
        SendMessage = (Button)findViewById(R.id.SendMessage);
        Save = (Button)findViewById(R.id.Save);
        Name = (TextView)findViewById(R.id.Name);
        Phone = (TextView)findViewById(R.id.Phone);
        SPhone = (TextView)findViewById(R.id.SPhone);
        Grade = (TextView)findViewById(R.id.Grade);
        AName = (TextView)findViewById(R.id.AName);
        Dno = (TextView)findViewById(R.id.Dno);



        /*Name.setText(user.getName());
        Phone.setText(user.getPhone());
        SPhone.setText(user.getSphone());
        Grade.setText(user.getGrade());
        AName.setText(user.getAname());
        Dno.setText(user.getDno());
        */

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_massage;
    }
    private void Choice_Num_Call(View v, final String[] temp) {
        // TODO 自动生成的方法存根
        num=temp[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMassageActivity.this);
        builder.setTitle("选择长短号");
        builder.setSingleChoiceItems(temp,1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                num=temp[which];
            }
        });
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i  =new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+num));
                startActivity(i);
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
    private void Choice_Num_Send(View v,final String[] temp)
    {
        num=temp[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMassageActivity.this);
        builder.setTitle("选择长短号");
        builder.setSingleChoiceItems(temp,1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                num=temp[which];
            }
        });
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i  =new Intent();
                i.setAction(Intent.ACTION_SENDTO);
                Uri uri= Uri.parse("smsto:"+num);
                i.setData(uri);
                startActivity(i);
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
