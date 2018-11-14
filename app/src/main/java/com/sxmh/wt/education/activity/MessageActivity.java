package com.sxmh.wt.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.RadioGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.MessageListItemAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.main.HomeFragment;
import com.sxmh.wt.education.model.response.MessageListResponse;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageActivity extends BaseActivity implements TitleView.OnTitleViewClickListener,
        RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.rv_activity_notify)
    RecyclerView rvActivityNotify;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String ACTION_MESSAGE = "action_message";
    public static final String INFORM_LIST_BEAN = "inform_list_bean";
    public static final String FLAG_STUDY = "0";
    public static final String FLAG_ACTIVITY = "1";
    public static final String FLAG_SYSTEM = "2";

    private List<MessageListResponse.InformListBean> beanList;
    private MessageListItemAdapter messageListItemAdapter;
    private boolean isRefresh;

    private int currentPage = 1;
    private String currentFlag = FLAG_SYSTEM;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        rgGroup.setOnCheckedChangeListener(this);

        getInformList();
        initRvActivityNotify();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorMainBlue, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRvActivityNotify() {
        beanList = new ArrayList<>();
        messageListItemAdapter = new MessageListItemAdapter(this, beanList);
        rvActivityNotify.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvActivityNotify.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvActivityNotify.setAdapter(messageListItemAdapter);
        messageListItemAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(this, NotificationContentActivity.class);
            intent.setAction(ACTION_MESSAGE);
            MessageListResponse.InformListBean bean = beanList.get(position);
            intent.putExtra(INFORM_LIST_BEAN, bean);
            startActivity(intent);
        });

        rvActivityNotify.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = NUtil.findMax(lastPositions);
                    }
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        isRefresh = false;
                        currentPage++;
                        // 滑动到底了
                        getInformList();
                    }
                }
            }
        });
    }

    private void getInformList() {
        net.getInformList(currentFlag, currentPage + "", "10");
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
        showToast("hh");
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MESSAGE_LIST) {
            MessageListResponse response = (MessageListResponse) content;
            List<MessageListResponse.InformListBean> informList = response.getInformList();
            if (isRefresh) beanList.clear();
            beanList.addAll(informList);
            messageListItemAdapter.notifyDataSetChanged();
            ToastUtil.newToast(this, "刷新成功");
            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isRefresh = true;
        currentPage = 1;
        switch (checkedId) {
            case R.id.rb_system_notify:
                currentFlag = FLAG_SYSTEM;
                break;
            case R.id.rb_activity_notify:
                currentFlag = FLAG_ACTIVITY;
                break;
            case R.id.rb_study_remind:
                currentFlag = FLAG_STUDY;
                break;
        }

        getInformList();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        currentPage = 1;
        getInformList();
    }
}
