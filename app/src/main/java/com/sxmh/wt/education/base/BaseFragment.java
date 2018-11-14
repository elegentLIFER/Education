package com.sxmh.wt.education.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * Created by Wang Tao on 2018/4/9 0009.
 */

public abstract class BaseFragment extends Fragment implements IView {
    private static final String TAG = "BaseFragment";
    private AlertDialog progressDialog;
    protected Net net;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(initLayoutId(), container, false);
        ButterKnife.inject(this, inflate);

        net = new Net(this);
        initData();
        Log.e(TAG, "当前Fragment  ------>   "+getClass().getName());
        return inflate;
    }

    protected abstract int initLayoutId();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.newToast(getContext(), toast);
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new AlertDialog.Builder(getContext())
                    .setView(LayoutInflater.from(getContext()).inflate(R.layout.loading, null))
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
}
