package com.sxmh.wt.education.activity.live;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.CommonFragmentVpAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.MyQuestionLibItemFragment;
import com.sxmh.wt.education.fragment.RecyclerViewFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.AutoFitHViewPager;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyLiveActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.vp_engineer_type)
    AutoFitHViewPager vpEngineerType;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private CommonFragmentVpAdapter fragmentVpAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_my_live;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        net.getMyCourseClassifyLv1();
        tabAndViewPagerPrepare();
    }

    private void tabAndViewPagerPrepare() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        fragmentVpAdapter = new CommonFragmentVpAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vpEngineerType.setAdapter(fragmentVpAdapter);

        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tlTab.setupWithViewPager(vpEngineerType);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MY_LESSON_CLASSFIFY) {
            LessonTypeResponse response = (LessonTypeResponse) content;
            titleList.clear();
            fragmentList.clear();
            List<LessonTypeResponse.CourseClassListBean> beanList = response.getCourseClassList();
            for (int i = 0; i < beanList.size(); i++) {
                RecyclerViewFragment fragment = new RecyclerViewFragment();
                Bundle bundle = new Bundle();
                LessonTypeResponse.CourseClassListBean bean = beanList.get(i);
                bundle.putString(LiveActivity.COURSE_CLASS_ID, bean.getId());
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
                titleList.add(bean.getCourseClassName());
            }
            fragmentVpAdapter.notifyDataSetChanged();
            if (titleList.size() == 0) {
                ToastUtil.newToast(this, getString(R.string.no_course));
            }
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
