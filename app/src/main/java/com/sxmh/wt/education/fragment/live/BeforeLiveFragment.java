package com.sxmh.wt.education.fragment.live;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.activity.lesson.LessonWatchActivity;
import com.sxmh.wt.education.adapter.live.RvBeforeLiveAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;
import com.sxmh.wt.education.model.response.live.BeforeLiveListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class BeforeLiveFragment extends BaseFragment implements RvBeforeLiveAdapter.OnItemClickListener {
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    private List<BeforeLiveListResponse.NetCourseListBean> list;
    private RvBeforeLiveAdapter adapter;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        String courseClassId = getArguments().getString(RealLiveActivity.COURSE_CLASS_ID);
        net.getBeforeLiveList(courseClassId);

        list = new ArrayList<>();
        adapter = new RvBeforeLiveAdapter(getContext(), list);
        rvContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(adapter);
        adapter.setItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        List<BeforeLiveListResponse.NetCourseListBean> beanList = (List<BeforeLiveListResponse.NetCourseListBean>) content;
        list.clear();
        list.addAll(beanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        startLessonWatchActivity(position);
    }

    private void startLessonWatchActivity(int position) {
        BeforeLiveListResponse.NetCourseListBean bean = list.get(position);
//        Gson gson = new Gson();
//        String json = gson.toJson(bean);
//        saveRecentWatch(json);
        Intent intent = new Intent(getContext(), LessonWatchActivity.class);
        DownloadTransbean transbean = new DownloadTransbean(bean.getNetCourseName(), bean.getId(), "");
        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
        startActivity(intent);
    }
}