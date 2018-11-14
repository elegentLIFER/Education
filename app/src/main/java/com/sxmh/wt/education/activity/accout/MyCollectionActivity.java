package com.sxmh.wt.education.activity.accout;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.lesson.LessonWatchActivity;
import com.sxmh.wt.education.activity.live.LiveActivity;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.activity.question_lib.PaperListActivity;
import com.sxmh.wt.education.activity.question_lib.QuesAnalyseActivity;
import com.sxmh.wt.education.activity.question_lib.TestResultActivity;
import com.sxmh.wt.education.adapter.CollectionLiveAdapter;
import com.sxmh.wt.education.adapter.QuestionAskAdapter;
import com.sxmh.wt.education.adapter.QuestionTalkAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.response.CollectionLiveListResponse;
import com.sxmh.wt.education.model.response.CollectionPaperListResponse;
import com.sxmh.wt.education.model.response.CollectionTalkListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MyCollectionActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.rv_talk_question)
    RecyclerView rvTalkQuestion;
    @InjectView(R.id.rv_ask_question)
    RecyclerView rvAskQuestion;
    @InjectView(R.id.rv_live)
    RecyclerView rvLive;

    private List<CollectionTalkListResponse.NetCourseListBean> netCourseListBeanList;
    private List<CollectionPaperListResponse.QuestionListBean> questionListBeanList;
    private List<CollectionLiveListResponse.LiveRoomListBean> liveRoomListBeanList;

    private QuestionTalkAdapter questionTalkAdapter;
    private QuestionAskAdapter questionAskAdapter;
    private CollectionLiveAdapter collectionLiveAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        rgGroup.setOnCheckedChangeListener(this);

        initRvTalkQuestion();
        initRvAskQuestion();
        initRvLive();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = rgGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_talk_question:
                net.getMyCollectTalkList();
                break;
            case R.id.rb_ask_question:
                net.getMyCollectPaperList();
                break;
            case R.id.rb_live:
                net.getMyCollectLiveList();
                break;
        }
    }

    private void initRvLive() {
        liveRoomListBeanList = new ArrayList<>();
        collectionLiveAdapter = new CollectionLiveAdapter(this, liveRoomListBeanList);
        rvLive.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLive.setAdapter(collectionLiveAdapter);
        collectionLiveAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(MyCollectionActivity.this, RealLiveActivity.class);
            intent.putExtra(LiveActivity.LIVE_ROOM_ID, liveRoomListBeanList.get(position).getId());
            startActivity(intent);
            ToastUtil.newToast(this, getString(R.string.developing));
        });
    }

    private void initRvAskQuestion() {
        questionListBeanList = new ArrayList<>();
        questionAskAdapter = new QuestionAskAdapter(this, questionListBeanList);
        rvAskQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAskQuestion.setAdapter(questionAskAdapter);
        questionAskAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(MyCollectionActivity.this, QuesAnalyseActivity.class);
            intent.putExtra(PaperListActivity.PAPER_LIST_ID,questionListBeanList.get(position).getId());
            intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, "");
            intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_ALL_ANALYSE);
            intent.putExtra(TestResultActivity.POSITION, 0);
            startActivity(intent);
        });
    }

    private void initRvTalkQuestion() {
        netCourseListBeanList = new ArrayList<>();
        questionTalkAdapter = new QuestionTalkAdapter(this, netCourseListBeanList);
        rvTalkQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        questionTalkAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(MyCollectionActivity.this, LessonWatchActivity.class);
            CollectionTalkListResponse.NetCourseListBean bean = netCourseListBeanList.get(position);
            DownloadTransbean transbean = new DownloadTransbean(bean.getCourseName(), bean.getId(), bean.getLitimg());
            intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
            startActivity(intent);
        });
        rvTalkQuestion.setAdapter(questionTalkAdapter);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_talk_question:
                rvTalkQuestion.setVisibility(View.VISIBLE);
                rvAskQuestion.setVisibility(View.GONE);
                rvLive.setVisibility(View.GONE);
                net.getMyCollectTalkList();
                break;
            case R.id.rb_ask_question:
                rvTalkQuestion.setVisibility(View.GONE);
                rvAskQuestion.setVisibility(View.VISIBLE);
                rvLive.setVisibility(View.GONE);
                net.getMyCollectPaperList();
                break;
            case R.id.rb_live:
                rvTalkQuestion.setVisibility(View.GONE);
                rvAskQuestion.setVisibility(View.GONE);
                rvLive.setVisibility(View.VISIBLE);
                net.getMyCollectLiveList();
                break;
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MY_COLLECTION_LIST_TALK) {
            CollectionTalkListResponse response = (CollectionTalkListResponse) content;
            netCourseListBeanList.clear();
            netCourseListBeanList.addAll(response.getNetCourseList());
            questionTalkAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_MY_COLLECTION_LIST_PAPER) {
            CollectionPaperListResponse response = (CollectionPaperListResponse) content;
            questionListBeanList.clear();
            questionListBeanList.addAll(response.getQuestionList());
            questionAskAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_MY_COLLECTION_LIST_LIVE) {
            CollectionLiveListResponse response = (CollectionLiveListResponse) content;
            liveRoomListBeanList.clear();
            liveRoomListBeanList.addAll(response.getLiveRoomList());
            collectionLiveAdapter.notifyDataSetChanged();
        }
    }
}
