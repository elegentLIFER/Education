package com.sxmh.wt.education.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.RvQuestionOptionItemAdapter;
import com.sxmh.wt.education.model.Answer;
import com.sxmh.wt.education.model.response.questionlib.QuestionInfoResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.LetterStringSwitch;
import com.sxmh.wt.education.util.NUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionView extends ScrollView implements RvQuestionOptionItemAdapter.OnOptionSelectListener {
    private static final String TAG = "QuestionView";
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_question)
    TextView tvQuestion;
    @InjectView(R.id.rv_option)
    RecyclerView rvOption;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.ll_exercise_area)
    LinearLayout llExerciseArea;
    @InjectView(R.id.tv_your_answer)
    TextView tvYourAnswer;
    @InjectView(R.id.web_analyse)
    WebView webAnalyse;
    @InjectView(R.id.ll_analyse)
    LinearLayout llAnalyse;
    @InjectView(R.id.tv_right_answer)
    TextView tvRightAnswer;
    @InjectView(R.id.webview_question_problems)
    WebView webViewQuestionProblems;
    @InjectView(R.id.et_wenda)
    public EditText etWenda;

    private QuestionInfoResponse response;
    private List<String> optionList;
    private RvQuestionOptionItemAdapter optionItemAdapter;

    private List<Boolean> selectionList;
    private OnOptionSelectListener listener;
    private boolean analyseStatus;

    public QuestionView(@NonNull Context context) {
        super(context);
        initWork();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_question, this);
        ButterKnife.inject(this, this);

        initSelectionList();
        initOption();
    }

    private void initOption() {
        optionList = new ArrayList<>();
        optionItemAdapter = new RvQuestionOptionItemAdapter(getContext(), optionList, selectionList);
        rvOption.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvOption.setAdapter(optionItemAdapter);
        optionItemAdapter.setOnOptionSelectListener(this);
    }

    private void initSelectionList() {
        selectionList = new ArrayList<>();
        selectionList.add(false);
        selectionList.add(false);
        selectionList.add(false);
        selectionList.add(false);
        selectionList.add(false);
        selectionList.add(false);
    }

    public void setContent(QuestionInfoResponse response) {
        this.response = response;
        selecListToInit();
        showQuesContent();
    }

    private void selecListToInit() {
        int size = selectionList.size();
        for (int i = 0; i < size; i++) {
            selectionList.set(i, false);
        }
    }

    private void showQuesContent() {
        webview.loadDataWithBaseURL(null, response.getQuestionTitle(), "text/html", "UTF-8", null);
        getOptionContent();
        changeUiByQuesType();

        String beforeResult = response.getBeforeResult();
        if (!TextUtils.isEmpty(beforeResult)) {
            String[] selectedOptions = beforeResult.split("");
            int len = selectedOptions.length;
            for (int i = 0; i < len; i++) {
                int numByLetter = LetterStringSwitch.getNumByLetter(selectedOptions[i]);
                if (numByLetter != -1) {
                    selectionList.set(numByLetter, true);
                    optionItemAdapter.notifyDataSetChanged();
                }
            }
        }

        tvRightAnswer.setText(getContext().getString(R.string.right_answer, response.getQuestionAnswer()));
        String yourAnswer = getContext().getString(R.string.your_answer, TextUtils.isEmpty(beforeResult) ? "" : beforeResult);
        tvYourAnswer.setText(yourAnswer);
        webAnalyse.loadDataWithBaseURL(null, getContext().getString(R.string.analyse, response.getQuestionAnalysis()), "text/html", "UTF-8", null);
        webViewQuestionProblems.loadDataWithBaseURL(null, response.getQuestionProblem(), "text/html", "UTF-8", null);
        etWenda.setText(response.getBeforeResult());
    }

    private void getOptionContent() {
        optionList.clear();
        String a = response.getA();
        if (!TextUtils.isEmpty(a)) optionList.add(a);

        String b = response.getB();
        if (!TextUtils.isEmpty(b)) optionList.add(b);

        String c = response.getC();
        if (!TextUtils.isEmpty(c)) optionList.add(c);

        String d = response.getD();
        if (!TextUtils.isEmpty(d)) optionList.add(d);

        String e = response.getE();
        if (!TextUtils.isEmpty(e)) optionList.add(e);

        String f = response.getF();
        if (!TextUtils.isEmpty(f)) optionList.add(f);
    }

    private void changeUiByQuesType() {
        switch (response.getQueTypeId()) {
            case Constant.TYPE_SINGLE:
                tvType.setText(NUtil.getString(R.string.ques_single_option));
                rvOption.setVisibility(VISIBLE);
                llExerciseArea.setVisibility(GONE);
                break;
            case Constant.TYPE_MULTIPLE:
                tvType.setText(NUtil.getString(R.string.ques_multi_option));
                rvOption.setVisibility(VISIBLE);
                llExerciseArea.setVisibility(GONE);
                break;
            case Constant.TYPE_ANALYSE:
                tvType.setText(NUtil.getString(R.string.ques_analyse));
                rvOption.setVisibility(GONE);
                llExerciseArea.setVisibility(VISIBLE);
                break;
        }
        optionItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnOptionSelect(int position, boolean isSelected) {
        if (analyseStatus) return;
        if (response.getQueTypeId() == Constant.TYPE_SINGLE) selecListToInit();
        selectionList.set(position, isSelected);
        optionItemAdapter.notifyDataSetChanged();
        if (listener != null)
            listener.OnOptionSelect(position, isSelected);
    }

    public Answer getAnswer() {
        Answer answer = new Answer();
        answer.setSelectionList(selectionList);
        return answer;
    }

    public void setAnswer(Answer answer) {
        List<Boolean> list = answer.getSelectionList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            selectionList.set(i, list.get(i));
        }
        optionItemAdapter.notifyDataSetChanged();
    }

    public interface OnOptionSelectListener {
        void OnOptionSelect(int position, boolean isSelected);
    }

    public void setOnOptionSelectListener(OnOptionSelectListener listener) {
        this.listener = listener;
    }

    public void setYourAnswer(String yourAnswer) {
        tvYourAnswer.setText(yourAnswer);
    }

    public void setAnalyse(String analyse) {
        webAnalyse.loadDataWithBaseURL(null, getContext().getString(R.string.analyse, analyse), "text/html", "UTF-8", null);
    }

    public void showAnalyse(boolean show) {
        analyseStatus = show;
        llAnalyse.setVisibility(show ? VISIBLE : GONE);
    }

    public String getEtWenda() {
        return etWenda.getText().toString();
    }

    public void setEtWenda(String wenda) {
        etWenda.setText(wenda);
    }

    public void showExerciseArea(boolean show) {
        etWenda.setVisibility(show ? VISIBLE : GONE);
    }
}