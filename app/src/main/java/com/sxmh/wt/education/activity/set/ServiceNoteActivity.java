package com.sxmh.wt.education.activity.set;

import android.content.Intent;
import android.webkit.WebView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.PrivacyContentResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.TitleView;

import butterknife.InjectView;

public class ServiceNoteActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.webview)
    WebView webview;

    public static final String TITLE = "title";
    public static final String FLAG_WHICH = "flag";
    @Override
    protected int initLayoutId() {
        return R.layout.activity_service_note;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        Intent intent = getIntent();
        String flag = intent.getStringExtra(FLAG_WHICH);
        String title = intent.getStringExtra(TITLE);
        titleView.setTvTitle(title);
        net.getPrivacy(flag);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_PRIVACY) {
            PrivacyContentResponse response = (PrivacyContentResponse) content;
            String informContent = response.getInformContent();
            webview.loadDataWithBaseURL(null, informContent, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }
}
