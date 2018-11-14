package com.sxmh.wt.education.activity.question_lib;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxmh.wt.education.BanScrollGridLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.RvQuesCardViewAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.TransBean;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.view.QuestionCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionCardActivity extends BaseActivity {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.tv_right)
    TextView tvRight;
    @InjectView(R.id.qcv_single_opt)
    QuestionCardView qcvSingleOpt;
    @InjectView(R.id.qcv_multi_opt)
    QuestionCardView qcvMultiOpt;
    @InjectView(R.id.qcv_analyse)
    QuestionCardView qcvAnalyse;

    private List<Integer> singleOptQuesList;
    private List<Integer> multiOptQuesList;
    private List<Integer> analyseQuesList;

    public static final int REQUEST_CODE_POSITION = 1;
    public static final String POSITION = "position";
    public static final String SHOW_RIGHT_OR_WRONG = "show_right_or_wrong";

    private int restTime;
    private Timer timer;
    private ArrayList<TransBean> transBeanList;
    private boolean showRightOrWrong;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_question_card;
    }

    @Override
    protected void initData() {
        initQuesList();
        getIntentData();
        if (restTime != 0) startCountTime();
        else tvRight.setVisibility(View.GONE);
        setData();

        showSingleOptQues();
        showMultiOptQues();
        showAnalyseQues();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        transBeanList = intent.getParcelableArrayListExtra(ExerciseActivity.SPLIT_IDS);
        restTime = intent.getIntExtra(ExerciseActivity.REST_TIME, 0);
        showRightOrWrong = intent.getBooleanExtra(SHOW_RIGHT_OR_WRONG, false);
    }

    private void initQuesList() {
        singleOptQuesList = new ArrayList<>();
        multiOptQuesList = new ArrayList<>();
        analyseQuesList = new ArrayList<>();
    }

    private void showAnalyseQues() {
        if (analyseQuesList.size() != 0) {
            qcvAnalyse.setTvTitle(getString(R.string.ques_ana));
            RecyclerView analyseRvContent = qcvAnalyse.getRvContent();
            RvQuesCardViewAdapter adapter = new RvQuesCardViewAdapter(this, analyseQuesList, transBeanList);
            analyseRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            analyseRvContent.setAdapter(adapter);
            adapter.setItemClickListener((position -> {
                Intent intent = new Intent();
                intent.putExtra(POSITION, analyseQuesList.get(position));
                setResult(REQUEST_CODE_POSITION, intent);
                finish();
            }));
        } else {
            qcvAnalyse.setVisibility(View.GONE);
        }
    }

    private void showMultiOptQues() {
        if (multiOptQuesList.size() != 0) {
            qcvMultiOpt.setTvTitle(getString(R.string.ques_multi));
            RecyclerView multiOptRvContent = qcvMultiOpt.getRvContent();
            RvQuesCardViewAdapter adapter = new RvQuesCardViewAdapter(this, multiOptQuesList, transBeanList);
            adapter.setShowCorrectOrNot(showRightOrWrong);
            multiOptRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            multiOptRvContent.setAdapter(adapter);
            adapter.setItemClickListener(position -> {
                Intent intent = new Intent();
                intent.putExtra(POSITION, multiOptQuesList.get(position));
                setResult(REQUEST_CODE_POSITION, intent);
                finish();
            });
        } else {
            qcvMultiOpt.setVisibility(View.GONE);
        }
    }

    private void showSingleOptQues() {
        if (singleOptQuesList.size() != 0) {
            qcvSingleOpt.setTvTitle(getString(R.string.ques_single));
            RecyclerView singleOptRvContent = qcvSingleOpt.getRvContent();
            RvQuesCardViewAdapter adapter = new RvQuesCardViewAdapter(this, singleOptQuesList, transBeanList);
            adapter.setShowCorrectOrNot(showRightOrWrong);
            singleOptRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            singleOptRvContent.setAdapter(adapter);
            adapter.setItemClickListener(position -> {
                Intent intent = new Intent();
                intent.putExtra(POSITION, singleOptQuesList.get(position));
                setResult(REQUEST_CODE_POSITION, intent);
                finish();
            });
        } else {
            qcvSingleOpt.setVisibility(View.GONE);
        }
    }

    private void setData() {
        int len = transBeanList.size();
        for (int i = 0; i < len; i++) {
            Integer type = Integer.valueOf(transBeanList.get(i).getQuesType());
            switch (type) {
                case Constant.TYPE_SINGLE:
                    singleOptQuesList.add(i + 1);
                    break;
                case Constant.TYPE_MULTIPLE:
                    multiOptQuesList.add(i + 1);
                    break;
                case Constant.TYPE_ANALYSE:
                    analyseQuesList.add(i + 1);
                    break;
            }
        }
    }

    private void startCountTime() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (restTime > 0) {
                        runOnUiThread(() -> tvRight.setText(NUtil.secToTime(restTime)));
                        restTime--;
                    }
                }
            }, 0, 1000);
        }
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
