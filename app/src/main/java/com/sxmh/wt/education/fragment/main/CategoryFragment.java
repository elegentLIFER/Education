package com.sxmh.wt.education.fragment.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.MainActivity;
import com.sxmh.wt.education.adapter.CategoryVpAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.fragment.CategoryEngiFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class CategoryFragment extends BaseFragment {
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.vp_engineer_type)
    ViewPager vpEngineerType;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private CategoryVpAdapter adapter;
    private List<Fragment> fragmentList;

    private int which;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initData() {
        lessonTypeList = new ArrayList<>();
        tabAndViewPagerPrepare();

        net.getCourseClassify();
        ((MainActivity) getActivity()).toCategoryFragment();
    }

    private void tabAndViewPagerPrepare() {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentList = new ArrayList<>();
        adapter = new CategoryVpAdapter(fragmentManager, fragmentList, lessonTypeList);
        vpEngineerType.setAdapter(adapter);

        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tlTab.setupWithViewPager(vpEngineerType);
    }

    @Override
    public void updateContent(int request, Object content) {
        List<LessonTypeResponse.CourseClassListBean> beanList = (List<LessonTypeResponse.CourseClassListBean>) content;

        lessonTypeList.clear();
        lessonTypeList.addAll(beanList);
        for (int i = 0; i < lessonTypeList.size(); i++) {
            CategoryEngiFragment fragment = new CategoryEngiFragment();
            Bundle args = new Bundle();
            args.putString(Constant.CLASS_ID, lessonTypeList.get(i).getId());
            fragment.setArguments(args);
            fragmentList.add(fragment);
        }
        adapter.notifyDataSetChanged();
        vpEngineerType.setCurrentItem(which);
    }

    public void setWhich(boolean isFirst, int which) {
        this.which = which;
        if (!isFirst) {
            if (vpEngineerType!=null)
            vpEngineerType.setCurrentItem(which);
        }
    }
}
