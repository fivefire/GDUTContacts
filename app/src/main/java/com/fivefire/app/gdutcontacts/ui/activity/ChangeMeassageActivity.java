package com.fivefire.app.gdutcontacts.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.model.User;
import com.fivefire.app.gdutcontacts.ui.common.BaseActivity;
import com.fivefire.app.gdutcontacts.ui.common.ClearEditText;
import com.fivefire.app.gdutcontacts.utils.CheckInfo;
import com.fivefire.app.gdutcontacts.utils.DBUtils;
import com.fivefire.app.gdutcontacts.utils.DataOperate;
import com.fivefire.app.gdutcontacts.utils.UserMessage;

public class ChangeMeassageActivity extends BaseActivity {
    private ClearEditText edt_name,edt_sno,edt_dno,edt_pho,edt_spho,edt_note;
    Spinner edt_academy,edt_grade;
    private Button btn_confirm;
    private User user=new User();
    private String name;
    private String sno;
    private String grade;
    private String academy;
    private String dno;
    private String pho;
    private String spho;
    private String note;
   private int length;
   private int index=-1;
    private boolean isupdate=false;
    private Context context=ChangeMeassageActivity.this;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                isupdate=true;
            }
            if(isupdate){
                showToast("个人信息修改成功");
                SharedPreferences.Editor editor=context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                UserMessage.saveUser(user,context);
                editor.commit();
            }
            else{
                showToast("个人信息修改失败");
            }
        }
    };




    @Override
    protected void setupView(Bundle savedInstanceState) {
        setupActionBar();
    }

    @Override
    protected void initView() {
        edt_name = (ClearEditText)findViewById(R.id.edit_Name);
        edt_sno = (ClearEditText)findViewById(R.id.edit_Sno);
        edt_dno = (ClearEditText)findViewById(R.id.edit_Dno);
        edt_pho = (ClearEditText)findViewById(R.id.edit_Phone);
        edt_spho = (ClearEditText)findViewById(R.id.edit_Sphone);
        edt_note = (ClearEditText)findViewById(R.id.edit_Note);
        btn_confirm= (Button) findViewById(R.id.btn_edit_confirm);
        btn_confirm.setOnClickListener(new EditMeassageConfirm());
        edt_name.setText(UserMessage.getUserName(context));
        edt_sno.setText(UserMessage.getUserSno(context));
       edt_dno.setText(UserMessage.getUserDno(context));
        edt_pho.setText(UserMessage.getUserPhone(context));
        edt_spho.setText(UserMessage.getUserSphone(context));
        edt_note.setText(UserMessage.getUserNote(context));
        Spinner grade_Spinner = (Spinner) findViewById(R.id.grade_spinner);
        initGradeSpinner(grade_Spinner);
        Spinner academy_Spinner = (Spinner) findViewById(R.id.academy_spinner);
        initAcademySpinner(academy_Spinner);

    }


    private void initGradeSpinner(Spinner  grade_Spinner){
        final String[] mItems = getResources().getStringArray(R.array.Grades);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade=UserMessage.getUserGrade(context);
        grade_Spinner.setAdapter(adapter);
        length=mItems.length;
        for(int i=0;i<length;i++){
            if(grade.equals(mItems[i])){
                index=i;
            }
        }
        if(index>=0){
            grade_Spinner.setSelection(index,true);
            grade=mItems[index];
        }
        else{
            grade_Spinner.setSelection(0,true);
            grade=mItems[0];
        }
        grade_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                grade=mItems[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    private void initAcademySpinner(Spinner  academy_Spinner){
        final String[] mItems = getResources().getStringArray(R.array.Academy);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        academy=UserMessage.getUserAcademy(context);
        academy_Spinner.setAdapter(adapter);
        length=mItems.length;
        for(int i=0;i<length;i++){
            if(academy.equals(mItems[i])){
                index=i;
            }
        }
        if(index>=0){
            academy_Spinner.setSelection(index,true);
            academy=mItems[index];
        }
        else{
            academy_Spinner.setSelection(0,true);
            academy=mItems[0];
        }
        academy_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                academy=mItems[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    class EditMeassageConfirm implements View.OnClickListener{
       String rmi_name,rmi_sno,rmi_dno,rmi_pho,rmi_spho,rmi_note,rmi_network;
        @Override
        public void onClick(View view) {
            name = edt_name.getText().toString();
            sno = edt_sno.getText().toString();
           dno =edt_dno.getText().toString();
            pho = edt_pho.getText().toString();
            spho = edt_spho.getText().toString();
            note =edt_note.getText().toString();


            int tag = 1;
            if(name.equals("")){
                rmi_name="请正确输入姓名\n";
                tag = 0;
            }
            else{
                rmi_name="";
            }

            if(!CheckInfo.isSno(sno)) {
              rmi_sno="请输入正确学号\n";
                tag = 0;
            }
            else{
                rmi_sno="";
            }

            if(dno.equals("")){
                rmi_dno="请正确输入宿舍号\n";
                tag = 0;
            }
            else{
                rmi_dno="";
            }

            if(!CheckInfo.isMobile(pho)){
                rmi_pho="请正确输入电话号码\n";
                tag = 0;
            }
            else{
                rmi_pho="";
            }

           if(!CheckInfo.isNote(note)){
                rmi_note="备注最多50个字\n";
                tag = 0;
            }
           else{
               rmi_note="";
           }

            if(!CheckInfo.isNetworkAvailable(ChangeMeassageActivity.this)){
                rmi_network="当前网络信息不可用";
                tag = 0;
            }
            else{
                rmi_network="";
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
                    .setMessage("姓名:         "+name+"\n"+  "学号:         "+sno+"\n"+  "年级:         "+grade+"\n"+  "学院:         "+academy+"\n"+  "宿舍号:     "+dno+"\n"+  "手机长号: "+pho+"\n"+  "短号:         "+spho+"\n"+   "备注:         "+note+"\n")
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
            user.setName(name);
            user.setSno(sno);
            user.setGrade(Integer.valueOf(grade));
            user.setAname(academy);
            user.setDno(dno);
            user.setPhone(pho);
            user.setSphone(spho);
            user.setNote(note);
            user.setPassword(UserMessage.getUserPass(context));
            user.setObjectId(UserMessage.getObject(context)+"");
            user.setTag(Integer.valueOf(UserMessage.getTag(context)));
            DataOperate dataOperate = new DataOperate();
            dataOperate.update(context,user,user.getObjectId(),handler);
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
            backResult();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backResult();
        finish();
    }

    private void backResult(){
        Intent intent=new Intent();
        intent.putExtra("phone",UserMessage.getUserPhone(context));
        intent.putExtra("name",UserMessage.getUserName(context));
        setResult(RESULT_OK,intent);


    }
}
