package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.adapter.LessonAdviseAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.HomePageDataResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MoreLessonRecActivity extends BaseActivity implements TitleView.OnTitleViewClickListener,LessonAdviseAdapter.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_lesson)
    RecyclerView rvLesson;

    private List<HomePageDataResponse.RecomNetCourseListBean> lessonRecList;
    private LessonAdviseAdapter lessonAdviseAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void initData() {
        titleView.setTvTitle(getString(R.string.lesson_advise));
        titleView.setOnTitleViewClickListener(this);
        lessonRecList = new ArrayList<>();
        lessonAdviseAdapter = new LessonAdviseAdapter(this, lessonRecList);
        rvLesson.setLayoutManager(new GridLayoutManager(this, 2));
        rvLesson.setAdapter(lessonAdviseAdapter);
        lessonAdviseAdapter.setOnItemClickListener(this);

        net.getHomePageData();
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
        if (request == Net.KEY_RECM_LESSON) {
            lessonRecList.clear();
            lessonRecList.addAll(((List<HomePageDataResponse.RecomNetCourseListBean>) content));
            lessonAdviseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClick(int position) {
        ToastUtil.newToast(this, getString(R.string.developing));
        startActivity(new Intent(this, RealLiveActivity.class));
    }
}
