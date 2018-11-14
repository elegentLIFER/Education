package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownLoadBean;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.response.lesson.NetCourseInfoResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.VideoEncryptUtil;
import com.sxmh.wt.education.view.CircleImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class LessonWatchActivity extends BaseActivity implements MediaController.OnControllerClick {
    @InjectView(R.id.vv_live)
    VideoView vvLive;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_teacher)
    TextView tvTeacher;
    @InjectView(R.id.vv_live2)
    VideoView vvLive2;
    @InjectView(R.id.tv_play_num)
    TextView tvPlayNum;
    @InjectView(R.id.tv_download_video)
    TextView tvDownloadVideo;
    @InjectView(R.id.tv_collect)
    TextView tvCollect;
    @InjectView(R.id.fl_video)
    FrameLayout flVideo;
    @InjectView(R.id.ll_below)
    LinearLayout llBelow;
    @InjectView(R.id.tv_teacher_info)
    TextView tvTeacherInfo;
    @InjectView(R.id.iv_header)
    CircleImageView ivHeader;
    @InjectView(R.id.tv_lesson_introduce)
    TextView tvLessonIntroduce;
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @InjectView(R.id.fl_video2)
    FrameLayout flVideo2;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;

    public static final String DOWNLOAD_TRANSBEAN = "download_transbean";
    private boolean isPortrait = true;
    private MediaPlayer mediaPlayer;

    private boolean isCollected;
    private NetCourseInfoResponse.NetCourseInfoListBean netCourseInfoListBean;

    private String videoPath;
    private boolean played;
    private DownloadTransbean transbean;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_lesson_watch;
    }

    @Override
    protected void initData() {
        getIntentData();
        net.getNetCourseInfo(transbean.getId());
        tvTitle.setText(transbean.getCourseName());

        Vitamio.initialize(this);

        File file = new File(Constant.APP_PATH);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            if (file1.getName().equals(transbean.getCourseName() + ".mp4")) {
                videoPath = file1.getPath();
                VideoEncryptUtil.encrypt(videoPath);
            }
        }

        if (!TextUtils.isEmpty(videoPath)) {
            vvLive.setVideoPath(videoPath);
            MediaController controller = new MediaController(this, true, flVideo);
            controller.setOnControllerClick(this);
            vvLive.setMediaController(controller);
            controller.setMediaPlayer(vvLive);
            vvLive.start();
            vvLive.setOnPreparedListener((mp) -> {
                mediaPlayer = mp;
            });
            played = true;
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        transbean = (DownloadTransbean) intent.getSerializableExtra(DOWNLOAD_TRANSBEAN);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_NET_COURSE_INFO) {
            NetCourseInfoResponse response = (NetCourseInfoResponse) content;
            List<NetCourseInfoResponse.NetCourseInfoListBean> netCourseInfoList = response.getNetCourseInfoList();
            netCourseInfoListBean = netCourseInfoList.get(0);

            if (!played) {
                String playUrl = netCourseInfoListBean.getPlayUrl();
                if (!TextUtils.isEmpty(playUrl)) {
                    vvLive.setVideoURI(Uri.parse(playUrl));
                    MediaController controller = new MediaController(this, true, flVideo);
                    controller.setOnControllerClick(this);
                    vvLive.setMediaController(controller);
                    controller.setMediaPlayer(vvLive);
                    vvLive.start();
                    vvLive.setOnPreparedListener((mp) -> {
                        mediaPlayer = mp;
                    });
                }
            }
            tvPlayNum.setText(getString(R.string.play_num, netCourseInfoListBean.getLookNum() + ""));
            tvTeacherInfo.setText(netCourseInfoListBean.getTeacherInfo());
            RequestOptions options = new RequestOptions();
            options.error(R.drawable.touxaing).placeholder(R.drawable.touxaing);
            Glide.with(this).load(netCourseInfoListBean.getTeacherPhoto()).apply(options).into(ivHeader);
            tvLessonIntroduce.setText(netCourseInfoListBean.getRemark());
            String teacherName = netCourseInfoListBean.getTeacherName();
            tvTeacher.setText(getString(R.string.teacher, teacherName));
            tvTeacherName.setText(teacherName);
            setCollected(netCourseInfoListBean.isIsCollect());
        } else if (request == Net.REQUEST_COLLECT) {
            setCollected((boolean) content);
        } else if (request == Net.REQUEST_CANCEL_COLLECT) {
            setCollected(!(boolean) content);
        } else if (request == Net.REQUEST_LESSON_DOWNLOAD_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) {
                Intent intent = new Intent(this, MyDownloadActivity.class);
                DownLoadBean downLoadBean = new DownLoadBean(netCourseInfoListBean);
                downLoadBean.setCourseName(transbean.getCourseName());
                downLoadBean.setLitimg(transbean.getImgUrl());
                ArrayList<DownLoadBean> list = new ArrayList<>();
                list.add(downLoadBean);
                intent.putExtra(MyDownloadActivity.DOWNLOAD_BEAN, list);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!TextUtils.isEmpty(videoPath))
            VideoEncryptUtil.encrypt(videoPath);
    }

    private void setCollected(boolean collected) {
        isCollected = collected;
        ivCollect.setImageResource(isCollected ? R.drawable.icon_collection_blue : R.drawable.icon_collection);
    }

    @OnClick({R.id.vv_live, R.id.iv_back, R.id.tv_download_video, R.id.tv_collect, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vv_live:
                break;
            case R.id.iv_back:
                if (isPortrait) finish();
                toPortrait();
                break;
            case R.id.tv_download_video:
                net.downloadVideoPower(netCourseInfoListBean.getId());
                break;
            case R.id.tv_collect:
                collect();
                break;
            case R.id.iv_collect:
                collect();
                break;
        }
    }

    private void collect() {
        if (!isCollected)
            net.doCollect(transbean.getId(), Net.COLLECT_FLAG_TELL);
        else
            net.doCancelCollect(transbean.getId(), Net.COLLECT_FLAG_TELL);
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
        llBelow.setVisibility(View.VISIBLE);
    }

    private void toLandScape() {
        LinearLayout.LayoutParams fl_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        flVideo.setLayoutParams(fl_lp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置全屏
        vvLive.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        isPortrait = false;
        llBelow.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isPortrait) finish();
        toPortrait();
    }
}