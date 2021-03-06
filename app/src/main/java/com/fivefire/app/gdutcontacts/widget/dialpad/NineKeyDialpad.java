package com.fivefire.app.gdutcontacts.widget.dialpad;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.fivefire.app.gdutcontacts.R;
import com.fivefire.app.gdutcontacts.adapter.ContactsAdapter;
import com.fivefire.app.gdutcontacts.widget.dialpad.animation.OnAnimationListener;
import com.fivefire.app.gdutcontacts.widget.dialpad.ninekeybutton.INineKeyButton;
import com.fivefire.app.gdutcontacts.widget.dialpad.ninekeybutton.NineKeyButton;
import com.fivefire.app.gdutcontacts.widget.dialpad.query.IQuery;
import com.fivefire.app.gdutcontacts.widget.dialpad.query.NineKeyQuery;
import com.fivefire.app.gdutcontacts.widget.dialpad.searchview.DeletableEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个九键拨号键盘
 * Created by MicroStudent on 2016/5/19.
 */
public class NineKeyDialpad extends FrameLayout implements INineKeyDialpad, View.OnClickListener {

    private static final String TAG = "NineKeyDialpad";
    private List<INineKeyButton> mNineKeyButtons;

    private DeletableEditText mSearchView;

    private ImageButton mSendMsgButton;

    private ImageButton mCallButton;

    private ImageButton mHideButton;

    private TableLayout mContainer;

    private OnQueryTextListener mOnQueryTextListener;

    private RecyclerView mRecyclerView;

    private int mTintColor;

    private ValueAnimator mDropAnimator;

    private OnAnimationListener mOnAnimationListener;

    private boolean isShown = true;//当前是否已经显示

    public NineKeyDialpad(Context context) {
        this(context, null);
    }

    public NineKeyDialpad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineKeyDialpad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_ninekey_dialpad, this, true);
        mNineKeyButtons = new ArrayList<>();

        initView();

        handleAttrs(attrs);

        getAllNineKeyButton();

        setupListener();
    }

    /**
     * 初始化show和hide的动画
     */
    private void initAnimator() {
        mDropAnimator = ValueAnimator.ofInt(getHeight(), 0);
        mDropAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().height = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        mDropAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mDropAnimator.setDuration(500);
        mDropAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationFinish(isShown);
                }
            }
        });
    }

    private void handleAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NineKeyDialpad);
        mTintColor = array.getColor(R.styleable.NineKeyDialpad_tint_color, Color.BLACK);

        Drawable callIcon = array.getDrawable(R.styleable.NineKeyDialpad_call_icon);
        if (callIcon == null) {
            callIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_phone_black_24dp, null);
        }
        mCallButton.setImageDrawable(tintColor(callIcon, Color.WHITE));

        Drawable msgIcon = array.getDrawable(R.styleable.NineKeyDialpad_msg_icon);
        if (msgIcon == null) {
            msgIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_message_black_24dp, null);
        }
        mSendMsgButton.setImageDrawable(tintColor(msgIcon));

        Drawable hideIcon = array.getDrawable(R.styleable.NineKeyDialpad_hide_icon);
        if (hideIcon == null) {
            hideIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_expand_more_black_24dp, null);
        }
        mHideButton.setImageDrawable(tintColor(hideIcon));

        array.recycle();
    }

    private Drawable tintColor(Drawable icon) {
        return tintColor(icon, mTintColor);
    }


    private Drawable tintColor(Drawable icon, int color) {
        Drawable result = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(result, color);
        return result;
    }

    private void initView() {
        mContainer = (TableLayout) findViewById(R.id.container);
        mSearchView = (DeletableEditText) findViewById(R.id.et);
        mSendMsgButton = (ImageButton) findViewById(R.id.bt_send_msg);
        mCallButton = (ImageButton) findViewById(R.id.bt_call);
        mHideButton = (ImageButton) findViewById(R.id.bt_hide);
    }

    private void setupListener() {
        mHideButton.setOnClickListener(this);
        mSendMsgButton.setOnClickListener(this);
        mCallButton.setOnClickListener(this);
        for (INineKeyButton button : mNineKeyButtons) {
            button.setOnClickListener(this);
        }
    }

    private void getAllNineKeyButton() {
        int count = mContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            TableRow row = (TableRow) mContainer.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                View view = row.getChildAt(j);
                if (view instanceof NineKeyButton) {
                    mNineKeyButtons.add((INineKeyButton) view);
                    styleNineKeyButton((Button) view);
                }
            }
        }
    }

    private void styleNineKeyButton(Button button) {
        if (button != null) {
            button.setTextColor(mTintColor);
        }
    }


    public OnQueryTextListener getOnQueryTextListener() {
        return mOnQueryTextListener;
    }

    public void setOnQueryTextListener(OnQueryTextListener mOnQueryTextListener) {
        this.mOnQueryTextListener = mOnQueryTextListener;

        mSearchView.setOnQueryTextListener(mOnQueryTextListener);

        if (mOnQueryTextListener.getQuery() == null) {
            mOnQueryTextListener.setQuery(new NineKeyQuery());
        }
    }

    @Override
    public void show() {
        if (mDropAnimator == null) {
            initAnimator();
        }
        mDropAnimator.reverse();
        isShown = true;
    }

    @Override
    public void hide() {
        if (mDropAnimator == null) {
            initAnimator();
        }
        mDropAnimator.start();
        isShown = false;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;

    }

    @Override
    public void setQuery(IQuery query) {
        if (mOnQueryTextListener != null) {
            mOnQueryTextListener.setQuery(query);
        }
    }

    @Override
    public void setQueryText(String queryText) {
        mSearchView.setText(queryText);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof INineKeyButton) {
            handleNineKeyButtonOnClick((INineKeyButton) v);
            return;
        }
        switch (v.getId()) {
            case R.id.bt_call:
                String uri = "tel:" + mSearchView.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "权限不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                getContext().startActivity(intent);
                break;
            case R.id.bt_send_msg:
                uri = "sms:" + mSearchView.getText().toString();
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                getContext().startActivity(intent);
                break;
            case R.id.bt_hide:
                hide();
                break;
        }
    }

    private void handleNineKeyButtonOnClick(INineKeyButton button) {
        mSearchView.insertByCursor(button.getNumber());
        if (mOnQueryTextListener != null) {
            mOnQueryTextListener.onQueryTextChange(mSearchView.getText().toString());
        }
    }

    public OnAnimationListener getOnAnimationListener() {
        return mOnAnimationListener;
    }

    public void setOnAnimationListener(OnAnimationListener mOnAnimationListener) {
        this.mOnAnimationListener = mOnAnimationListener;
    }
}
