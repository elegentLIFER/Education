package com.sxmh.wt.education.activity.question_lib;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.CategoryVpAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.MyAskAnswerFragment;
import com.sxmh.wt.education.fragment.MyQuestionLibItemFragment;
import com.sxmh.wt.education.fragment.QuestionLibItemFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyQuestionLibActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.vp_engineer_type)
    ViewPager vpEngineerType;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private CategoryVpAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_question_lib;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        titleView.setTvTitle(getString(R.string.my_question_lib));
        lessonTypeList = new ArrayList<>();
        tabAndViewPagerPrepare();
        net.getMyCourseClassifyLv1();
    }

    private void tabAndViewPagerPrepare() {
        fragmentList = new ArrayList<>();
        adapter = new CategoryVpAdapter(getSupportFragmentManager(), fragmentList, lessonTypeList);
        vpEngineerType.setAdapter(adapter);

        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tlTab.setupWithViewPager(vpEngineerType);
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
        if (request == Net.REQUEST_MY_LESSON_CLASSFIFY) {
            LessonTypeResponse response = (LessonTypeResponse) content;
            lessonTypeList.clear();
            lessonTypeList.addAll(response.getCourseClassList());
            for (int i = 0; i < lessonTypeList.size(); i++) {
                MyQuestionLibItemFragment fragment = new MyQuestionLibItemFragment();
                Bundle args = new Bundle();
                args.putString(Constant.CLASS_ID, lessonTypeList.get(i).getId());
                fragment.setArguments(args);
                fragmentList.add(fragment);
            }
            adapter.notifyDataSetChanged();
            if (lessonTypeList.size() == 0) {
                ToastUtil.newToast(this, getString(R.string.no_course));
            }
        }
    }
}
