package com.sxmh.wt.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.ask_answer.AskAnswerActivity;
import com.sxmh.wt.education.activity.lesson.DownloadSelectActivity;
import com.sxmh.wt.education.activity.live.LiveActivity;
import com.sxmh.wt.education.activity.lesson.LessonListActivity;
import com.sxmh.wt.education.activity.question_lib.QuestionLibActivity;
import com.sxmh.wt.education.adapter.RvAllTypeAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class CategoryEngiFragment extends BaseFragment implements RvAllTypeAdapter.OnItemClickListener {
    private static final String TAG = "CategoryEngiFragment";
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private RvAllTypeAdapter allTypeAdapter;
    private List<NetCourseListResponse.ListBean> dataList;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        String classId = args.getString(Constant.CLASS_ID);

        net.getCourseClassify(classId);

        dataList = new ArrayList<>();
        lessonTypeList = new ArrayList<>();
        allTypeAdapter = new RvAllTypeAdapter(getActivity(), classId, lessonTypeList, dataList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(allTypeAdapter);
        allTypeAdapter.setItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_ALL_TYPE) {
            lessonTypeList.clear();
            lessonTypeList.addAll((List<LessonTypeResponse.CourseClassListBean>) content);
            allTypeAdapter.notifyDataSetChanged();

            StringBuilder sb = new StringBuilder();
            int size = lessonTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(lessonTypeList.get(i).getId() + ",");
            }
            net.getNetCourseList(sb.toString());
        } else if (request == Net.REQUEST_SPEC_TYPE_LESSON) {
            dataList.clear();
            dataList.addAll((List<NetCourseListResponse.ListBean>) content);
            allTypeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnNetSchoolTalkClick() {
        showToast("网校讲题");
    }

    @Override
    public void OnAskAnswerOnlineClick() {
        startActivity(new Intent(getActivity(), AskAnswerActivity.class));
    }

    @Override
    public void OnLiveClick() {
        startActivity(new Intent(getActivity(), LiveActivity.class));
    }

    @Override
    public void OnWorkOnlineClick() {
        startActivity(new Intent(getActivity(), QuestionLibActivity.class));
    }

    @Override
    public void OnItemClick(int topPosition,int position) {
        Intent intent = new Intent(getActivity(), LessonListActivity.class);
        NetCourseListResponse.ListBean.NetCourseListBean courseListBean = dataList.get(topPosition).getNetCourseList().get(position);
        intent.putExtra(Constant.KEY_LESSON_NAME, courseListBean.getNetCourseName());
        intent.putExtra(Constant.KEY_LESSON_ID, courseListBean.getNetCourseId());
        startActivity(intent);
    }

    @Override
    public void OnDownloadClick(int topPosition, int position) {
        Intent intent = new Intent(getActivity(), DownloadSelectActivity.class);
        NetCourseListResponse.ListBean.NetCourseListBean courseListBean = dataList.get(topPosition).getNetCourseList().get(position);
        intent.putExtra(Constant.KEY_LESSON_NAME, courseListBean.getNetCourseName());
        intent.putExtra(Constant.KEY_LESSON_ID, courseListBean.getNetCourseId());
        startActivity(intent);
    }
}
