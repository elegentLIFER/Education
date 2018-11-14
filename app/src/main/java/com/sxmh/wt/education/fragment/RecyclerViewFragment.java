package com.sxmh.wt.education.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.live.LiveActivity;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.adapter.RvLiveAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class RecyclerViewFragment extends BaseFragment {
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    private List<LiveRoomListResponse.LiveListBean> beanList;
    private RvLiveAdapter liveAdapter;
    private LiveRoomListResponse.LiveListBean liveListBean;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String id = arguments.getString(LiveActivity.COURSE_CLASS_ID);
        net.getLiveRoomList(id);

        beanList = new ArrayList<>();
        liveAdapter = new RvLiveAdapter(getContext(), beanList);
        rvContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(liveAdapter);
        liveAdapter.setOnItemClickListener((position) -> {
            liveListBean = beanList.get(position);
            net.getLivePower(liveListBean.getId());
        });
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_LIVE_LIST) {
            beanList.clear();
            LiveRoomListResponse response = (LiveRoomListResponse) content;
            beanList.addAll(response.getLiveList());
            liveAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_LIVE_WATCH_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) {
                Intent intent = new Intent(getContext(), RealLiveActivity.class);
                intent.putExtra(LiveActivity.LIVE_ROOM_ID, liveListBean.getId());
                startActivity(intent);
                ToastUtil.newToast(getContext(), getString(R.string.developing));
            }
        }
    }
}
