package com.sxmh.wt.education.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.NUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TitleView extends FrameLayout {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.tv_right)
    TextView tvRight;
    @InjectView(R.id.ll_outer)
    LinearLayout llOuter;

    private OnTitleViewClickListener listener;

    public TitleView(@NonNull Context context) {
        super(context);
        initWork();
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
        getAttr(context, attrs);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        int defaultColor = NUtil.getColor(R.color.colorTextDKGray);
        float defaultSize = NUtil.px2sp(getResources().getDimension(R.dimen.base_text_size));
        // 标题
        String title = typedArray.getString(R.styleable.TitleView_title);
        int color = typedArray.getColor(R.styleable.TitleView_title_color, defaultColor);
        float textsize = typedArray.getDimension(R.styleable.TitleView_title_size, defaultSize);
        tvTitle.setText(title);
        tvTitle.setTextColor(color);
        tvTitle.setTextSize(textsize);
        // 右边文字
        String righttext = typedArray.getString(R.styleable.TitleView_right_text);
        int righttextColor = typedArray.getColor(R.styleable.TitleView_right_text_color, defaultColor);
        float rightTextSize = typedArray.getDimension(R.styleable.TitleView_right_text_size, defaultSize);
        tvRight.setText(righttext);
        tvRight.setTextColor(righttextColor);
        tvRight.setTextSize(rightTextSize);
    }

    private void initWork() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.title, null);
        ButterKnife.inject(this, inflate);
        addView(inflate);
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                listener.onBackClick();
                break;
            case R.id.tv_right:
                listener.onTitleRightClick();
                break;
        }
    }

    public interface OnTitleViewClickListener {
        void onBackClick();

        void onTitleRightClick();
    }

    public void setOnTitleViewClickListener(OnTitleViewClickListener listener) {
        this.listener = listener;
    }

    public void setTvRight(String text) {
        tvRight.setText(text);
    }

    public void setTvTitle(String text) {
        tvTitle.setText(text);
    }

    public void setBackgroundColor(int color) {
        llOuter.setBackgroundColor(color);
    }

    public void setIvBack(int id) {
        ivBack.setImageResource(id);
    }

    public void setTvRightColor(int color) {
        tvRight.setTextColor(color);
    }

    public void setTvTitleColor(int color) {
        tvTitle.setTextColor(color);
    }
}
