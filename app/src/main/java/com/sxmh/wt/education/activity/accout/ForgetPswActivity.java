package com.sxmh.wt.education.activity.accout;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.ChangePswResponse;
import com.sxmh.wt.education.model.response.CodeVerifyResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class ForgetPswActivity extends BaseActivity {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_code)
    EditText etCode;
    @InjectView(R.id.tv_get_code)
    TextView tvGetCode;
    @InjectView(R.id.ll_et_code)
    LinearLayout llEtCode;
    @InjectView(R.id.tv_next)
    TextView tvNext;
    @InjectView(R.id.et_psw)
    EditText etPsw;
    @InjectView(R.id.et_psw_again)
    EditText etPswAgain;
    @InjectView(R.id.tv_sure)
    TextView tvSure;

    private CodeVerifyResponse codeVerifyResponse;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.iv_back, R.id.tv_get_code, R.id.tv_next, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(etCode.getText().toString())) {
                    net.doVerifyCode(etPhone.getText().toString(), etCode.getText().toString());
                } else {
                    ToastUtil.newToast(this, getString(R.string.input_code));
                }
                break;
            case R.id.tv_sure:
                String psw = etPsw.getText().toString();
                String pswAgain = etPswAgain.getText().toString();
                if (!psw.equals(pswAgain)) {
                    ToastUtil.newToast(this, "前后两次输入不一致");
                    return;
                }

                if (codeVerifyResponse != null && !TextUtils.isEmpty(codeVerifyResponse.getMemberId()))
                    net.doResetPwd(psw, codeVerifyResponse.getMemberId());
                break;
        }
    }

    private void getCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.input_phone_num));
            return;
        }
        net.getCode(phone, "4");
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_GET_CODE) {
            tvGetCode.setEnabled(false);
            tvGetCode.setTextColor(Color.GRAY);
            new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String countDownString = (int) (millisUntilFinished / 1000) + "秒";
                    tvGetCode.setText(countDownString);
                }

                @Override
                public void onFinish() {
                    tvGetCode.setText(getString(R.string.get_code));
                    tvGetCode.setEnabled(true);
                }
            }.start();
            return;
        } else if (request == Net.REQUEST_CHANGE_PSW) {
            ChangePswResponse response = (ChangePswResponse) content;
            if (response.isResult()) finish();
        } else if (request == Net.REQUEST_CODE_VERIFY) {
            codeVerifyResponse = (CodeVerifyResponse) content;
            if (codeVerifyResponse.isResult()) {
                tvNext.setVisibility(View.GONE);
                etPhone.setVisibility(View.GONE);
                llEtCode.setVisibility(View.GONE);
                etPsw.setVisibility(View.VISIBLE);
                etPswAgain.setVisibility(View.VISIBLE);
                tvSure.setVisibility(View.VISIBLE);
            }
        }
    }
}
