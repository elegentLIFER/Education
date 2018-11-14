package com.sxmh.wt.education.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import butterknife.ButterKnife;


/**
 * Created by Wang Tao on 2018/4/9 0009.
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    private static final String TAG = "BaseActivity";
    private AlertDialog progressDialog;
    protected Net net;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setContentView(initLayoutId());
        ButterKnife.inject(this);

        net = new Net(this);
        initData();
        Log.e(TAG, "当前Activity  ----->  " + getClass().getName());
    }

    protected abstract int initLayoutId();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new AlertDialog.Builder(this)
                    .setView(LayoutInflater.from(this).inflate(R.layout.loading, null))
                    .create();
            Window window = progressDialog.getWindow();
            window.setDimAmount(0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public void cancelLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.newToast(this, toast);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View currentFocus = getCurrentFocus();
            if (shouldHideInput(currentFocus, ev)) {
                hideInput();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean shouldHideInput(View currentFocus, MotionEvent ev) {
        if (currentFocus != null && currentFocus instanceof EditText) {

            float x = ev.getX();
            float y = ev.getY();

            int[] outLocation = new int[2];
            currentFocus.getLocationInWindow(outLocation);
            int lx = outLocation[0];
            int ly = outLocation[1];

            // 点击的位置在EditText范围内与否
            boolean b = x > lx && x < lx + currentFocus.getWidth() && y < ly + currentFocus.getHeight() && y > ly;
            if (b) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideInput() {
        InputMethodManager manager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}