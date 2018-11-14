package com.sxmh.wt.education.activity.accout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.LoginPhoneResponse;
import com.sxmh.wt.education.model.response.LoginResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "LoginActivity";
    @InjectView(R.id.tl_tab)
    TabLayout tlTab;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_psw)
    EditText etPsw;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_code)
    EditText etCode;
    @InjectView(R.id.tv_get_code)
    TextView tvGetCode;
    @InjectView(R.id.tv_next)
    TextView tvLogin;
    @InjectView(R.id.tv_forget_psw)
    TextView tvForgetPsw;
    @InjectView(R.id.tv_register_immediately)
    TextView tvRegisterImmediately;
    @InjectView(R.id.ll_et_code)
    LinearLayout llEtCode;
    @InjectView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        tlTab.addOnTabSelectedListener(this);
    }

    @OnClick({R.id.tv_get_code, R.id.tv_next, R.id.tv_forget_psw, R.id.tv_register_immediately, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_next:
                login();
                break;
            case R.id.tv_forget_psw:
                startActivity(new Intent(LoginActivity.this, ForgetPswActivity.class));
                break;
            case R.id.tv_register_immediately:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.input_phone_num));
            return;
        }
        net.getCode(phone, "2");
    }


    private void login() {
        String name = etName.getText().toString();
        String psw = etPsw.getText().toString();
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        int selectedTabPos = tlTab.getSelectedTabPosition();
        if (selectedTabPos == Net.REQUEST_BY_ACCOUNT) {
            if (TextUtils.isEmpty(name)) {
                showToast(getString(R.string.input_username));
                return;
            }
            if (TextUtils.isEmpty(psw)) {
                showToast(getString(R.string.input_psw));
                return;
            }

            String ANDROID_ID = Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
            net.loginPsw(name, psw, ANDROID_ID, Build.MODEL);
        } else if (selectedTabPos == Net.REQUEST_BY_PHONE) {
            if (TextUtils.isEmpty(phone)) {
                showToast(getString(R.string.input_phone_num));
                return;
            }
            if (TextUtils.isEmpty(code)) {
                showToast(getString(R.string.input_code));
                return;
            }
            String ANDROID_ID = Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
            net.loginPhone(phone, code, ANDROID_ID, Build.MODEL);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == Net.REQUEST_BY_ACCOUNT) {
            toAccountLoginStatus();
            return;
        }
        toPhoneLoginStatus();
    }

    private void toAccountLoginStatus() {
        etName.setVisibility(View.VISIBLE);
        etPsw.setVisibility(View.VISIBLE);
        llEtCode.setVisibility(View.GONE);
        etPhone.setVisibility(View.GONE);
    }

    private void toPhoneLoginStatus() {
        etName.setVisibility(View.GONE);
        etPsw.setVisibility(View.GONE);
        llEtCode.setVisibility(View.VISIBLE);
        etPhone.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
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
        } else if (request == Net.REQUEST_BY_ACCOUNT) {
            LoginResponse loginResponse = (LoginResponse) content;
            if (loginResponse.isResult()) {
                saveUserInfo(loginResponse.getMemberId(), etName.getText().toString(), "",loginResponse.getValidTime());
                this.finish();
            }
        } else if (request == Net.REQUEST_BY_PHONE) {
            LoginPhoneResponse loginPhoneResponse = (LoginPhoneResponse) content;
            if (loginPhoneResponse.isResult()) {
                saveUserInfo(loginPhoneResponse.getMemberId(), loginPhoneResponse.getMemberName(), etPhone.getText().toString(),loginPhoneResponse.getValidTime());
                this.finish();
            }
        }
    }

    private void saveUserInfo(String memberId, String name, String phoneNum,String validTime) {
        User user = User.getInstance();
        if (!TextUtils.isEmpty(name)) user.setUserName(name);
        user.setPassword(etPsw.getText().toString());
        if (!TextUtils.isEmpty(phoneNum)) user.setPhoneNum(phoneNum);
        user.setMemberId(memberId);
        user.setValidTime(Long.valueOf(validTime));

        SharedPreferences sp = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user, User.class);
        edit.putString(Constant.SP_KEY_USER, json);
        edit.putLong(Constant.SP_LOGIN_TIME, System.currentTimeMillis());
        edit.commit();
    }
}