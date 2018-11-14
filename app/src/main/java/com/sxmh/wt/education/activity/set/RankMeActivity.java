package com.sxmh.wt.education.activity.set;

import android.os.Bundle;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.view.TitleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RankMeActivity extends BaseActivity implements TitleView.OnTitleViewClickListener{
    @InjectView(R.id.title_view)
    TitleView titleView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_rank_me;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {

    }
}
