package com.sxmh.wt.education.activity.question_lib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sxmh.wt.education.BanScrollGridLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.RvQuesCardViewAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.TransBean;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.view.QuestionCardView;
import com.sxmh.wt.education.view.TestResultView;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestResultActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.test_result_view)
    TestResultView testResultView;
    @InjectView(R.id.tv_analyse_all)
    TextView tvAnalyseAll;
    @InjectView(R.id.tv_analyse_wrongs)
    TextView tvAnalyseWrongs;
    @InjectView(R.id.tv_execise_again)
    TextView tvExeciseAgain;
    @InjectView(R.id.qcv_single_opt)
    QuestionCardView qcvSingleOpt;
    @InjectView(R.id.qcv_multi_opt)
    QuestionCardView qcvMultiOpt;
    @InjectView(R.id.qcv_wenda)
    QuestionCardView qcvWenda;

    private List<Integer> singleOptQuesList;
    private List<Integer> multiOptQuesList;
    private List<Integer> analyseQuesList;

    private ArrayList<TransBean> transBeanList;
    private String paperListId;

    private PaperCatalogResponse.PaperCatalogListBean paperCatalogListBean;

    public static final int REQUEST_CODE_POSITION = 1;
    public static final String POSITION = "position";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_test_result;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        singleOptQuesList = new ArrayList<>();
        multiOptQuesList = new ArrayList<>();
        analyseQuesList = new ArrayList<>();

        Intent intent = getIntent();
        transBeanList = intent.getParcelableArrayListExtra(ExerciseActivity.SPLIT_IDS);
        int restTime = intent.getIntExtra(ExerciseActivity.REST_TIME, 0);
        paperCatalogListBean = (PaperCatalogResponse.PaperCatalogListBean) intent.getSerializableExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN);
        paperListId = intent.getStringExtra(PaperListActivity.PAPER_LIST_ID);

        testResultView.setTitle(paperCatalogListBean.getCatalogName());
        testResultView.setTime(NUtil.secToTime(restTime));

        int totalScore = 0;
        int size = transBeanList.size();
        for (int i = 0; i < size; i++) {
            String scoreString = transBeanList.get(i).getScore();
            if (TextUtils.isEmpty(scoreString)) continue;
            totalScore += Integer.valueOf(scoreString);
        }
        testResultView.setScore(totalScore + "");

        setData();
        showSingleOptQues();
        showMultiOptQues();
        showAnalyseQues();
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @OnClick({R.id.tv_analyse_all, R.id.tv_analyse_wrongs, R.id.tv_execise_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_analyse_all:
                toQuesAnalyseActivity(Constant.FLAG_ALL_ANALYSE);
                break;
            case R.id.tv_analyse_wrongs:
                toQuesAnalyseActivity(Constant.FLAG_SEE_WRONGS);
                break;
            case R.id.tv_execise_again:
                finish();
                startExeciseActivity();
                break;
        }
    }

    private void startExeciseActivity() {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean);
        intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
        intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_DO_AGAIN);
        startActivity(intent);
    }

    private void toQuesAnalyseActivity(String flag) {
        Intent intent = new Intent(this, QuesAnalyseActivity.class);
        intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean.getId());
        intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
        intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, flag);
        startActivity(intent);
    }

    private void showMultiOptQues() {
        if (multiOptQuesList.size() != 0) {
            qcvMultiOpt.setTvTitle(getString(R.string.ques_multi));
            RecyclerView multiOptRvContent = qcvMultiOpt.getRvContent();
            RvQuesCardViewAdapter adapter = new RvQuesCardViewAdapter(this, multiOptQuesList, transBeanList);
            adapter.setShowCorrectOrNot(true);
            multiOptRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            multiOptRvContent.setAdapter(adapter);
            adapter.setItemClickListener(position -> {
                Intent intent = new Intent(TestResultActivity.this, QuesAnalyseActivity.class);
                intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
                intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean.getId());
                intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_ALL_ANALYSE);
                intent.putExtra(POSITION, position + singleOptQuesList.size());
                startActivity(intent);
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
            adapter.setShowCorrectOrNot(true);
            singleOptRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            singleOptRvContent.setAdapter(adapter);
            adapter.setItemClickListener(position -> {
                Intent intent = new Intent(TestResultActivity.this, QuesAnalyseActivity.class);
                intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
                intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean.getId());
                intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_ALL_ANALYSE);
                intent.putExtra(POSITION, position);
                startActivity(intent);
            });
        } else {
            qcvSingleOpt.setVisibility(View.GONE);
        }
    }

    private void showAnalyseQues() {
        if (analyseQuesList.size() != 0) {
            qcvWenda.setTvTitle(getString(R.string.ques_ana));
            RecyclerView analyseRvContent = qcvWenda.getRvContent();
            RvQuesCardViewAdapter adapter = new RvQuesCardViewAdapter(this, analyseQuesList, transBeanList);
            analyseRvContent.setLayoutManager(new BanScrollGridLayoutManager(this, 7));
            analyseRvContent.setAdapter(adapter);
            adapter.setItemClickListener((position -> {
                Intent intent = new Intent(TestResultActivity.this, QuesAnalyseActivity.class);
                intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
                intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean.getId());
                intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_ALL_ANALYSE);
                intent.putExtra(POSITION, position + singleOptQuesList.size() + multiOptQuesList.size());
                startActivity(intent);
            }));
        } else {
            qcvWenda.setVisibility(View.GONE);
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
}
