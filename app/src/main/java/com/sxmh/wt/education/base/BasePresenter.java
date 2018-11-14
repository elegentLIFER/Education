package com.sxmh.wt.education.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter {
    protected WeakReference<IView> iView;

    public BasePresenter(IView iView) {
        this.iView = new WeakReference<>(iView);
    }
}
