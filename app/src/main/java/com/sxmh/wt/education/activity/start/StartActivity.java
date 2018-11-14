package com.sxmh.wt.education.activity.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.util.Constant;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;

public class StartActivity extends BaseActivity {
    @InjectView(R.id.tv_count_down)
    TextView tvCountDown;
    private Timer timer;
    private int maxTime = 3;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> tvCountDown.setText(getString(R.string.jump, maxTime + "")));
                if (maxTime == 1) {
                    judgeAndJumpPage();
                    return;
                }
                maxTime--;
            }
        }, 0, 1000);
    }

    private void judgeAndJumpPage() {
        finish();
        if (getIsFirstStatus()) {
            toSplashActivity();
            return;
        }
        toTypeSelectActivity();
    }

    private boolean getIsFirstStatus() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        return sp.getBoolean(Constant.SP_IS_FIRST, true);
    }

    private void toTypeSelectActivity() {
        Intent intent = new Intent(StartActivity.this, TypeSelectActivity.class);
        startActivity(intent);
    }

    private void toSplashActivity() {
        Intent intent = new Intent(StartActivity.this, SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    @OnClick(R.id.tv_count_down)
    public void onViewClicked() {
        judgeAndJumpPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
