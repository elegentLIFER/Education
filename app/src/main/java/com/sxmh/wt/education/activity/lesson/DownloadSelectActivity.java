package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.adapter.lesson.RvDownloadSelectAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownLoadBean;
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
import butterknife.OnClick;
import cn.jpush.android.service.DownloadActivity;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class DownloadSelectActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    private static final String TAG = "DownloadSelectActivity";
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;
    @InjectView(R.id.tv_download)
    TextView tvDownload;
    @InjectView(R.id.tv_see_cache)
    TextView tvSeeCache;

    private List<NetCourseResponse.NetCourseListBean> courseBeanList;
    private RvDownloadSelectAdapter lessonListAdapter;

    private String lessonId;

    private List<NetCourseInfoResponse.NetCourseInfoListBean> netCourseInfoList;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_download_select;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        Intent intent = getIntent();
        String lessonName = intent.getStringExtra(Constant.KEY_LESSON_NAME);
        lessonId = intent.getStringExtra(Constant.KEY_LESSON_ID);
        titleView.setTvTitle(lessonName);

        net.getNetCourse(lessonId, User.getInstance().getMemberId());

        netCourseInfoList = new ArrayList<>();
        courseBeanList = new ArrayList<>();
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_COURSE) {
            courseBeanList = (List<NetCourseResponse.NetCourseListBean>) content;
            lessonListAdapter = new RvDownloadSelectAdapter(this, courseBeanList);
            rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvContent.setAdapter(lessonListAdapter);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < courseBeanList.size(); i++) {
                sb.append(courseBeanList.get(i).getId() + ",");
            }
            net.getNetCourseInfo(sb.toString());
        } else if (request == Net.REQUEST_NET_COURSE_INFO) {
            NetCourseInfoResponse response = (NetCourseInfoResponse) content;
            netCourseInfoList.clear();
            netCourseInfoList.addAll(response.getNetCourseInfoList());
        } else if (request == Net.REQUEST_LESSON_DOWNLOAD_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (!response.isResult()) return;

//            ArrayList<DownLoadBean> list = new ArrayList<>();
//            List<Boolean> selectionList = lessonListAdapter.getSelectionList();
//            for (int i = 0; i < selectionList.size(); i++) {
//                if (selectionList.get(i)) {
//                    NetCourseResponse.NetCourseListBean bean = courseBeanList.get(i);
//
//                    NetCourseInfoResponse.NetCourseInfoListBean loadBean = new NetCourseInfoResponse.NetCourseInfoListBean();
//                    loadBean.setId(bean.getId());
//                    DownLoadBean downLoadBean = new DownLoadBean(loadBean);
//                    downLoadBean.setCourseName(bean.getNetCourseName());
//                    downLoadBean.setLitimg(bean.getLitimg());

//                    NetCourseInfoResponse.NetCourseInfoListBean infoBean = downLoadBean.getInfoBean();
//                    NetCourseInfoResponse.NetCourseInfoListBean courseInfoListBean = netCourseInfoList.get(i);
//                    infoBean.setPlayUrl(courseInfoListBean.getPlayUrl());
//                    infoBean.setIsCollect(courseInfoListBean.isIsCollect());
//                    infoBean.setLookNum(courseInfoListBean.getLookNum());
//                    infoBean.setRemark(courseInfoListBean.getRemark());
//                    infoBean.setTeacherInfo(courseInfoListBean.getTeacherInfo());
//                    infoBean.setTeacherName(courseInfoListBean.getTeacherName());
//                    infoBean.setTeacherPhoto(courseInfoListBean.getTeacherPhoto());
//                    list.add(downLoadBean);
//                }
//            }
//            Intent intent = new Intent(this, MyDownloadActivity.class);
//            intent.putExtra(MyDownloadActivity.DOWNLOAD_BEAN, list);
//            startActivity(intent);
//
            ArrayList<DownLoadBean> list = new ArrayList<>();
            List<Boolean> selectionList = lessonListAdapter.getSelectionList();

            Disposable subscribe = Observable.zip(
                    Observable.zip(Observable.fromIterable(selectionList),
                            Observable.fromIterable(courseBeanList),
                            (aBoolean, bean) -> aBoolean ? bean : new NetCourseResponse.NetCourseListBean()),
                    Observable.fromIterable(netCourseInfoList),
                    (bean, netCourseInfoListBean) -> {
                        DownLoadBean downLoadBean = new DownLoadBean(netCourseInfoListBean);
                        downLoadBean.setCourseName(bean.getNetCourseName());
                        downLoadBean.setLitimg(bean.getLitimg());
                        return downLoadBean;
                    }).filter(downLoadBean -> !TextUtils.isEmpty(downLoadBean.getCourseName()))
                    .subscribe(list::add);

            Intent intent = new Intent(this, MyDownloadActivity.class);
            intent.putExtra(MyDownloadActivity.DOWNLOAD_BEAN, list);
            startActivity(intent);
        }
    }

    @OnClick({R.id.tv_download, R.id.tv_see_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                String memberId = User.getInstance().getMemberId();
                if (TextUtils.isEmpty(memberId))
                    startActivity(new Intent(this, LoginActivity.class));
                else {
                    StringBuilder sb = new StringBuilder();
                    List<Boolean> selectionList = lessonListAdapter.getSelectionList();
                    for (int i = 0; i < selectionList.size(); i++) {
                        if (selectionList.get(i)) {
                            NetCourseResponse.NetCourseListBean bean = courseBeanList.get(i);
                            sb.append(bean.getId() + ",");
                        }
                    }
                    net.downloadVideoPower(sb.toString());
                }
                break;
            case R.id.tv_see_cache:
                startActivity(new Intent(this, MyDownloadActivity.class));
                break;
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }
}
