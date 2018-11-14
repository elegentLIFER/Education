package com.sxmh.wt.education.activity.lesson;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.CategoryVpAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.MyCategoryEngiFragment;
import com.sxmh.wt.education.fragment.MyQuestionLibItemFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyLessonActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.vp_engineer_type)
    ViewPager vpEngineerType;
    @InjectView(R.id.title_view)
    TitleView titleView;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private CategoryVpAdapter adapter;
    private List<Fragment> fragmentList;
    private int which;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_my_lesson;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        lessonTypeList = new ArrayList<>();
        tabAndViewPagerPrepare();

        net.getMyCourseClassifyLv1();
    }

    private void tabAndViewPagerPrepare() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        adapter = new CategoryVpAdapter(fragmentManager, fragmentList, lessonTypeList);
        vpEngineerType.setAdapter(adapter);

        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tlTab.setupWithViewPager(vpEngineerType);
    }

    @Override
    public void updateContent(int request, Object content) {
        LessonTypeResponse response = (LessonTypeResponse) content;
        List<LessonTypeResponse.CourseClassListBean> beanList = response.getCourseClassList();
        lessonTypeList.clear();
        lessonTypeList.addAll(beanList);
        for (int i = 0; i < lessonTypeList.size(); i++) {
            MyCategoryEngiFragment fragment = new MyCategoryEngiFragment();
            Bundle args = new Bundle();
            args.putString(Constant.CLASS_ID, lessonTypeList.get(i).getId());
            fragment.setArguments(args);
            fragmentList.add(fragment);
        }
        adapter.notifyDataSetChanged();
        vpEngineerType.setCurrentItem(which);
        if (lessonTypeList.size() == 0) {
            ToastUtil.newToast(this, getString(R.string.no_course));
        }
    }

    public void setWhich(boolean isFirst, int which) {
        this.which = which;
        if (!isFirst) {
            vpEngineerType.setCurrentItem(which);
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {

    }
}
