package com.fivefire.app.gdutcontacts.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by djd14 on 2016/5/18.
 */
public class DataOperate {
    String SUCCESS="succeess";
    String ERROR="error";
    private User user;

    public  void  add(final Context context,User user){
        if (user!=null){
            user.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context,"注册成功，请等候管理员确认!",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                     Toast.makeText(context,"注册失败，用户已经存在:",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void update( final Context context,User user ,String objectid){
        user.update(context, objectid, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"update user data successfully!",Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context,"update user data fail!",Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public List<User> querycontains(final Context context,String key,String value){//模糊搜索
        final List<User>[] userlist = new List[1];
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereContains(key, value);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userlist[0] = list;

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "query fail:" + s, Toast.LENGTH_SHORT).show();
            }
        });
        return userlist[0];
    }

    public User queryabsolutly(final Context context,String objectid){
        final User[] user = new User[1];
        BmobQuery<User> query=new BmobQuery<>();
        query.getObject(context, objectid, new GetListener<User>() {
            @Override
            public void onSuccess(User muser) {
                user[0] = muser;
                Toast.makeText(context, "query user successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "query user fail:" + s, Toast.LENGTH_SHORT).show();
            }
        });
        return user[0];
    }

   public void delete(final Context context,String objectid){
       User user=new User();
       user.setObjectId(objectid);
       user.delete(context, new DeleteListener() {
           @Override
           public void onSuccess() {
               Toast.makeText(context, "delete successfully", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(int i, String s) {
               Toast.makeText(context, "delete fail:" + s, Toast.LENGTH_SHORT).show();
           }
       });
   }

    public boolean login(final Context context,String phone,String password){
         final   boolean [] isexit=new boolean[1];
        BmobQuery<com.fivefire.app.gdutcontacts.model.User> queryphone=new BmobQuery<>();
        queryphone.addWhereEqualTo("Phone",phone);
        BmobQuery<User> querypassword=new BmobQuery<>();
        querypassword.addWhereEqualTo("Password", password);
        BmobQuery<User> querytag=new BmobQuery<>();
        querytag.addWhereNotEqualTo("Tag", 3);
        List<BmobQuery<User>> list=new ArrayList<>();
        list.add(querypassword);
        list.add(queryphone);
        list.add(querytag);
        BmobQuery<User> querylist=new BmobQuery<>();
        querylist.and(list);
        Log.d("66666666","666666666");
        querylist.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                Log.d("66666666","999999999");
                isexit[0]=true;
                Log.d("66666666","999999999"+isexit[0]);

            }

            @Override
            public void onError(int i, String s) {
                Log.d("66666666","22222222222222");
               // isexit[0]=false;
                Toast.makeText(context,"error code:"+i+"message:"+s,Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("66666666","555555555"+isexit[0]);
        return  isexit[0]==false;
    }
    public User quenryEqual(Context context,String key,String value){
      //  final User user;
        Bmob.initialize(context,"58d2bb059cc1244e252cea21b4313d0c");
        Log.d("66666666666",""+key+value);
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo(key,value);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                 user = new User();
                user =list.get(0);
                Log.d("666666666",user.getPassword());
            }

            @Override
            public void onError(int i, String s) {
                Log.d("6666666666",""+i+"   6666   "+s);
            }
        });
        if(user!=null)
        Log.d("6666666666666",user.getPhone());
        else {
            Log.d("66666","666666");
        }
        return user;
    }

}