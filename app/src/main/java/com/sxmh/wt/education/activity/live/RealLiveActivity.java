package com.sxmh.wt.education.activity.live;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.CommonFragmentVpAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.live.BeforeLiveFragment;
import com.sxmh.wt.education.fragment.live.ChatFragment;
import com.sxmh.wt.education.model.response.CollectResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomInfoResponse;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class RealLiveActivity extends BaseActivity implements MediaController.OnControllerClick {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tb_tab)
    TabLayout tbTab;
    @InjectView(R.id.vp_content)
    ViewPager vpContent;
    @InjectView(R.id.vv_live)
    VideoView vvLive;
    @InjectView(R.id.vv_teacher)
    VideoView vvTeacher;
    @InjectView(R.id.iv_playing)
    ImageView ivPlaying;
    @InjectView(R.id.tv_room_name)
    TextView tvRoomName;
    @InjectView(R.id.tv_teacher)
    TextView tvTeacher;
    @InjectView(R.id.iv_header)
    ImageView ivHeader;
    @InjectView(R.id.tv_teacher_info)
    TextView tvTeacherInfo;
    @InjectView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @InjectView(R.id.fl_video)
    FrameLayout flVideo;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;
    @InjectView(R.id.iv_appoint)
    ImageView ivAppoint;
    @InjectView(R.id.tv_collect)
    TextView tvCollect;
    @InjectView(R.id.tv_appoint)
    TextView tvAppoint;

    public static final String COURSE_CLASS_ID = "course_class_id";
    public static final String LIVE_ROOM_INFO_RESPONSE = "live_room_info_response";

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private CommonFragmentVpAdapter adapter;
    private AnimationDrawable drawable;

    private MediaPlayer mediaPlayer;
    private boolean isPortrait = true;

    private boolean isCollected;
    private boolean isAppointed;
    private String liveRoomId;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_real_live;
    }

    @Override
    protected void initData() {
        liveRoomId = getIntent().getStringExtra(LiveActivity.LIVE_ROOM_ID);
        net.getLiveRoomInfo(liveRoomId);

        if (!LibsChecker.checkVitamioLibs(this)) return;
        drawable = (AnimationDrawable) ivPlaying.getDrawable();
        drawable.start();
    }

    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_appoint, R.id.tv_collect, R.id.tv_appoint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (isPortrait) finish();
                toPortrait();
                break;
            case R.id.iv_collect:
                collect();
                break;
            case R.id.iv_appoint:
                appoint();
                break;
            case R.id.tv_collect:
                collect();
                break;
            case R.id.tv_appoint:
                appoint();
                break;
        }
    }

    private void appoint() {
        if (!isAppointed)
            net.doAppointLive(liveRoomId);
        else
            net.doCancelAppointLive(liveRoomId);
    }

    private void collect() {
        if (!isCollected)
            net.doCollect(liveRoomId, Net.COLLECT_FLAG_LIVE);
        else
            net.doCancelCollect(liveRoomId, Net.COLLECT_FLAG_LIVE);
    }

    private MediaController controller;

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_LIVE_INFO) {
            LiveRoomInfoResponse response = (LiveRoomInfoResponse) content;
            tvRoomName.setText(response.getRoomName());
            setCollected(response.isIsCollect());
            setAppointed(response.isIsAppoint());

            String teacher = response.getTeacher();
            tvTeacher.setText(getString(R.string.teacher, teacher));
            tvTeacherInfo.setText(response.getTeacherIntro());
            tvTeacherName.setText(teacher);
            Glide.with(this).load(response.getTeacherImage()).into(ivHeader);

//            vvLive.setVideoPath(response.getScreenUrl());
//            controller = new MediaController(this, true, flVideo);
//            controller.setOnControllerClick(this);
//            vvLive.setMediaController(controller);
//            vvLive.requestFocus();
//            vvLive.start();
//            vvLive.setOnPreparedListener(mediaPlayer -> {
//                this.mediaPlayer = mediaPlayer;
//                mediaPlayer.setPlaybackSpeed(1.0f);
//                mediaPlayer.setLooping(true);
//            });
//
//            controller.showSpeedAjust(false);
            tabAndViewPagerPrepare(response.getCourseClassId(), response);
        } else if (request == Net.REQUEST_COLLECT) {
            setCollected((boolean) content);
        } else if (request == Net.REQUEST_CANCEL_COLLECT) {
            setCollected(!(boolean) content);
        } else if (request == Net.REQUEST_APPONINT) {
            CollectResponse response = (CollectResponse) content;
            setAppointed(response.isResult());
        } else if (request == Net.REQUEST_CANCEL_APPONINT) {
            CollectResponse response = (CollectResponse) content;
            setAppointed(!response.isResult());
        }
    }

    private void setCollected(boolean collected) {
        isCollected = collected;
        ivCollect.setImageResource(isCollected ? R.drawable.icon_collection_blue : R.drawable.icon_collection);
    }

    private void setAppointed(boolean appointed) {
        isAppointed = appointed;
        ivAppoint.setImageResource(appointed ? R.drawable.zhibo_yuyue_y : R.drawable.zhibo_yuyue);
    }

    private void tabAndViewPagerPrepare(String courseClassId, LiveRoomInfoResponse response) {
        fragmentList = new ArrayList<>();
        BeforeLiveFragment beforeLiveFragment = new BeforeLiveFragment();
        Bundle args = new Bundle();
        args.putString(COURSE_CLASS_ID, courseClassId);
        beforeLiveFragment.setArguments(args);
        fragmentList.add(beforeLiveFragment);

        ChatFragment chatFragment = new ChatFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(LIVE_ROOM_INFO_RESPONSE, response);
        chatFragment.setArguments(arguments);
        fragmentList.add(chatFragment);
        titleList = new ArrayList<>();
        titleList.add(getString(R.string.live_lesson));
        titleList.add(getString(R.string.chat_area));
        adapter = new CommonFragmentVpAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vpContent.setAdapter(adapter);

        tbTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tbTab.setupWithViewPager(vpContent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (drawable.isRunning()) {
//            drawable.stop();
//            drawable = null;
//        }
    }

    @Override
    public void OnFullScreenClick() {
        if (isPortrait) {
            toLandScape();
            return;
        }
        toPortrait();
    }

    @Override
    public void OnSpeedSelect(float speed) {
        if (mediaPlayer != null) {
            mediaPlayer.setPlaybackSpeed(speed);
        }
    }

    private void toPortrait() {
        LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, NUtil.dp2px(191));
        flVideo.setLayoutParams(fl_lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isPortrait = true;
//        llBelow.setVisibility(View.VISIBLE);
    }

    private void toLandScape() {
        LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        flVideo.setLayoutParams(fl_lp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置全屏
        vvLive.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        isPortrait = false;
//        llBelow.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isPortrait) finish();
        toPortrait();
    }
}