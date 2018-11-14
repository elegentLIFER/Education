package com.sxmh.wt.education.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.ask_answer.QuestionContentActivity;
import com.sxmh.wt.education.activity.ask_answer.QuestionListActivity;
import com.sxmh.wt.education.activity.lesson.LessonWatchActivity;
import com.sxmh.wt.education.activity.live.LiveActivity;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.activity.question_lib.PaperListActivity;
import com.sxmh.wt.education.activity.question_lib.QuesAnalyseActivity;
import com.sxmh.wt.education.activity.question_lib.TestResultActivity;
import com.sxmh.wt.education.adapter.search.RvSearchAskAdapter;
import com.sxmh.wt.education.adapter.search.RvSearchAskAnswerAdapter;
import com.sxmh.wt.education.adapter.search.RvSearchLiveAdapter;
import com.sxmh.wt.education.adapter.search.RvSearchTalkAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.SearchResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.tv_search)
    EditText tvSearch;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.rv_talk_question)
    RecyclerView rvTalkQuestion;
    @InjectView(R.id.rv_ask_question)
    RecyclerView rvAskQuestion;
    @InjectView(R.id.rv_live)
    RecyclerView rvLive;
    @InjectView(R.id.rv_ask_answer)
    RecyclerView rvAskAnswer;

    private List<SearchResponse.NetCourseListBean> netCourseListBeanList;
    private List<SearchResponse.PaperQuestionListBean> questionListBeanList;
    private List<SearchResponse.LiveRoomListBean> liveRoomListBeanList;
    private List<SearchResponse.NetProblemListBean> netProblemListBeanList;

    private RvSearchTalkAdapter questionTalkAdapter;
    private RvSearchAskAdapter questionAskAdapter;
    private RvSearchLiveAdapter collectionLiveAdapter;
    private RvSearchAskAnswerAdapter askAnswerAdapter;

    private SearchResponse.LiveRoomListBean liveListBean;
    private SearchResponse.NetCourseListBean netCourseListBean;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        rgGroup.setOnCheckedChangeListener(this);

        initRvTalkQuestion();
        initRvAskQuestion();
        initRvLive();
        initRvAskAnswer();

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyWord = tvSearch.getText().toString();
//                if (!TextUtils.isEmpty(keyWord))
                net.getSearch(keyWord);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRvLive() {
        liveRoomListBeanList = new ArrayList<>();
        collectionLiveAdapter = new RvSearchLiveAdapter(this, liveRoomListBeanList);
        rvLive.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLive.setAdapter(collectionLiveAdapter);
        collectionLiveAdapter.setItemClickListener((position) -> {
            liveListBean = liveRoomListBeanList.get(position);
            net.getLivePower(liveListBean.getId());
        });
    }

    private void initRvAskQuestion() {
        questionListBeanList = new ArrayList<>();
        questionAskAdapter = new RvSearchAskAdapter(this, questionListBeanList);
        rvAskQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAskQuestion.setAdapter(questionAskAdapter);
        questionAskAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(this, QuesAnalyseActivity.class);
            SearchResponse.PaperQuestionListBean bean = questionListBeanList.get(position);
            intent.putExtra(PaperListActivity.PAPER_LIST_ID, bean.getId());
            intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, "");
            intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_ALL_ANALYSE);
            intent.putExtra(TestResultActivity.POSITION, 0);
            startActivity(intent);
        });
    }

    private void initRvTalkQuestion() {
        netCourseListBeanList = new ArrayList<>();
        questionTalkAdapter = new RvSearchTalkAdapter(this, netCourseListBeanList);
        rvTalkQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        questionTalkAdapter.setItemClickListener((position) -> {
            netCourseListBean = netCourseListBeanList.get(position);

            String memberId = User.getInstance().getMemberId();
            if (TextUtils.isEmpty(memberId)) startActivity(new Intent(this, LoginActivity.class));
            else net.getNetCoursePower(netCourseListBean.getId(),memberId);
        });
        rvTalkQuestion.setAdapter(questionTalkAdapter);
    }

    private void initRvAskAnswer() {
        netProblemListBeanList = new ArrayList<>();
        askAnswerAdapter = new RvSearchAskAnswerAdapter(this, netProblemListBeanList);
        rvAskAnswer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        askAnswerAdapter.setItemClickListener((position) -> {
            Intent intent = new Intent(this, LessonWatchActivity.class);
            startActivity(intent);
        });
        rvAskAnswer.setAdapter(askAnswerAdapter);
        askAnswerAdapter.setItemClickListener((position -> {
            Intent intent = new Intent(this, QuestionContentActivity.class);
            intent.putExtra(QuestionListActivity.NET_PROBLEM_CLASS_ID, netProblemListBeanList.get(position).getId());
            startActivity(intent);
        }));
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_SEARCH) {
            SearchResponse response = (SearchResponse) content;
            netCourseListBeanList.clear();
            questionListBeanList.clear();
            liveRoomListBeanList.clear();
            netProblemListBeanList.clear();

            netCourseListBeanList.addAll(response.getNetCourseList());
            questionTalkAdapter.notifyDataSetChanged();

            questionListBeanList.addAll(response.getPaperQuestionList());
            questionAskAdapter.notifyDataSetChanged();

            liveRoomListBeanList.addAll(response.getLiveRoomList());
            collectionLiveAdapter.notifyDataSetChanged();

            netProblemListBeanList.addAll(response.getNetProblemList());
            askAnswerAdapter.notifyDataSetChanged();
        } else if (request == Net.REQUEST_LESSON_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) startLessonWatchActivity();
        } else if (request == Net.REQUEST_LIVE_WATCH_POWER) {
            DoQuesPowerResponse response = (DoQuesPowerResponse) content;
            if (response.isResult()) {
                Intent intent = new Intent(this, RealLiveActivity.class);
                intent.putExtra(LiveActivity.LIVE_ROOM_ID, liveListBean.getId());
                startActivity(intent);
                ToastUtil.newToast(this, getString(R.string.developing));
            }
        }
    }

    private void startLessonWatchActivity() {
        saveRecentWatch();

        Intent intent = new Intent(this, LessonWatchActivity.class);
        DownloadTransbean transbean = new DownloadTransbean(netCourseListBean.getNetCourseName(), netCourseListBean.getId(), "");
        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, transbean);
        startActivity(intent);
    }

    private void saveRecentWatch() {
        Gson gson = new Gson();
        String json = gson.toJson(netCourseListBean);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        String recentWatchString = sharedPreferences.getString(Constant.SP_KEY_RECENT_WATCH, "");
        if (!recentWatchString.contains(netCourseListBean.getId())) {
            String newValue = recentWatchString + Constant.SEPARATER_JSON + json;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Constant.SP_KEY_RECENT_WATCH, newValue);
            edit.commit();
        }
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_talk_question:
                rvTalkQuestion.setVisibility(View.VISIBLE);
                rvAskQuestion.setVisibility(View.GONE);
                rvLive.setVisibility(View.GONE);
                rvAskAnswer.setVisibility(View.GONE);
                net.getMyCollectTalkList();
                break;
            case R.id.rb_ask_question:
                rvTalkQuestion.setVisibility(View.GONE);
                rvAskQuestion.setVisibility(View.VISIBLE);
                rvLive.setVisibility(View.GONE);
                rvAskAnswer.setVisibility(View.GONE);
                net.getMyCollectPaperList();
                break;
            case R.id.rb_live:
                rvTalkQuestion.setVisibility(View.GONE);
                rvAskQuestion.setVisibility(View.GONE);
                rvLive.setVisibility(View.VISIBLE);
                rvAskAnswer.setVisibility(View.GONE);
                net.getMyCollectLiveList();
                break;
            case R.id.rb_ask_answer:
                rvTalkQuestion.setVisibility(View.GONE);
                rvAskQuestion.setVisibility(View.GONE);
                rvLive.setVisibility(View.GONE);
                rvAskAnswer.setVisibility(View.VISIBLE);
                net.getMyCollectLiveList();
                break;
        }
    }
}
