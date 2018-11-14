package com.sxmh.wt.education.activity.set;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import butterknife.InjectView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, RadioGroup.OnCheckedChangeListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.et_email)
    EditText etEmail;
    @InjectView(R.id.et_other_contact)
    EditText etOtherContact;
    @InjectView(R.id.tv_commit)
    TextView tvCommit;

    private int flag;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        rgGroup.setOnCheckedChangeListener(this);
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

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String email = etEmail.getText().toString();
        String otherContact = etOtherContact.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.newToast(this, "请填写反馈内容");
            return;
        }
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(otherContact)) {
            ToastUtil.newToast(this, "请至少填写一个联系方式");
            return;
        }
        net.doSaveOpinion(content, flag + "", email, otherContact);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        flag = checkedId - 1;
    }
}
