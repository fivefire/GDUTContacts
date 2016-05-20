package com.fivefire.app.gdutcontacts.widget.Dialpad.searchview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.fivefire.app.gdutcontacts.R;

/**
 * Created by MicroStudent on 2016/5/19.
 */
public class DeletableEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private int mClearIconId;
    private Drawable mClearIcon;
    private int mClearIconSize;
    private int mIconLeftX;
    private int mIconRightX;
    private boolean isClearIconVisible = true;
    private boolean isTouch = false;
    private Resources mResources;


    public DeletableEditText(Context context) {
        this(context, null);
    }

    public DeletableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public DeletableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();

        TypedArray typedArray = mResources.obtainAttributes(attrs, R.styleable.DeletableEditText);
        final int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.DeletableEditText_clean_icon:
                    mClearIcon = typedArray.getDrawable(R.styleable.DeletableEditText_clean_icon);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();

        init();
    }

    private void init() {

        //这里的18是调试出来的，估计换设备调试要跪
//        final Bitmap ClearIconBitmap = ImageResizer.decodeSampledBitmapFromResource(mResources, mClearIconId, 18, 18);
//        mClearIcon = new BitmapDrawable(mResources, ClearIconBitmap);

        if (mClearIcon == null) {
            throw new RuntimeException("没有为删除图标设置资源");
        }

        mClearIconSize = Math.max(mClearIcon.getIntrinsicWidth(), mClearIcon.getIntrinsicHeight());

        //默认隐藏clear按钮
        setIsClearIconVisible(false);
        //设置焦点变化的监听器
        setOnFocusChangeListener(this);
        //设置内容变化监听器
        addTextChangedListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //计算icon绘制的位置
        mIconRightX = getWidth()  - getPaddingRight();
        mIconLeftX = mIconRightX - mClearIconSize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isClearIconVisible) {
                    //按下事件，且图标可视，此时应该判断第一次按下的位置是否处图标所在位置
                    isTouch = event.getX() > mIconLeftX && event.getX() < mIconRightX;
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起，且图标可视，此时应该判断位置是否在图标所在位置，若是，再判断isTouch是否为真，为真则清空文本
                if (event.getX() > mIconLeftX && event.getX() < mIconRightX) {
                    if (isTouch) {
                        int length = length();
                        if (length > 0) {
                            getText().delete(length - 1, length);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 在光标处插入文本
     * @param text the text to insert.
     */
    public void insertByCursor(CharSequence text) {
        if (text != null) {
            int start = getSelectionStart();
            getText().insert(start, text);
        }
    }


    public void setIsClearIconVisible(boolean isClearIconVisible) {
        this.isClearIconVisible = isClearIconVisible;
        if (isClearIconVisible) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mClearIcon, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setIsClearIconVisible(getText().length() > 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setIsClearIconVisible(getText().length() > 0);
        } else {
            setIsClearIconVisible(false);
        }
        invalidate();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
