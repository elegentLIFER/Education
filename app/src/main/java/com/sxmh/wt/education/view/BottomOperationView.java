package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BottomOperationView extends FrameLayout {
    @InjectView(R.id.tv_pre_question)
    TextView tvPreQuestion;
    @InjectView(R.id.tv_question_card)
    TextView tvQuestionCard;
    @InjectView(R.id.tv_commit)
    TextView tvCommit;
    @InjectView(R.id.tv_next_question)
    TextView tvNextQuestion;

    private OnOptionClickListener listener;

    public BottomOperationView(@NonNull Context context) {
        super(context);
        initWork();
    }

    public BottomOperationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public BottomOperationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_operation, this, true);
        ButterKnife.inject(this, this);
    }

    @OnClick({R.id.tv_pre_question, R.id.tv_question_card, R.id.tv_commit, R.id.tv_next_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pre_question:
                listener.onPreQuestionClick();
                break;
            case R.id.tv_question_card:
                listener.onQuestionCardClick();
                break;
            case R.id.tv_commit:
                listener.onCommitClick();
                break;
            case R.id.tv_next_question:
                listener.onNextQuestionClick();
                break;
        }
    }

    public interface OnOptionClickListener{
        void onPreQuestionClick();
        void onQuestionCardClick();
        void onCommitClick();
        void onNextQuestionClick();
    }

    public void setOnOptionListener(OnOptionClickListener listener) {
        this.listener = listener;
    }
}
