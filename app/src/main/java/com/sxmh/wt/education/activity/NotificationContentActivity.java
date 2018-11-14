package com.sxmh.wt.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.main.HomeFragment;
import com.sxmh.wt.education.model.response.MessageListResponse;
import com.sxmh.wt.education.model.response.NotificationContentResponse;
import com.sxmh.wt.education.view.TitleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationContentActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.tv_msg_title)
    TextView tvTitle;
    @InjectView(R.id.tv_date)
    TextView tvDate;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_notification_content;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        Intent intent = getIntent();
        if (intent.getAction() == MessageActivity.ACTION_MESSAGE) {
            MessageListResponse.InformListBean informListBean = (MessageListResponse.InformListBean) intent.getSerializableExtra(MessageActivity.INFORM_LIST_BEAN);
            tvTitle.setText(informListBean.getInformTitle());
            tvDate.setText(informListBean.getCreateDate());
            webview.loadDataWithBaseURL(null, informListBean.getInformContent(), "text/html", "UTF-8", null);
        } else if (intent.getAction() == HomeFragment.ACTION_NOTIFICATION) {
            String notificationId = intent.getStringExtra(HomeFragment.INTENT_NOTIFICATION_ID);
            Bundle params = new Bundle();
            net.getNotificationContent(notificationId);
        }
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
        NotificationContentResponse response = (NotificationContentResponse) content;
        tvDate.setText("创建时间：" + response.getCreateDate());
        webview.loadDataWithBaseURL(null, response.getDetails(), "text/html", "UTF-8", null);
    }
}
