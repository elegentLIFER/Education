package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionCardView extends LinearLayout {
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    public QuestionCardView(Context context) {
        super(context);
        initWork();
    }

    public QuestionCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public QuestionCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_question_card, this, true);
        ButterKnife.inject(this, this);
    }

    public RecyclerView getRvContent() {
        return rvContent;
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }
}
