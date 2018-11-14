package com.sxmh.wt.education.activity.set;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.BasePresenter;
import com.sxmh.wt.education.view.TitleView;

import butterknife.InjectView;

public class FunctionIntroduceActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_current_version;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {

    }

    @Override
    public void updateContent(int request, Object content) {

    }
}
