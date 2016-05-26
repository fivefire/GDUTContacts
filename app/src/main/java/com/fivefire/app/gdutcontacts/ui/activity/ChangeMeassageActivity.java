package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ChangeMeassageActivity extends BaseActivity {
    private ClearEditText edt_name,edt_sno,edt_grade,edt_acadeny,edt_dno,edt_pho,edt_spho,edt_note;
    private Button btn_confirm;
    private User user;
    private String name;
    private String sno;
    private String grade;
    private String acadeny;
    private String dno;
    private String pho;
    private String spho;
    private String note;




    @Override
    protected void setupView(Bundle savedInstanceState) {
        setupActionBar();
    }

    @Override
    protected void initView() {
        edt_name = (ClearEditText)findViewById(R.id.edit_Name);
        edt_sno = (ClearEditText)findViewById(R.id.edit_Sno);
        edt_grade = (ClearEditText)findViewById(R.id.edit_Grade);
        edt_acadeny = (ClearEditText)findViewById(R.id.edit_Acadeny);
        edt_dno = (ClearEditText)findViewById(R.id.edit_Dno);
        edt_pho = (ClearEditText)findViewById(R.id.edit_Phone);
        edt_spho = (ClearEditText)findViewById(R.id.edit_Sphone);
        edt_note = (ClearEditText)findViewById(R.id.edit_Note);
        btn_confirm= (Button) findViewById(R.id.btn_edit_confirm);



        btn_confirm.setOnClickListener(new EditMeassageConfirm());


    }
    class EditMeassageConfirm implements View.OnClickListener{
       String rmi_name,rmi_sno,rmi_dno,rmi_pho,rmi_spho,rmi_note,rmi_network;
        @Override
        public void onClick(View view) {
            name = edt_name.getText().toString();
            sno = edt_sno.getText().toString();
            grade = edt_grade.getText().toString();
           acadeny = edt_acadeny.getText().toString();
           dno =edt_dno.getText().toString();
            pho = edt_pho.getText().toString();
            spho = edt_spho.getText().toString();
            note =edt_note.getText().toString();


            int tag = 1;
            if(name.equals("")){
                rmi_name="名字不能为空\n";
                tag = 0;
            }

            if(!CheckInfo.isSno(sno)) {
              rmi_sno="请输入正确学号\n";
                tag = 0;
            }
            if(dno.equals("")){
                rmi_dno="宿舍号不能为空";
                tag = 0;
            }
            if(!CheckInfo.isMobile(pho)){
                rmi_pho="请正确输入电话号码";
                tag = 0;
            }
           if(!CheckInfo.isNote(note)){
                rmi_note="备注最多50个字\n";
                tag = 0;
            }
            if(CheckInfo.isNetworkAvailable(ChangeMeassageActivity.this)){
                rmi_network="当前网络信息不可用";
                tag = 0;
            }
            if(tag==0){
                    new AlertDialog.Builder(ChangeMeassageActivity.this)
                            .setTitle("出错")
                            .setMessage(rmi_name+rmi_sno+rmi_dno+rmi_pho+rmi_note+rmi_network)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定",null)
                            .create()
                            .show();
                }else {
            new AlertDialog.Builder(ChangeMeassageActivity.this)
                    .setTitle("请确认")
                    .setMessage("姓名:"+name+"\n"+  "学号:"+sno+"\n"+  "年级:"+grade+"\n"+  "学院:"+acadeny+"\n"+  "宿舍号:"+dno+"\n"+  "手机长号"+pho+"\n"+  "短号"+spho+"\n"+   "备注"+note+"\n")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定",new EnsureAction())
                    .create()
                    .show();
                }
        }
    }

    class EnsureAction implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String path = ChangeMeassageActivity.this.getFilesDir().getPath();
            String filename = "temp.db";
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path + "/" + filename, null);
            User user= DBUtils.SearchUserByKey(pho,db);
            user.setName(name);
            user.setSno(sno);
            user.setGrade(Integer.valueOf(grade));
            user.setDno(dno);
            user.setSno(sno);
            user.setPhone(pho);
            user.setSphone(spho);
            user.setNote(note);
            DataOperate dataOperate = new DataOperate();
            //dataOperate.update(ChangeMeassageActivity.this,user,user.getObjectId(),);
            Toast.makeText(ChangeMeassageActivity.this,"数据发送中...",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_meassage;
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("编辑个人资料");
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
