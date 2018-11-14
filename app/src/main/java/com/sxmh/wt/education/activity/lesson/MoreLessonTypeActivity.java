package com.sxmh.wt.education.activity.lesson;

import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.BanScrollGridLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.LessonTypeAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MoreLessonTypeActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_lesson)
    RecyclerView rvLesson;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private LessonTypeAdapter lessonTypeAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void initData() {
        titleView.setTvTitle(getString(R.string.lesson_type));
        titleView.setOnTitleViewClickListener(this);
        lessonTypeList = new ArrayList<>();
        lessonTypeAdapter = new LessonTypeAdapter(this, lessonTypeList);
        rvLesson.setLayoutManager(new BanScrollGridLayoutManager(this, 2));
        rvLesson.setAdapter(lessonTypeAdapter);
        lessonTypeAdapter.setOnItemClickListener((position) -> {
        });

        net.getCourseClassify();
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
        lessonTypeList.clear();
        lessonTypeList.addAll(((List<LessonTypeResponse.CourseClassListBean>) content));
        lessonTypeAdapter.notifyDataSetChanged();
    }
}
