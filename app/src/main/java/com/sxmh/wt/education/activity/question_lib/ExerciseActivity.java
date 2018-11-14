package com.sxmh.wt.education.activity.question_lib;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.Answer;
import com.sxmh.wt.education.model.TransBean;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionIdsResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionInfoResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.LetterStringSwitch;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.BottomOperationView;
import com.sxmh.wt.education.view.QuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;

public class ExerciseActivity extends BaseActivity implements BottomOperationView.OnOptionClickListener, QuestionView.OnOptionSelectListener {
    private static final String TAG = "ExerciseActivity";
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.iv_collect)
    ImageView ivCollect;
    @InjectView(R.id.tv_right)
    TextView tvRight;
    @InjectView(R.id.question_view)
    QuestionView questionView;
    @InjectView(R.id.bottom_operation_view)
    BottomOperationView bottomOperationView;
    @InjectView(R.id.tv_paper_name)
    TextView tvPaperName;
    @InjectView(R.id.tv_current_ques)
    TextView tvCurrentQues;
    @InjectView(R.id.tv_sum_num)
    TextView tvSumNum;

    public static final String SPLIT_IDS = "splitIds";
    public static final String REST_TIME = "rest_time";
    private ArrayList<TransBean> transBeanList;

    private int indexCurQues;
    private int restTime;
    private boolean isCollected;

    private QuestionInfoResponse infoResponse;
    private QuestionIdsResponse idsResponse;

    private String paperListId;
    private PaperCatalogResponse.PaperCatalogListBean paperCatalogListBean;
    private String whichOperate;

    private Timer timer;
    private boolean quesCardClicked;
    private boolean commitClicked;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initData() {
        bottomOperationView.setOnOptionListener(this);
        questionView.setOnOptionSelectListener(this);

        transBeanList = new ArrayList<>();

        getIntentData();
        tvPaperName.setText(paperCatalogListBean.getCatalogName());
        tvCurrentQues.setText(indexCurQues + 1 + "");

        net.getPaperQuestion(paperCatalogListBean.getId(), paperListId, whichOperate);
        questionView.etWenda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String etWenda = questionView.getEtWenda();
                if (!TextUtils.isEmpty(etWenda)) {
                    net.doQuestionLog(transBeanList.get(indexCurQues).getId(), paperListId, paperCatalogListBean.getId(), etWenda,
                            "0", "0", infoResponse.getQueTypeId() + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        paperListId = intent.getStringExtra(PaperListActivity.PAPER_LIST_ID);
        whichOperate = intent.getStringExtra(PaperListActivity.FLAG_WHICH_OPERATE);
        paperCatalogListBean = (PaperCatalogResponse.PaperCatalogListBean) intent.getSerializableExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN);
    }

    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                sureQuit();
                break;
            case R.id.iv_collect:
                String quesId = transBeanList.get(indexCurQues).getId();
                if (!isCollected)
                    net.doCollect(quesId, Net.COLLECT_FLAG_EXECISE);
                else
                    net.doCancelCollect(quesId, Net.COLLECT_FLAG_EXECISE);
                break;
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        switch (request) {
            case Net.REQUEST_QUESTION_IDS:
                idsResponse = (QuestionIdsResponse) content;
                refreshTranBeanList();

                net.getQuestionInfo(transBeanList.get(0).getId(), whichOperate == Constant.FLAG_DO_CONTINUE);
                if (restTime == 0) {
                    restTime = idsResponse.getTestTime() * 60;
                    startCountTime();
                    tvSumNum.setText(idsResponse.getQuestionSum() + "");
                }

                if (commitClicked) {
                    Intent intent = new Intent(this, TestResultActivity.class);
                    intent.putParcelableArrayListExtra(SPLIT_IDS, transBeanList);
                    intent.putExtra(REST_TIME, restTime);
                    intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, paperCatalogListBean);
                    intent.putExtra(PaperListActivity.PAPER_LIST_ID, paperListId);
                    startActivity(intent);
                    finish();
                    return;
                }

                // to question card page if is continue do
                if (Constant.FLAG_DO_CONTINUE.equals(whichOperate) || quesCardClicked) {
                    Intent questionCardActIntent = new Intent(this, QuestionCardActivity.class);
                    questionCardActIntent.putParcelableArrayListExtra(SPLIT_IDS, transBeanList);
                    questionCardActIntent.putExtra(REST_TIME, restTime);
                    questionCardActIntent.putExtra(QuestionCardActivity.SHOW_RIGHT_OR_WRONG, false);
                    startActivityForResult(questionCardActIntent, 1);
                    quesCardClicked = false;
                }
                break;
            case Net.REQUEST_QUESTION_INFO:
                infoResponse = (QuestionInfoResponse) content;
                setCollected(infoResponse.isIsCollect());
                questionView.setContent(infoResponse);
                String questionAnswer = infoResponse.getQuestionAnswer();
                if (!TextUtils.isEmpty(questionAnswer)) {
                    String[] split = questionAnswer.split("");
                    List<Boolean> rightAnswer = NUtil.getANewOptionList();
                    int length = split.length;
                    for (int i = 0; i < length; i++) {
                        int numByLetter = LetterStringSwitch.getNumByLetter(split[i]);
                        if (numByLetter != -1) {
                            rightAnswer.set(numByLetter, true);
                        }
                    }
                }
                break;
            case Net.REQUEST_COLLECT:
                setCollected((boolean) content);
                break;
            case Net.REQUEST_CANCEL_COLLECT:
                setCollected(!(boolean) content);
                break;
        }
    }

    private void refreshTranBeanList() {
        transBeanList.clear();
        String beforeQueidString = idsResponse.getBeforeQueids();
        String idString = idsResponse.getIdString();
        if (TextUtils.isEmpty(idString)) return;
        String[] splitIds = idString.split(Constant.SEPARATER_COMMA);
        if (TextUtils.isEmpty(beforeQueidString)) {
            int len = splitIds.length;
            for (int i = 0; i < len; i++) {
                String[] split = splitIds[i].split(Constant.SEPARATER_ID);

                TransBean transBean = new TransBean();
                transBean.setId(split[0]);
                transBean.setQuesType(split[1]);
                transBeanList.add(transBean);
            }
        } else {
            String[] beforeQueids = beforeQueidString.split(Constant.SEPARATER_COMMA);

            int len = splitIds.length;
            for (int i = 0; i < len; i++) {
                String[] split = splitIds[i].split(Constant.SEPARATER_ID);

                TransBean transBean = new TransBean();
                transBean.setId(split[0]);
                transBean.setQuesType(split[1]);

                int beforeIdsLen = beforeQueids.length;
                for (int j = 0; j < beforeIdsLen; j++) {
                    String beforeQueid = beforeQueids[j];
                    if (beforeQueid.contains(split[0])) {
                        transBean.setHasDone(true);
                        String[] split1 = beforeQueid.split(Constant.SEPARATER_ID);
                        String[] split2 = split1[1].split(Constant.SEPARATER_STAR);
                        transBean.setCorrect("0".equals(split2[0]));
                        transBean.setScore(split2[1]);
                    }
                }
                transBeanList.add(transBean);
            }
        }
    }

    private void setCollected(boolean collected) {
        isCollected = collected;
        ivCollect.setImageResource(getCollectImgId());
    }

    private int getCollectImgId() {
        return isCollected ? R.drawable.icon_collection_blue : R.drawable.icon_collection;
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
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onBackPressed() {
        sureQuit();
    }

    private void sureQuit() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("确定退出答题？")
                .setPositiveButton("确定", (DialogInterface dialog, int which) -> finish())
                .setNegativeButton("取消", null).create();
        alertDialog.show();
    }

    @Override
    public void onPreQuestionClick() {
        if (indexCurQues > 0) {
            indexCurQues--;
            net.getQuestionInfo(transBeanList.get(indexCurQues).getId(), true);
            tvCurrentQues.setText(indexCurQues + 1 + "");
        } else
            ToastUtil.newToast(this, getString(R.string.has_the_first));
    }

    @Override
    public void onQuestionCardClick() {
        quesCardClicked = true;
        net.getPaperQuestion(paperCatalogListBean.getId(), paperListId, Constant.FLAG_DO_CONTINUE);
    }

    @Override
    public void onCommitClick() {
        commitClicked = true;
        net.getPaperQuestion(paperCatalogListBean.getId(), paperListId, Constant.FLAG_DO_CONTINUE);
    }

    @Override
    public void onNextQuestionClick() {
        if (indexCurQues < transBeanList.size() - 1) {
            indexCurQues++;
            net.getQuestionInfo(transBeanList.get(indexCurQues).getId(), true);
            tvCurrentQues.setText(indexCurQues + 1 + "");
        } else {
            ToastUtil.newToast(this, getString(R.string.has_the_last));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == QuestionCardActivity.REQUEST_CODE_POSITION) {
            indexCurQues = data.getIntExtra(QuestionCardActivity.POSITION, 0) - 1;
            net.getQuestionInfo(transBeanList.get(indexCurQues).getId(), true);
            Log.e("hhhhhhhhhhh   ", transBeanList.get(indexCurQues).getId() + "");
            tvCurrentQues.setText(indexCurQues + 1 + "");
        }
    }

    @Override
    public void OnOptionSelect(int position, boolean isSelected) {
        Answer answer = questionView.getAnswer();
        List<Boolean> selectionList = answer.getSelectionList();
        int size = selectionList.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (selectionList.get(i)) {
                sb.append(LetterStringSwitch.getLetterByNum(i));
            }
        }

        String answerString = sb.toString();
        String answerCorrect = infoResponse.getQuestionAnswer();
        boolean isCorrect = answerCorrect.equals(answerString);

        TransBean transBean = transBeanList.get(indexCurQues);
        transBean.setCorrect(isCorrect);
        net.doQuestionLog(transBean.getId(), paperListId, paperCatalogListBean.getId(), answerString,
                isCorrect ? "0" : "1", isCorrect ? infoResponse.getCquestionScore() + "" : "0", infoResponse.getQueTypeId() + "");
    }
}
