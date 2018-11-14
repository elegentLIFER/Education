package com.sxmh.wt.education.activity.lesson;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.view.TitleView;

import butterknife.InjectView;
import io.vov.vitamio.utils.Log;

public class CacheActivity extends BaseActivity implements TitleView.OnTitleViewClickListener{
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_cache)
    RecyclerView rvCache;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_cache;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);


        rvCache.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void updateContent(int request, Object content) {

    }

    @Override
    public void onBackClick() {

    }

    @Override
    public void onTitleRightClick() {

    }
}
