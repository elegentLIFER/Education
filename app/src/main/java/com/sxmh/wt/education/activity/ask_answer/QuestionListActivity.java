package com.sxmh.wt.education.activity.ask_answer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.RvQuestionListAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.AskAnswerFragment;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionListActivity extends BaseActivity implements TitleView.OnTitleViewClickListener,
        RvQuestionListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "QuestionListActivity";
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_ask_answer)
    RecyclerView rvAskAnswer;
    @InjectView(R.id.tv_i_ask)
    TextView tvIAsk;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.et_search)
    EditText etSearch;

    private List<NetProblemListResponse.NetProblemListBean> beanList;
    private RvQuestionListAdapter questionListAdapter;
    private String netProblemClassId;
    public static final String NET_PROBLEM_CLASS_ID = "net_problem_id";

    private int currentPage = 0;
    private boolean isRefresh;
    private String classId;
    private boolean isMy;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_question_list;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        getIntentData();
        getNetProblemList();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorMainBlue, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        initQuesList();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isRefresh = true;
                net.getNetProblemList(classId, "0", "10", User.getInstance().getMemberId(), etSearch.getText().toString(), "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        classId = intent.getStringExtra(Constant.CLASS_ID);
        netProblemClassId = intent.getStringExtra(AskAnswerFragment.NET_PROBLEM_CLASS_ID);
        isMy = intent.getBooleanExtra(Constant.IS_MY, false);
    }

    private void initQuesList() {
        beanList = new ArrayList<>();
        questionListAdapter = new RvQuestionListAdapter(this, beanList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_bottom));
        rvAskAnswer.addItemDecoration(dividerItemDecoration);
        rvAskAnswer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAskAnswer.setAdapter(questionListAdapter);
        questionListAdapter.setOnItemClickListener(this);
        rvAskAnswer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = NUtil.findMax(lastPositions);
                    }
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        isRefresh = false;
                        currentPage++;
                        // 滑动到底了
                        getNetProblemList();
                    }
                }
            }
        });
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
        if (request == Net.REQUEST_NET_PROBLEM_LIST) {
            NetProblemListResponse response = (NetProblemListResponse) content;
            boolean isExceedViewPageNum = response.isIsExceedViewPageNum();
            if (isExceedViewPageNum) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(100, 10, 100, 10);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.BLACK);
                textView.setText("您好，您已达到问题查看页数上限，请联系客服，开通更多问题页数查看权限");
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setView(textView)
                        .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                        }).create().show();
                return;
            }

            if (isRefresh) beanList.clear();
            List<NetProblemListResponse.NetProblemListBean> netProblemList = response.getNetProblemList();
            if (netProblemList == null) {
                questionListAdapter.setDataFinished(false);
                questionListAdapter.notifyDataSetChanged();
                return;
            }
            beanList.addAll(netProblemList);
            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            questionListAdapter.notifyDataSetChanged();
            ToastUtil.newToast(this, "刷新成功");

            if (currentPage >= beanList.size() / 10) questionListAdapter.setDataFinished(true);
            else questionListAdapter.setDataFinished(false);
        }
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, QuestionContentActivity.class);
        intent.putExtra(NET_PROBLEM_CLASS_ID, beanList.get(position).getId());
        startActivity(intent);
    }

    @OnClick(R.id.tv_i_ask)
    public void onViewClicked() {
        Intent intent = new Intent(QuestionListActivity.this, AskActivity.class);
        intent.putExtra(Constant.CLASS_ID, classId);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        currentPage = 0;
        getNetProblemList();
    }

    private void getNetProblemList() {
        String memberId = User.getInstance().getMemberId();
        if (isMy)
            net.getNetProblemList(netProblemClassId, currentPage + "", "10", memberId, "", "0");
        else
            net.getNetProblemList(netProblemClassId, currentPage + "", "10", memberId, "", "");
    }
}
