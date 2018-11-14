package com.sxmh.wt.education.fragment.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.set.AboutActivity;
import com.sxmh.wt.education.activity.accout.ForgetPswActivity;
import com.sxmh.wt.education.activity.set.RankMeActivity;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

public class SetFragment extends BaseFragment {
    @InjectView(R.id.tv_share_app)
    TextView tvShareApp;
    @InjectView(R.id.tv_use_without_wifi)
    TextView tvUseWithoutWifi;
    @InjectView(R.id.tv_change_psw)
    TextView tvChangePsw;
    @InjectView(R.id.tv_allow_push_msg)
    TextView tvAllowPushMsg;
    @InjectView(R.id.tv_clear_cache)
    TextView tvClearCache;
    @InjectView(R.id.tv_about)
    TextView tvAbout;
    @InjectView(R.id.tv_current_version)
    TextView tvCurrentVersion;
    @InjectView(R.id.tv_give_a_score)
    TextView tvGiveAScore;

    public static final String APP_ID = "wx0e097472c2f912a4";
    public static IWXAPI iwxapi;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void initData() {
        registerToWX();

        PackageManager packageManager = getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            tvCurrentVersion.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void registerToWX() {
        iwxapi = WXAPIFactory.createWXAPI(getContext(), APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    @OnClick({R.id.tv_share_app, R.id.tv_use_without_wifi, R.id.tv_change_psw, R.id.tv_allow_push_msg, R.id.tv_clear_cache, R.id.tv_about, R.id.tv_current_version, R.id.tv_give_a_score})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share_app:
                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wx_share_to, null);
                TextView tvFriend = inflate.findViewById(R.id.tv_friend);
                TextView tvTimeLine = inflate.findViewById(R.id.tv_timeline);
                tvFriend.setOnClickListener(v -> {
                    shareAPP(SendMessageToWX.Req.WXSceneSession);
                });
                tvTimeLine.setOnClickListener(v ->
                        shareAPP(SendMessageToWX.Req.WXSceneTimeline)
                );
                new AlertDialog.Builder(getContext()).setView(inflate).setTitle("分享到").create().show();
                break;
            case R.id.tv_use_without_wifi:
                break;
            case R.id.tv_change_psw:
                startActivity(new Intent(getContext(), ForgetPswActivity.class));
                break;
            case R.id.tv_allow_push_msg:
                break;
            case R.id.tv_clear_cache:
                showLoading();
                File[] files = new File(Constant.CACHE_PATH).listFiles();
                if (files.length > 0) {
                    for (File f : files) {
                        f.delete();
                    }
                }
                cancelLoading();
                ToastUtil.newToast(getContext(), "清理完毕");
                break;
            case R.id.tv_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_current_version:
                break;
            case R.id.tv_give_a_score:
                startActivity(new Intent(getActivity(), RankMeActivity.class));
                break;
        }
    }

    private void shareAPP(int type) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = Constant.SHARE_URL;

        WXMediaMessage message = new WXMediaMessage(webpageObject);
        message.title = "学煌教育";
        message.description = "官网";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        message.thumbData = bitmap.getNinePatchChunk();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = message;
        req.scene = type;

        iwxapi.sendReq(req);
    }


}
