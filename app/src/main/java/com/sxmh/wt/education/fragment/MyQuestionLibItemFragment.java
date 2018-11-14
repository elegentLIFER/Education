package com.sxmh.wt.education.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.MyRvQuesLibAllTypeAdapter;
import com.sxmh.wt.education.adapter.questionlib.RvQuesLibAllTypeAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyQuestionLibItemFragment extends BaseFragment {
    private static final String TAG = "QuestionLibItemFragment";
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    private List<MyPaperResponse.CourseClasslistBean> lessonTypeList;
    private MyRvQuesLibAllTypeAdapter allTypeAdapter;
    private List<PaperListResponse.CourseClasslistBean> listList;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        String pid = args.getString(Constant.CLASS_ID);

        net.getMyPaper(pid);

        listList = new ArrayList<>();
        lessonTypeList = new ArrayList<>();
        allTypeAdapter = new MyRvQuesLibAllTypeAdapter(getActivity(), lessonTypeList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(allTypeAdapter);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MY_PAPER) {
            MyPaperResponse response = (MyPaperResponse) content;
            lessonTypeList.clear();
            lessonTypeList.addAll(response.getCourseClasslist());
            allTypeAdapter.notifyDataSetChanged();
        }
    }
}
