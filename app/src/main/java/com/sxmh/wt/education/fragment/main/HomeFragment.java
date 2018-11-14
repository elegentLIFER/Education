package com.sxmh.wt.education.fragment.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.BanScrollGridLayoutManager;
import com.sxmh.wt.education.BanScrollLinearLayoutManager;
import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.MainActivity;
import com.sxmh.wt.education.activity.MessageActivity;
import com.sxmh.wt.education.activity.lesson.MoreLessonHotActivity;
import com.sxmh.wt.education.activity.lesson.MoreLessonRecActivity;
import com.sxmh.wt.education.activity.lesson.MoreLessonTypeActivity;
import com.sxmh.wt.education.activity.NotificationContentActivity;
import com.sxmh.wt.education.activity.SearchActivity;
import com.sxmh.wt.education.activity.ask_answer.AskAnswerActivity;
import com.sxmh.wt.education.activity.lesson.LessonWatchActivity;
import com.sxmh.wt.education.activity.live.LiveActivity;
import com.sxmh.wt.education.activity.question_lib.QuestionLibActivity;
import com.sxmh.wt.education.adapter.ArvHomeBannerAdapter;
import com.sxmh.wt.education.adapter.ArvNotificationAdapter;
import com.sxmh.wt.education.adapter.LessonAdviseAdapter;
import com.sxmh.wt.education.adapter.LessonHotAdapter;
import com.sxmh.wt.education.adapter.LessonTypeAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.response.GetHomeCycImgResponse;
import com.sxmh.wt.education.model.response.HomePageDataResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.RightTopStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @InjectView(R.id.iv_scan)
    ImageView ivScan;
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.rtsv_message)
    RightTopStatusView rtsvMessage;
    @InjectView(R.id.rtsv_lesson)
    RightTopStatusView tysvLesson;
    @InjectView(R.id.rtsv_live)
    RightTopStatusView rtsvLive;
    @InjectView(R.id.rtsv_question_lib)
    RightTopStatusView rtsvQuestionLib;
    @InjectView(R.id.rtsv_ask_answer)
    RightTopStatusView rtsvAskAnswer;
    @InjectView(R.id.rv_lesson_type)
    RecyclerView rvLessonType;
    @InjectView(R.id.rv_lesson_advise)
    RecyclerView rvLessonAdvise;
    @InjectView(R.id.rv_lesson_hot)
    RecyclerView rvLessonHot;
    @InjectView(R.id.more_lesson_type)
    TextView moreLessonType;
    @InjectView(R.id.more_lesson_advise)
    TextView moreLessonAdvise;
    @InjectView(R.id.more_lesson_hot)
    TextView moreLessonHot;
    @InjectView(R.id.arv_banner)
    AutoPlayRecyclerView arvBanner;
    @InjectView(R.id.arv_notification)
    AutoPlayRecyclerView arvNotification;

    public static final String ACTION_NOTIFICATION = "action_notification";
    public static final String INTENT_NOTIFICATION_ID = "intent_notification_id";

    public static final int FiX_NUM_HOT_LESSON = 5;
    public static final int FiX_NUM_REC_LESSON = 4;
    public static final int FiX_NUM_LESSON_TYPE = 6;

    private List<GetHomeCycImgResponse.FirstCycleImgBean> urlList;
    private ArvHomeBannerAdapter bannerAdapter;
    private ArvNotificationAdapter notificationAdapter;
    private LessonTypeAdapter lessonTypeAdapter;
    private LessonAdviseAdapter lessonAdviseAdapter;
    private LessonHotAdapter lessonHotAdapter;

    private List<HomePageDataResponse.HotNetCourseListBean> hotLessonList;
    private List<HomePageDataResponse.RecomNetCourseListBean> recomLessonList;
    private List<HomePageDataResponse.TopInformListBean> notificationList;
    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        net.getCycImg();
        net.getHomePageData();
        net.getCourseClassify();

        initLessonData();
        initNotificationView();
    }

    private void initLessonData() {
        ScaleLayoutManager build = new ScaleLayoutManager.Builder(getContext(), -40)
                .setOrientation(OrientationHelper.HORIZONTAL)
                .setMaxVisibleItemCount(3)
                .build();
        arvBanner.setLayoutManager(build);
        urlList = new ArrayList<>();
        bannerAdapter = new ArvHomeBannerAdapter(getContext(), urlList);
        arvBanner.setAdapter(bannerAdapter);

        hotLessonList = new ArrayList<>(FiX_NUM_HOT_LESSON);
        lessonHotAdapter = new LessonHotAdapter(getContext(), hotLessonList);
        lessonHotAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), LessonWatchActivity.class);
            HomePageDataResponse.HotNetCourseListBean bean = hotLessonList.get(position);
            DownloadTransbean transbean = new DownloadTransbean(bean.getNetCourseName(), bean.getId(), bean.getLitimg());
            intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
            startActivity(intent);
        });
        rvLessonHot.setLayoutManager(new BanScrollLinearLayoutManager(getContext()));
        rvLessonHot.setAdapter(lessonHotAdapter);

        lessonTypeList = new ArrayList<>(FiX_NUM_LESSON_TYPE);
        lessonTypeAdapter = new LessonTypeAdapter(getContext(), lessonTypeList);
        rvLessonType.setLayoutManager(new BanScrollGridLayoutManager(getContext(), 2));
        rvLessonType.setAdapter(lessonTypeAdapter);
        lessonTypeAdapter.setOnItemClickListener(position -> {
            MyApplication.setCurrentLessonTypePosition(position);
            ((MainActivity) getActivity()).toCategoryFragment();
        });

        recomLessonList = new ArrayList<>(FiX_NUM_REC_LESSON);
        lessonAdviseAdapter = new LessonAdviseAdapter(getContext(), recomLessonList);
        lessonAdviseAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), LessonWatchActivity.class);
            HomePageDataResponse.RecomNetCourseListBean bean = recomLessonList.get(position);
            DownloadTransbean transbean = new DownloadTransbean(bean.getNetCourseName(), bean.getId(), bean.getLitimg());
            intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
            startActivity(intent);
        });
        rvLessonAdvise.setLayoutManager(new BanScrollGridLayoutManager(getContext(), 2));
        rvLessonAdvise.setAdapter(lessonAdviseAdapter);

        notificationList = new ArrayList<>();
        notificationAdapter = new ArvNotificationAdapter(getContext(), notificationList);
        arvNotification.setAdapter(notificationAdapter);
        notificationAdapter.setItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), NotificationContentActivity.class);
            intent.setAction(ACTION_NOTIFICATION);
            String notificationId = notificationList.get(position).getId();
            intent.putExtra(INTENT_NOTIFICATION_ID, notificationId);
            startActivity(intent);
        });
    }

    /**
     * 初始化头条通知控件
     */
    private void initNotificationView() {
        ScaleLayoutManager build1 = new ScaleLayoutManager.Builder(getContext(), 0)
                .setOrientation(OrientationHelper.VERTICAL)
                .setMaxVisibleItemCount(1)
                .setMoveSpeed(0.1f)
                .build();
        arvNotification.setLayoutManager(build1);
    }

    @OnClick({R.id.iv_scan, R.id.rtsv_message, R.id.rtsv_lesson, R.id.rtsv_live, R.id.rtsv_question_lib, R.id.rtsv_ask_answer,
            R.id.more_lesson_type, R.id.more_lesson_advise, R.id.more_lesson_hot, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                showToast("扫一扫");
                break;
            case R.id.rtsv_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.rtsv_lesson:
                MyApplication.setCurrentLessonTypePosition(0);
                ((MainActivity) getActivity()).toCategoryFragment();
                break;
            case R.id.rtsv_live:
                startActivity(new Intent(getActivity(), LiveActivity.class));
                break;
            case R.id.rtsv_question_lib:
                startActivity(new Intent(getActivity(), QuestionLibActivity.class));
                break;
            case R.id.rtsv_ask_answer:
                startActivity(new Intent(getActivity(), AskAnswerActivity.class));
                break;
            case R.id.more_lesson_type:
                startActivity(new Intent(getActivity(), MoreLessonTypeActivity.class));
                break;
            case R.id.more_lesson_advise:
                startActivity(new Intent(getActivity(), MoreLessonRecActivity.class));
                break;
            case R.id.more_lesson_hot:
                startActivity(new Intent(getActivity(), MoreLessonHotActivity.class));
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateContent(int request, Object content) {
        switch (request) {
            case Net.KEY_CYC_IMG:
                refreshBannerData(content);
                break;
            case Net.KEY_HOT_LESSON:
                refreshHotLessonData(content);
                break;
            case Net.KEY_RECM_LESSON:
                refreshRecmLessonData(content);
                break;
            case Net.KEY_NOTIFICATION:
                refreshNotificationData(content);
                break;
            case Net.KEY_LESSON_TYPE:
                refreshLessonTypeData(content);
                break;
        }
    }

    private void refreshLessonTypeData(Object content) {
        List<LessonTypeResponse.CourseClassListBean> beanList = (List<LessonTypeResponse.CourseClassListBean>) content;
        List<LessonTypeResponse.CourseClassListBean> fixNumBeanList = beanList.size() >= FiX_NUM_LESSON_TYPE ? beanList.subList(0, FiX_NUM_LESSON_TYPE) : beanList;

        lessonTypeList.clear();
        lessonTypeList.addAll(fixNumBeanList);
        lessonTypeAdapter.notifyDataSetChanged();
    }

    private void refreshNotificationData(Object content) {
        List<HomePageDataResponse.TopInformListBean> beanList = (List<HomePageDataResponse.TopInformListBean>) content;
        notificationList.clear();
        notificationList.addAll(beanList);
        notificationAdapter.notifyDataSetChanged();
    }

    private void refreshRecmLessonData(Object content) {
        List<HomePageDataResponse.RecomNetCourseListBean> beanList = (List<HomePageDataResponse.RecomNetCourseListBean>) content;
        List<HomePageDataResponse.RecomNetCourseListBean> fixNumBeanList = beanList.size() >= FiX_NUM_REC_LESSON ? beanList.subList(0, FiX_NUM_REC_LESSON) : beanList;
        recomLessonList.clear();
        recomLessonList.addAll(fixNumBeanList);
        lessonAdviseAdapter.notifyDataSetChanged();
    }

    private void refreshHotLessonData(Object content) {
        List<HomePageDataResponse.HotNetCourseListBean> beanList = (List<HomePageDataResponse.HotNetCourseListBean>) content;
        List<HomePageDataResponse.HotNetCourseListBean> fixNumBeanList = beanList.size() >= FiX_NUM_HOT_LESSON ? beanList.subList(0, FiX_NUM_HOT_LESSON) : beanList;
        hotLessonList.clear();
        hotLessonList.addAll(fixNumBeanList);
        lessonHotAdapter.notifyDataSetChanged();
    }

    private void refreshBannerData(Object content) {
        List<GetHomeCycImgResponse.FirstCycleImgBean> beanList = (List<GetHomeCycImgResponse.FirstCycleImgBean>) content;
        urlList.clear();
        urlList.addAll(beanList);
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (arvBanner != null) arvBanner.pause();
        arvBanner = null;
        bannerAdapter = null;
        System.gc();
    }
}
