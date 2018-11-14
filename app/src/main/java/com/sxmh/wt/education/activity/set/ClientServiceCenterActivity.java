package com.sxmh.wt.education.activity.set;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.AdviseWayResponse;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ClientServiceCenterActivity extends BaseActivity implements TitleView.OnTitleViewClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.tv_pre_consultation)
    TextView tvPreConsultation;
    @InjectView(R.id.tv_after_consultation)
    TextView tvAfterConsultation;
    @InjectView(R.id.tv_hot_line)
    TextView tvHotLine;
    @InjectView(R.id.tv_ai_consult)
    TextView tvAiConsult;

    public static final int WAY_TYPE_QQ = 0;
    public static final int WAY_TYPE_PHONE = 1;
    public static final int ADVISORY_TYPE_SHOUQIAN = 0;
    public static final int ADVISORY_TYPE_SHOUHOU = 1;
    public static final int ADVISORY_TYPE_KEFU = 2;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_client_service_center;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        net.getAdvisoryWay();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {

    }

    private List<AdviseWayResponse.AdvisoryWayListBean> advisoryWayList;

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_ADVISE_WAY) {
            AdviseWayResponse response = (AdviseWayResponse) content;
            advisoryWayList = response.getAdvisoryWayList();
        }
    }

    @OnClick({R.id.tv_pre_consultation, R.id.tv_after_consultation, R.id.tv_hot_line, R.id.tv_ai_consult})
    public void onViewClicked(View view) {
        String qq = "";
        String phone = "";
        switch (view.getId()) {
            case R.id.tv_pre_consultation:
                int size = advisoryWayList.size();
                for (int i = 0; i < size; i++) {
                    AdviseWayResponse.AdvisoryWayListBean bean = advisoryWayList.get(i);
                    int advisoryType = bean.getAdvisoryType();
                    int wayType = bean.getWayType();
                    String number = bean.getNumber();
                    if (advisoryType == ADVISORY_TYPE_SHOUQIAN) {
                        if (wayType == WAY_TYPE_QQ) qq = number;
                        if (wayType == WAY_TYPE_PHONE) phone = number;
                    }
                }
                showContectWay(qq, phone);
                break;
            case R.id.tv_after_consultation:
                int size2 = advisoryWayList.size();
                for (int i = 0; i < size2; i++) {
                    AdviseWayResponse.AdvisoryWayListBean bean = advisoryWayList.get(i);
                    int advisoryType = bean.getAdvisoryType();
                    int wayType = bean.getWayType();
                    String number = bean.getNumber();
                    if (advisoryType == ADVISORY_TYPE_SHOUHOU) {
                        if (wayType == WAY_TYPE_QQ) qq = number;
                        if (wayType == WAY_TYPE_PHONE) phone = number;
                    }
                }
                showContectWay(qq, phone);
                break;
            case R.id.tv_hot_line:
                int size3 = advisoryWayList.size();
                for (int i = 0; i < size3; i++) {
                    AdviseWayResponse.AdvisoryWayListBean bean = advisoryWayList.get(i);
                    int advisoryType = bean.getAdvisoryType();
                    int wayType = bean.getWayType();
                    String number = bean.getNumber();
                    if (advisoryType == ADVISORY_TYPE_KEFU) {
                        if (wayType == WAY_TYPE_QQ) qq = number;
                        if (wayType == WAY_TYPE_PHONE) phone = number;
                    }
                }
                showContectWay(qq, phone);
                break;
            case R.id.tv_ai_consult:
                startActivity(new Intent(ClientServiceCenterActivity.this, AiConsultActivity.class));
                break;
        }
    }

    private void showContectWay(String qq, String phone) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_choose_advise_way, null);
        TextView qqWay = inflate.findViewById(R.id.tv_qq_way);
        TextView phoneWay = inflate.findViewById(R.id.tv_phone_way);
        TextView cancel = inflate.findViewById(R.id.tv_cancel);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(inflate)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        qqWay.setText(getString(R.string.qq_way, qq));
        phoneWay.setText(getString(R.string.phone_way, phone));
        qqWay.setOnClickListener((view) -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(qq);
            ToastUtil.newToast(this, "已经复制到粘贴板");
        });
        phoneWay.setOnClickListener((view) -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(dialIntent);
        });
        cancel.setOnClickListener((view) -> alertDialog.dismiss());
    }
}
