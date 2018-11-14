package com.sxmh.wt.education.adapter;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;

        import com.sxmh.wt.education.model.response.LessonTypeResponse;
        import com.sxmh.wt.education.model.response.lesson.MyLessonClassifyResponse;

        import java.util.List;

public class MyLessonCategoryVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<MyLessonClassifyResponse.CourseClassListBean> lessonTypeList;

    public MyLessonCategoryVpAdapter(FragmentManager fm, List<Fragment> fragmentList, List<MyLessonClassifyResponse.CourseClassListBean> lessonTypeList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.lessonTypeList = lessonTypeList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lessonTypeList.get(position).getCourseClassName();
    }
}
