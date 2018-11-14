package com.sxmh.wt.education.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.RvQuesLibAllTypeAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class QuestionLibItemFragment extends BaseFragment {
    private static final String TAG = "QuestionLibItemFragment";
    @InjectView(R.id.rv_content)
    RecyclerView rvAllType;

    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private RvQuesLibAllTypeAdapter allTypeAdapter;
    private List<PaperListResponse.CourseClasslistBean> listList;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        String pid = args.getString(Constant.CLASS_ID);

        net.getCourseClassify(pid);

        listList = new ArrayList<>();
        lessonTypeList = new ArrayList<>();
        allTypeAdapter = new RvQuesLibAllTypeAdapter(getActivity(), pid, lessonTypeList, listList);
        rvAllType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAllType.setAdapter(allTypeAdapter);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_ALL_TYPE) {
            lessonTypeList.clear();
            lessonTypeList.addAll((List<LessonTypeResponse.CourseClassListBean>) content);

            StringBuilder sb = new StringBuilder();
            int size = lessonTypeList.size();
            for (int i = 0; i < size; i++) {
                sb.append(lessonTypeList.get(i).getId() + ",");
            }
            net.getPaperList(sb.toString());
        } else if (request == Net.REQUEST_SPEC_TYPE_PAPER_LIST) {
            List<PaperListResponse.CourseClasslistBean> beanList = (List<PaperListResponse.CourseClasslistBean>) content;
            listList.addAll(beanList);

            allTypeAdapter.notifyDataSetChanged();
        }
    }
}
