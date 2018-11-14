package com.sxmh.wt.education.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.NUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 右上角新消息指示控件
 */
public class RightTopStatusView2 extends FrameLayout {
    @InjectView(R.id.tv_below)
     TextView tvBelow;
    @InjectView(R.id.iv_top)
     ImageView ivTop;
    @InjectView(R.id.fl_outer)
    FrameLayout flOuter;
    @InjectView(R.id.iv_bottom)
    public ImageView ivBottom;
    @InjectView(R.id.iv_bottom_another)
    ImageView ivBottomAnother;

    public RightTopStatusView2(@NonNull Context context) {
        super(context);
        initWork();
    }

    public RightTopStatusView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
        getAttr(context, attrs);
    }

    public RightTopStatusView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
        getAttr(context, attrs);
    }

    private void initWork() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_right_top_status2, null);
        ButterKnife.inject(this, inflate);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        inflate.setLayoutParams(layoutParams);
        addView(inflate);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RightTopStatusView);

        String text = typedArray.getString(R.styleable.RightTopStatusView_bottom_text);
        if (!TextUtils.isEmpty(text)) {
            tvBelow.setVisibility(VISIBLE);
        }
        tvBelow.setText(text);
        int textColor = typedArray.getColor(R.styleable.RightTopStatusView_bottom_text_color, Color.DKGRAY);
        tvBelow.setTextColor(textColor);
        float textSize = typedArray.getDimension(R.styleable.RightTopStatusView_bottom_text_size, NUtil.sp2px(15));
        tvBelow.setTextSize(NUtil.px2sp(textSize));

        Drawable topDrawable = typedArray.getDrawable(R.styleable.RightTopStatusView_top_img);
        ivTop.setImageDrawable(topDrawable);

        Drawable bottomDrawable = typedArray.getDrawable(R.styleable.RightTopStatusView_bottom_img);
        ivBottom.setImageDrawable(bottomDrawable);

        Drawable bottomAnotherDrawable = typedArray.getDrawable(R.styleable.RightTopStatusView_bottom_img_another);
        ivBottomAnother.setImageDrawable(bottomAnotherDrawable);
    }


    public void toNewStatus(boolean newOrNot) {
        ivTop.setVisibility(newOrNot?VISIBLE:INVISIBLE);
    }

    public void closeNewStatus() {
        ivTop.setVisibility(View.GONE);
    }

    public boolean isInNewStatus() {
        if (ivTop.getVisibility() == VISIBLE) {
            return true;
        }
        return false;
    }

    public void setBottomText(String text) {
        tvBelow.setText(text);
    }

    public void setBottomImg(int resId) {
        ivBottom.setImageResource(resId);
    }

    public void setTopImgRes(int resId) {
        ivTop.setImageResource(resId);
    }
}
