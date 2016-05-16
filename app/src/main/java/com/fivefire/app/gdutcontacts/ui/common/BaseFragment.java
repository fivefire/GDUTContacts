package com.fivefire.app.gdutcontacts.ui.common;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 新建的Fragment尽量继承这个类
 * Created by Bright Van on 2015/8/22/022.
 */
public abstract class BaseFragment extends Fragment {
    protected View mContentView;

    public BaseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
            initViews(mContentView);
            setupViews(savedInstanceState);
        }
        // 缓存的mContentView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个mContentView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null) {
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    public abstract int getLayoutId();

    protected abstract void initViews(View view);

    protected abstract void setupViews(Bundle bundle);

    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     *  新建的Activity尽量继承这个类
     * Created by MicroStudent on 2016/4/5.
     */
    public abstract static class BaseActivity extends AppCompatActivity {
        protected Context mContext;

        public BaseActivity() {
            this.mContext = this;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(getContentViewId());
            initView();
            setupView(savedInstanceState);
        }
        //配置view
        protected abstract void setupView(Bundle savedInstanceState);
        //初始化view
        protected abstract void initView();
        //设置layoutID
        protected abstract int getContentViewId();
        //显示Toast
        protected void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

    }
}
