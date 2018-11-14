package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.RecentWatchAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.vov.vitamio.utils.StringUtils;

public class RecentWatchActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, RecentWatchAdapter.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_recent_watch)
    RecyclerView rvRecentWatch;

    private List<NetCourseResponse.NetCourseListBean> netCourseListBeanList;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_recent_watch;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        netCourseListBeanList = new ArrayList<>();
        RecentWatchAdapter recentWatchAdapter = new RecentWatchAdapter(this, netCourseListBeanList);
        recentWatchAdapter.setOnItemClickListener(this);
        rvRecentWatch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRecentWatch.setAdapter(recentWatchAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        String recentWatchString = sharedPreferences.getString(Constant.SP_KEY_RECENT_WATCH, "");
        Stack<NetCourseResponse.NetCourseListBean> stack = new Stack<>();

        if (TextUtils.isEmpty(recentWatchString)) return;
        Disposable subscribe = Observable.just(recentWatchString)
                .concatMap((beforeString) -> Observable.fromArray(recentWatchString.split(Constant.SEPARATER_JSON)))
                .filter(s -> !TextUtils.isEmpty(s))
                .map(s -> new Gson().fromJson(s, NetCourseResponse.NetCourseListBean.class))
                .subscribe(stack::push);

        int size = stack.size();
        for (int i = 0; i < size; i++)
            netCourseListBeanList.add(stack.pop());
        recentWatchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void OnItemClick(int position) {
        NetCourseResponse.NetCourseListBean bean = netCourseListBeanList.get(position);
        toDoadLoad(bean);
    }

    private void toDoadLoad(NetCourseResponse.NetCourseListBean bean) {
        Intent intent = new Intent(RecentWatchActivity.this, LessonWatchActivity.class);
        DownloadTransbean transbean = new DownloadTransbean(bean.getNetCourseName(), bean.getId(), bean.getLitimg());
        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
        startActivity(intent);
    }

    @Override
    public void updateContent(int request, Object content) {
    }
}
