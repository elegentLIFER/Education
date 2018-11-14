package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.adapter.lesson.RvLessonListAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownLoadBean;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.lesson.NetCourseInfoResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class LessonListActivity extends BaseActivity implements TitleView.OnTitleViewClickListener,
        RvLessonListAdapter.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_lesson)
    RecyclerView rvLesson;

    private List<NetCourseResponse.NetCourseListBean> courseBeanList;
    private RvLessonListAdapter lessonListAdapter;

    private String lessonId;
    private int clickedPosition;
    private NetCourseResponse.NetCourseListBean netCourseListBean;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        Intent intent = getIntent();
        String lessonName = intent.getStringExtra(Constant.KEY_LESSON_NAME);
        lessonId = intent.getStringExtra(Constant.KEY_LESSON_ID);
        titleView.setTvTitle(lessonName);

        net.getNetCourse(lessonId, User.getInstance().getMemberId());

        courseBeanList = new ArrayList<>();
        lessonListAdapter = new RvLessonListAdapter(this, courseBeanList);
        rvLesson.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLesson.setAdapter(lessonListAdapter);
        lessonListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_COURSE) {
            courseBeanList.clear();
            courseBeanList.addAll((List<NetCourseResponse.NetCourseListBean>) content);
            lessonListAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_NET_COURSE_INFO) {
            NetCourseInfoResponse response = (NetCourseInfoResponse) content;
            List<NetCourseInfoResponse.NetCourseInfoListBean> beanList = response.getNetCourseInfoList();
            NetCourseInfoResponse.NetCourseInfoListBean bean = beanList.get(0);

            DownLoadBean downLoadBean = new DownLoadBean(bean);
            downLoadBean.setCourseName(netCourseListBean.getNetCourseName());
            downLoadBean.setLitimg(netCourseListBean.getLitimg());
            ArrayList<DownLoadBean> list = new ArrayList<>();
            list.add(downLoadBean);
            Intent intent = new Intent(this, MyDownloadActivity.class);
            intent.putExtra(MyDownloadActivity.DOWNLOAD_BEAN, list);
            startActivity(intent);
        } else if (request == Net.REQUEST_COLLECT) {
            net.getNetCourse(lessonId, User.getInstance().getMemberId());
        } else if (request == Net.REQUEST_CANCEL_COLLECT) {
            net.getNetCourse(lessonId, User.getInstance().getMemberId());
        } else if (request == Net.REQUEST_LESSON_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) startLessonWatchActivity(clickedPosition);
        } else if (request == Net.REQUEST_LESSON_DOWNLOAD_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) net.getNetCourseInfo(netCourseListBean.getId());
        }
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
        NetCourseResponse.NetCourseListBean bean = courseBeanList.get(position);
        if (bean.getState() == Constant.LESSON_POWER_FREE) {
            startLessonWatchActivity(position);
            return;
        }
        clickedPosition = position;

        String memberId = User.getInstance().getMemberId();
        if (TextUtils.isEmpty(memberId)) startActivity(new Intent(this, LoginActivity.class));
        else net.getNetCoursePower(bean.getId(),memberId);
    }

    private void startLessonWatchActivity(int position) {
        NetCourseResponse.NetCourseListBean bean = courseBeanList.get(position);
        saveRecentWatch(bean);

        DownloadTransbean transbean = new DownloadTransbean(bean.getNetCourseName(), bean.getId(), bean.getLitimg());
        Intent intent = new Intent(this, LessonWatchActivity.class);
        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
        startActivity(intent);
    }

    private void saveRecentWatch(NetCourseResponse.NetCourseListBean bean) {
        Gson gson = new Gson();
        String json = gson.toJson(bean);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        String recentWatchString = sharedPreferences.getString(Constant.SP_KEY_RECENT_WATCH, "");
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!recentWatchString.contains(bean.getId())) {
            String newValue = recentWatchString + Constant.SEPARATER_JSON + json;
            edit.putString(Constant.SP_KEY_RECENT_WATCH, newValue);
        }
        edit.commit();
    }

    @Override
    public void OnCollectClick(int position) {
        NetCourseResponse.NetCourseListBean bean = courseBeanList.get(position);
        if (!bean.isIsCollect())
            net.doCollect(bean.getId(), Net.COLLECT_FLAG_TELL);
        else
            net.doCancelCollect(bean.getId(), Net.COLLECT_FLAG_TELL);
    }

    @Override
    public void OnDownloadClick(int position) {
        netCourseListBean = courseBeanList.get(position);
        net.downloadVideoPower(netCourseListBean.getId());
    }
}
