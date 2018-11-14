package com.sxmh.wt.education.activity.accout;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
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
    @InjectView(R.id.et_psw)
    EditText etPsw;
    @InjectView(R.id.et_psw_again)
    EditText etPswAgain;
    @InjectView(R.id.tv_register_immediately)
    TextView tvRegisterImmediately;
    @InjectView(R.id.tv_to_login)
    TextView tvToLogin;
    @InjectView(R.id.cb_agree_protocol)
    CheckBox cbAgreeProtocol;
    @InjectView(R.id.et_name)
    EditText etName;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        cbAgreeProtocol.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_get_code, R.id.tv_register_immediately, R.id.tv_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_register_immediately:
                register();
                break;
            case R.id.tv_to_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }

    private void getCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.input_phone_num));
            return;
        }
        net.getCode(phone, "1");
    }

    private void register() {
        String userName = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        String psw = etPsw.getText().toString();
        String pswAgain = etPswAgain.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast(getString(R.string.input_username));
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.input_phone_num));
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showToast(getString(R.string.input_code));
            return;
        }

        if (TextUtils.isEmpty(psw)) {
            showToast(getString(R.string.input_psw));
            return;
        }

        if (psw.equals(pswAgain)) {
            net.register(userName, psw, phone, code);
            return;
        }
        showToast(getString(R.string.psw_different_twice));
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
        }
        if (request == Net.KEY_REGISTER) {
            if ((boolean) content) {
                finish();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        tvRegisterImmediately.setEnabled(isChecked);
        if (isChecked) {
            tvRegisterImmediately.setTextColor(NUtil.getColor(R.color.colorTextWhite));
            tvRegisterImmediately.setBackgroundResource(R.drawable.shape_bt_bk_round_blue);
            return;
        }
        tvRegisterImmediately.setTextColor(NUtil.getColor(R.color.colorTextDKGray));
        tvRegisterImmediately.setBackgroundResource(R.drawable.shape_bt_bk_round_gray);
    }
}
