package com.sxmh.wt.education.base;

public interface IView {
    void updateContent(int request,Object content);

    void showLoading();

    void cancelLoading();

    void showToast(String error);
}
