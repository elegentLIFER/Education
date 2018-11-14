package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.LessonHotAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.HomePageDataResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MoreLessonHotActivity extends BaseActivity implements TitleView.OnTitleViewClickListener,LessonHotAdapter.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_lesson)
    RecyclerView rvLesson;

    private List<HomePageDataResponse.HotNetCourseListBean> lessonHotList;
    private LessonHotAdapter lessonHotAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void initData() {
        titleView.setTvTitle(getString(R.string.lesson_hot));
        titleView.setOnTitleViewClickListener(this);
        lessonHotList = new ArrayList<>();
        lessonHotAdapter = new LessonHotAdapter(this, lessonHotList);
        rvLesson.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLesson.setAdapter(lessonHotAdapter);
        lessonHotAdapter.setOnItemClickListener(this);

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
        if (request == Net.KEY_HOT_LESSON) {
            lessonHotList.clear();
            lessonHotList.addAll(((List<HomePageDataResponse.HotNetCourseListBean>) content));
            lessonHotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClick(int position) {
        startActivity(new Intent(this, MoreLessonHotActivity.class));
    }
}
