package com.sxmh.wt.education.fragment.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.set.ClientServiceCenterActivity;
import com.sxmh.wt.education.activity.set.FeedbackActivity;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.activity.MainActivity;
import com.sxmh.wt.education.activity.accout.MyCollectionActivity;
import com.sxmh.wt.education.activity.lesson.MyDownloadActivity;
import com.sxmh.wt.education.activity.live.MyLiveActivity;
import com.sxmh.wt.education.activity.accout.PersonalInfoActivity;
import com.sxmh.wt.education.activity.ask_answer.MyAskAnswerActivity;
import com.sxmh.wt.education.activity.lesson.MyLessonActivity;
import com.sxmh.wt.education.activity.lesson.RecentWatchActivity;
import com.sxmh.wt.education.activity.question_lib.MyQuestionLibActivity;
import com.sxmh.wt.education.activity.question_lib.MyWrongsActivity;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.PersonInfoResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {
    @InjectView(R.id.iv_head)
    ImageView ivHead;
    @InjectView(R.id.tv_my_collection)
    TextView tvMyCollection;
    @InjectView(R.id.tv_recent_watch)
    TextView tvRecentWatch;
    @InjectView(R.id.tv_my_download)
    TextView tvMyDownload;
    @InjectView(R.id.tv_client_service_center)
    TextView tvClientServiceCenter;
    @InjectView(R.id.tv_feedback)
    TextView tvFeedback;
    @InjectView(R.id.tv_my_lesson)
    TextView tvMyLesson;
    @InjectView(R.id.tv_my_question_lib)
    TextView tvMyQuestionLib;
    @InjectView(R.id.tv_my_ask_answer)
    TextView tvMyAskAnswer;
    @InjectView(R.id.tv_my_wrongs)
    TextView tvMyWrongs;
    @InjectView(R.id.tv_my_live)
    TextView tvMyLive;
    @InjectView(R.id.tv_logout)
    TextView tvLogout;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_login_duration)
    TextView tvLoginDuration;
    @InjectView(R.id.ll_operate_two)
    LinearLayout llOperateTwo;
    @InjectView(R.id.ll_operate_one)
    LinearLayout llOperateOne;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        showDifferentContentByLoginOrNot();
    }

    private void showDifferentContentByLoginOrNot() {
        if (TextUtils.isEmpty(User.getInstance().getMemberId())) {
            llOperateOne.setVisibility(View.GONE);
            llOperateTwo.setVisibility(View.GONE);
            tvUserName.setText("请登录");
        } else {
            net.getMemberInfo();
            SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_THIS_APP, Context.MODE_PRIVATE);
            long lastLoginTime = sp.getLong(Constant.SP_LOGIN_TIME, 0);
            if (lastLoginTime != 0) {
                long duration = System.currentTimeMillis() - lastLoginTime;
                String time = duration / 1000 / 3600 / 24 + "";
                tvLoginDuration.setText(getString(R.string.has_login_num, time));
            }
            llOperateOne.setVisibility(View.VISIBLE);
            llOperateTwo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_PERSON_INFO) {
            PersonInfoResponse response = (PersonInfoResponse) content;
            saveUserInfo(response);

            User instance = User.getInstance();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.touxaing).error(R.drawable.touxaing);
            Glide.with(this).load(instance.getPhoto()).apply(requestOptions).into(ivHead);
            tvUserName.setText(instance.getUserName());
        }
    }

    private void saveUserInfo(PersonInfoResponse response) {
        User instance = User.getInstance();
        instance.setCompany(response.getCompany());
        instance.setEmailBind(response.getEmailBind());
        instance.setPhoto(response.getPhoto());
        instance.setPosition(response.getPosition());
        instance.setState(response.getState());
    }

    @OnClick({R.id.iv_head, R.id.tv_my_collection, R.id.tv_recent_watch, R.id.tv_my_download,
            R.id.tv_client_service_center, R.id.tv_feedback, R.id.tv_my_lesson, R.id.tv_my_question_lib,
            R.id.tv_my_ask_answer, R.id.tv_my_wrongs, R.id.tv_my_live, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                judgeLoginStatusAndJumpPage();
                break;
            case R.id.tv_my_collection:
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            case R.id.tv_recent_watch:
                startActivity(new Intent(getActivity(), RecentWatchActivity.class));
                break;
            case R.id.tv_my_download:
                startActivity(new Intent(getActivity(), MyDownloadActivity.class));
                break;
            case R.id.tv_my_lesson:
                startActivity(new Intent(getActivity(), MyLessonActivity.class));
                break;
            case R.id.tv_my_question_lib:
                startActivity(new Intent(getActivity(), MyQuestionLibActivity.class));
                break;
            case R.id.tv_my_ask_answer:
                startActivity(new Intent(getActivity(), MyAskAnswerActivity.class));
                break;
            case R.id.tv_my_wrongs:
                startActivity(new Intent(getActivity(), MyWrongsActivity.class));
                break;
            case R.id.tv_my_live:
                startActivity(new Intent(getActivity(), MyLiveActivity.class));
                break;
            case R.id.tv_client_service_center:
                startActivity(new Intent(getActivity(), ClientServiceCenterActivity.class));
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.tv_user_name:
                judgeLoginStatusAndJumpPage();
                break;
            case R.id.tv_logout:
                String memberId = User.getInstance().getMemberId();
                if (TextUtils.isEmpty(memberId)) {
                    ToastUtil.newToast(getContext(), "尚未登录");
                    return;
                }

                new AlertDialog.Builder(getActivity()).setTitle("确定退出登录？")
                        .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                            User.getInstance().clear();
                            SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_THIS_APP, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString(Constant.SP_KEY_USER, "");
                            edit.commit();

                            net.getMemberInfo();
                            ToastUtil.newToast(getContext(), "退出登录");
                            showDifferentContentByLoginOrNot();
                            ((MainActivity) getActivity()).toHomeFragment();
                        }).setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void judgeLoginStatusAndJumpPage() {
        User instance = User.getInstance();
        if (instance.getMemberId() == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
