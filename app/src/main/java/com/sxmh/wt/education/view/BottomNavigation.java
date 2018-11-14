package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.NUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BottomNavigation extends FrameLayout implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "BottomNavigation";
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.rb_home_page)
    RadioButton rbHomePage;
    @InjectView(R.id.rb_set)
    RadioButton rbSet;
    @InjectView(R.id.rb_category)
    RadioButton rbCategory;
    @InjectView(R.id.rb_my)
    RadioButton rbMy;

    private OnBottomNaviClickListener listener;

    public BottomNavigation(@NonNull Context context) {
        super(context);
        initWork();
    }

    public BottomNavigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public BottomNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.bottom_navigation, null);
        ButterKnife.inject(this, inflate);
        rgGroup.setOnCheckedChangeListener(this);
        rbHomePage.setOnTouchListener((View v, MotionEvent event)->{
                if (rbHomePage.isChecked()){
                    listener.onHomeClickAgain();
                    return true;
                }
                return false;
        });
        rbHomePage.setTextColor(NUtil.getColor(R.color.colorMainBlue));
        addView(inflate);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rbHomePage.setTextColor(NUtil.getColor(R.color.colorTextDKGray));
        rbCategory.setTextColor(NUtil.getColor(R.color.colorTextDKGray));
        rbSet.setTextColor(NUtil.getColor(R.color.colorTextDKGray));
        rbMy.setTextColor(NUtil.getColor(R.color.colorTextDKGray));

        switch (checkedId) {
            case R.id.rb_home_page:
                listener.onHomePageClick();
                rbHomePage.setTextColor(NUtil.getColor(R.color.colorMainBlue));
                break;
            case R.id.rb_set:
                listener.onSetClick();
                rbSet.setTextColor(NUtil.getColor(R.color.colorMainBlue));
                break;
            case R.id.rb_category:
                listener.onCategoryClick();
                rbCategory.setTextColor(NUtil.getColor(R.color.colorMainBlue));
                break;
            case R.id.rb_my:
                listener.onMyClick();
                rbMy.setTextColor(NUtil.getColor(R.color.colorMainBlue));
                break;
        }

    }

    public interface OnBottomNaviClickListener {
        void onHomePageClick();

        void onHomeClickAgain();

        void onSetClick();

        void onCategoryClick();

        void onMyClick();
    }

    public void setOnBottomNavClickListener(OnBottomNaviClickListener listener) {
        this.listener = listener;
    }

    public void setCategoryRadioChecked() {
        rbCategory.setChecked(true);
    }

    public void setHomeRadioChecked() {
        rbHomePage.setChecked(true);
    }

    public boolean isRbHomeChecked() {
        return rbHomePage.isChecked();
    }
}
