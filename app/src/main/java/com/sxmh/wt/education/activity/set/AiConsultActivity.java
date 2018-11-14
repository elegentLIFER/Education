package com.sxmh.wt.education.activity.set;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.RvAiConsultChatAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.ConsultMessage;
import com.sxmh.wt.education.model.response.AiAnswerListResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AiConsultActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;
    @InjectView(R.id.et_message)
    EditText etMessage;
    @InjectView(R.id.tv_send)
    TextView tvSend;
    @InjectView(R.id.tv_demo_question)
    TextView tvDemoQuestion;

    private List<ConsultMessage> consultMessageList;
    private RvAiConsultChatAdapter consultChatAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ai_consult;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        consultMessageList = new ArrayList<>();

        consultChatAdapter = new RvAiConsultChatAdapter(this, consultMessageList);
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvContent.setAdapter(consultChatAdapter);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_AI_ANSWER_LIST) {
            AiAnswerListResponse response = (AiAnswerListResponse) content;
            List<AiAnswerListResponse.AnswerListBean> answerList = response.getAnswerList();
            int size = answerList.size();
            if (answerList != null && size > 0) {
                for (int i = 0; i < size; i++) {
                    ConsultMessage consultMessage = new ConsultMessage();
                    consultMessage.setMachine(true);
                    consultMessage.setContent(answerList.get(i).getAnswer());
                    consultMessageList.add(consultMessage);
                }
            }else{
                ConsultMessage consultMessage = new ConsultMessage();
                consultMessage.setMachine(true);
                consultMessage.setContent(response.getMsg());
                consultMessageList.add(consultMessage);
            }

            consultChatAdapter.notifyDataSetChanged();
            rvContent.scrollToPosition(consultMessageList.size() - 1);
        }
    }


    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @OnClick({R.id.tv_demo_question, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_demo_question:
                net.getAiAnswerList(tvDemoQuestion.getText().toString());
                break;
            case R.id.tv_send:
                String input = etMessage.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    ConsultMessage consultMessage = new ConsultMessage();
                    consultMessage.setMachine(false);
                    consultMessage.setContent(input);
                    consultMessageList.add(consultMessage);
                    consultChatAdapter.notifyDataSetChanged();

                    etMessage.setText("");

                    net.getAiAnswerList(input);
                    rvContent.scrollToPosition(consultMessageList.size() - 1);
                }
                break;
        }
    }
}
