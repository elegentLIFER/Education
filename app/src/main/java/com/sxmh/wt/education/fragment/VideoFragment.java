package com.sxmh.wt.education.fragment;

import android.net.Uri;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseFragment;

import butterknife.InjectView;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoFragment extends BaseFragment {
    @InjectView(R.id.vv_live)
    VideoView vvLive;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {

        vvLive.setVideoURI(Uri.parse("http://video.pinabc.cn/userfiles/files/files2018/18一级建造师/18一建（管理）/18一建管理（精讲班）宿吉南/01、18一建管理精讲第一章（1）（2）-1.mp4"));
        //使用类库提供的MediaController (注意是Vitamio包下的)
        MediaController controller = new MediaController(getContext());
        //双重绑定
        vvLive.setMediaController(controller);
        controller.setMediaPlayer(vvLive);
        //播放视频
        vvLive.start();
    }

    @Override
    public void updateContent(int request, Object content) {
    }
}
