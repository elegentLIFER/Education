package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestResultView extends FrameLayout {
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.tv_score)
    TextView tvScore;
    @InjectView(R.id.tv_teacher)
    TextView tvTime;

    public TestResultView(@NonNull Context context) {
        super(context);
        initWork();
    }

    public TestResultView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public TestResultView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_test_result, this, true);
        ButterKnife.inject(this, this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setScore(String score) {
        tvScore.setText(score);
    }

    public void setTime(String time) {
        tvTime.setText(time);
    }
}
