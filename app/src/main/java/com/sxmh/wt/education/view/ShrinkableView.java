package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ShrinkableView extends FrameLayout {
    @InjectView(R.id.tv_text)
    TextView tvText;
    @InjectView(R.id.rv_list)
    RecyclerView rvList;
    @InjectView(R.id.iv_right)
    ImageView ivRight;
    @InjectView(R.id.ll_outer)
    LinearLayout llOuter;

    public ShrinkableView(@NonNull Context context) {
        super(context);
        initWork();
    }

    public ShrinkableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public ShrinkableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_shrinkable, null);
        ButterKnife.inject(this, inflate);
        addView(inflate);
    }

    public void setTitleText(String text) {
        tvText.setText(text);
    }

    public RecyclerView getRv() {
        return rvList;
    }

    @OnClick(R.id.ll_outer)
    public void onViewClicked() {
        boolean visible = rvList.getVisibility() == VISIBLE;
        rvList.setVisibility(visible ? GONE : VISIBLE);
        ivRight.setImageResource(visible ? R.drawable.more_ : R.drawable.xiala);
    }

    public void setOpen(boolean openOrNot) {
        rvList.setVisibility(openOrNot ? VISIBLE : GONE);
    }
}
