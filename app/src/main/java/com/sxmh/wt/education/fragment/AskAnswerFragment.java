package com.sxmh.wt.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.ask_answer.QuestionListActivity;
import com.sxmh.wt.education.adapter.RvAskAnswerAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class AskAnswerFragment extends BaseFragment implements RvAskAnswerAdapter.OnItemClickListener {
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    public static final String NET_PROBLEM_CLASS_ID = "netProblemClassId";
    private List<NetProblemClassResponse.NetProblemClassListBean> lessonTypeList;
    private RvAskAnswerAdapter askAnswerAdapter;
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

        net.getNetProblemClass(classId);

        dataList = new ArrayList<>();
        lessonTypeList = new ArrayList<>();
        askAnswerAdapter = new RvAskAnswerAdapter(getActivity(), classId, lessonTypeList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(askAnswerAdapter);
        askAnswerAdapter.setItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_PROBLEM) {
            NetProblemClassResponse response = (NetProblemClassResponse) content;
            lessonTypeList.clear();
            lessonTypeList.addAll(response.getNetProblemClassList());
            askAnswerAdapter.notifyDataSetChanged();

            StringBuilder sb = new StringBuilder();
            int size = lessonTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(lessonTypeList.get(i).getNetProblemClassId() + ",");
            }
        }
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(Constant.CLASS_ID, classId);
        intent.putExtra(NET_PROBLEM_CLASS_ID, lessonTypeList.get(position).getNetProblemClassId());
        intent.putExtra(Constant.IS_MY, false);
        startActivity(intent);
    }
}
