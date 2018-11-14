package com.sxmh.wt.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.lesson.LessonListActivity;
import com.sxmh.wt.education.adapter.RvMyAllTypeAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.MyCourseClassifyResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyCategoryEngiFragment extends BaseFragment implements RvMyAllTypeAdapter.OnItemClickListener {
    private static final String TAG = "CategoryEngiFragment";
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    private List<MyCourseClassifyResponse.CourseClassListBean> lessonTypeList;
    private RvMyAllTypeAdapter allTypeAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        String classId = args.getString(Constant.CLASS_ID);

        net.getMyCourseClassify(classId);

        lessonTypeList = new ArrayList<>();
        allTypeAdapter = new RvMyAllTypeAdapter(getActivity(), classId, lessonTypeList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(allTypeAdapter);
        allTypeAdapter.setItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MY_COURSE_CLASSFIFY) {
            lessonTypeList.clear();
            MyCourseClassifyResponse response = (MyCourseClassifyResponse) content;
            lessonTypeList.addAll(response.getCourseClassList());
            allTypeAdapter.notifyDataSetChanged();

            StringBuilder sb = new StringBuilder();
            int size = lessonTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(lessonTypeList.get(i).getId() + ",");
            }
            net.getNetCourseList(sb.toString());
        }
    }

    @Override
    public void OnItemClick(int topPosition, int position) {
        Intent intent = new Intent(getActivity(), LessonListActivity.class);
        MyCourseClassifyResponse.CourseClassListBean.NetCourseListBean bean = lessonTypeList.get(topPosition).getNetCourseList().get(position);
        intent.putExtra(Constant.KEY_LESSON_NAME, bean.getNetCourseName());
        intent.putExtra(Constant.KEY_LESSON_ID, bean.getNetCourseId());
        startActivity(intent);
    }
}
