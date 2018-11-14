package com.sxmh.wt.education.activity.live;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.BanScrollGridLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.ArvFuncBannerAdapter;
import com.sxmh.wt.education.adapter.CommonFragmentVpAdapter;
import com.sxmh.wt.education.adapter.live.LessonLiveHotAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.RecyclerViewFragment;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.AutoFitHViewPager;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class LiveActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.vp_engineer_type)
    AutoFitHViewPager vpEngineerType;
    @InjectView(R.id.rv_live_hot)
    RecyclerView rvLiveHot;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.rv_auto_play)
    AutoPlayRecyclerView rvAutoPlay;

    public static final String COURSE_CLASS_ID = "courseClassId";
    public static final String LIVE_ROOM_ID = "live_room_id";
    private List<LiveRoomListResponse.HotLiveListBean> hotLiveListBeanList;
    private LessonLiveHotAdapter hotAdapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private CommonFragmentVpAdapter fragmentVpAdapter;

    private List<GetCycImgResponse.CycleImgListBean> urlList;
    private ArvFuncBannerAdapter bannerAdapter;

    private LiveRoomListResponse.HotLiveListBean hotLiveListBean;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        net.getLiveRoomList("");
        net.getCourseClassify();

        urlList = new ArrayList<>();
        bannerAdapter = new ArvFuncBannerAdapter(this, urlList);
        ScaleLayoutManager build = new ScaleLayoutManager.Builder(this, 0)
                .setOrientation(OrientationHelper.HORIZONTAL)
                .setMinScale(1)
                .setMaxVisibleItemCount(1)
                .build();
        rvAutoPlay.setLayoutManager(build);
        rvAutoPlay.setAdapter(bannerAdapter);

        // TODO: 2018/9/30 0030  id 写死了
        net.getFunctionCycImg(Constant.BANNER_LIVE, "ff8080815b96bc5e015b9bd171800104");

        initLiveHot();
        tabAndViewPagerPrepare();
    }

    private void initLiveHot() {
        hotLiveListBeanList = new ArrayList<>();
        hotAdapter = new LessonLiveHotAdapter(this, hotLiveListBeanList);
        rvLiveHot.setLayoutManager(new BanScrollGridLayoutManager(this, 2));
        rvLiveHot.setAdapter(hotAdapter);
        hotAdapter.setItemClickListener((position) -> {
            hotLiveListBean = hotLiveListBeanList.get(position);
            net.getLivePower(hotLiveListBean.getId());
        });
    }

    private void tabAndViewPagerPrepare() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        fragmentVpAdapter = new CommonFragmentVpAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vpEngineerType.setAdapter(fragmentVpAdapter);

        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlTab.setTabTextColors(getResources().getColor(R.color.colorTextDKGray), getResources().getColor(R.color.colorMainBlue));
        tlTab.setupWithViewPager(vpEngineerType);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_LIVE_LIST) {
            LiveRoomListResponse response = (LiveRoomListResponse) content;
            hotLiveListBeanList.clear();
            List<LiveRoomListResponse.HotLiveListBean> hotLiveList = response.getHotLiveList();
            hotLiveListBeanList.addAll(hotLiveList.size() > 3 ? hotLiveList.subList(0, 3) : hotLiveList);
            hotAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_LIVE_INFO) {
        } else if (request == Net.KEY_LESSON_TYPE) {
            List<LessonTypeResponse.CourseClassListBean> listBeans = (List<LessonTypeResponse.CourseClassListBean>) content;
            titleList.clear();
            fragmentList.clear();
            for (int i = 0; i < listBeans.size(); i++) {
                RecyclerViewFragment fragment = new RecyclerViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString(COURSE_CLASS_ID, listBeans.get(i).getId());
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
                titleList.add(listBeans.get(i).getCourseClassName());
            }
            fragmentVpAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_BANNER) {
            urlList.clear();
            urlList.addAll((List<GetCycImgResponse.CycleImgListBean>) content);
            bannerAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_LIVE_WATCH_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) {
                Intent intent = new Intent(LiveActivity.this, RealLiveActivity.class);
                intent.putExtra(LIVE_ROOM_ID, hotLiveListBean.getId());
                startActivity(intent);
                ToastUtil.newToast(this, getString(R.string.developing));
            }
        }
    }

    @OnClick(R.id.tv_more)
    public void onViewClicked() {
        ToastUtil.newToast(LiveActivity.this, "更多");
    }
}