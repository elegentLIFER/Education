package com.sxmh.wt.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.ask_answer.QuestionListActivity;
import com.sxmh.wt.education.adapter.RvAskAnswerAdapter;
import com.sxmh.wt.education.adapter.RvMyAskAnswerAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.ask_answer.MyNetProblemClassResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyAskAnswerFragment extends BaseFragment implements RvMyAskAnswerAdapter.OnItemClickListener {
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    public static final String NET_PROBLEM_CLASS_ID = "netProblemClassId";
    private List<MyNetProblemClassResponse.CourseClassListBean> lessonTypeList;
    private RvMyAskAnswerAdapter askAnswerAdapter;
    private List<NetCourseListResponse.ListBean.NetCourseListBean> dataList;

    private String classId;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        classId = args.getString(Constant.CLASS_ID);

        net.getMyNetProblemCourseClass(classId);

        dataList = new ArrayList<>();
        lessonTypeList = new ArrayList<>();
        askAnswerAdapter = new RvMyAskAnswerAdapter(getActivity(), lessonTypeList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(askAnswerAdapter);
        askAnswerAdapter.setItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_PROBLEM) {
            MyNetProblemClassResponse response = (MyNetProblemClassResponse) content;
            lessonTypeList.clear();
            lessonTypeList.addAll(response.getCourseClassList());
            askAnswerAdapter.notifyDataSetChanged();

            StringBuilder sb = new StringBuilder();
            int size = lessonTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(lessonTypeList.get(i).getId() + ",");
            }
        }
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(Constant.CLASS_ID, classId);
        String id = lessonTypeList.get(position).getId();
        intent.putExtra(NET_PROBLEM_CLASS_ID, id);
        intent.putExtra(Constant.IS_MY, true);
        startActivity(intent);
    }
}
