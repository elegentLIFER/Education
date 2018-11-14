package com.sxmh.wt.education.activity.set;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.view.TitleView;

import butterknife.InjectView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tv_function_introduce)
    TextView tvFunctionIntroduce;
    @InjectView(R.id.tv_complain)
    TextView tvComplain;
    @InjectView(R.id.tv_check_version)
    TextView tvCheckVersion;
    @InjectView(R.id.tv_service_note)
    TextView tvServiceNote;
    @InjectView(R.id.tv_privacy)
    TextView tvPrivacy;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
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
    }

    @OnClick({R.id.tv_function_introduce, R.id.tv_complain, R.id.tv_check_version, R.id.tv_service_note, R.id.tv_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_function_introduce:
                startActivity(new Intent(this, FunctionIntroduceActivity.class));
                break;
            case R.id.tv_complain:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.tv_check_version:
                break;
            case R.id.tv_service_note:
                Intent intent = new Intent(this, ServiceNoteActivity.class);
                intent.putExtra(ServiceNoteActivity.TITLE, "服务条款");
                intent.putExtra(ServiceNoteActivity.FLAG_WHICH, "3");
                startActivity(intent);
                break;
            case R.id.tv_privacy:
                Intent intent2 = new Intent(this, ServiceNoteActivity.class);
                intent2.putExtra(ServiceNoteActivity.TITLE, "隐私政策");
                intent2.putExtra(ServiceNoteActivity.FLAG_WHICH, "4");
                startActivity(intent2);
                break;
        }
    }
}
