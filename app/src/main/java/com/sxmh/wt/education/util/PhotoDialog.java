package com.sxmh.wt.education.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.sxmh.wt.education.R;


/**
 * Created by Administrator on 2017/6/16.
 */

public class PhotoDialog extends Dialog implements View.OnClickListener {
    private Activity mActivity;
    private RelativeLayout rl_photo, rl_gallery;
    private OnClickListener listener;

    public PhotoDialog(@NonNull Activity activity, @StyleRes int themeResId, OnClickListener listener) {
        super(activity, themeResId);
        this.mActivity = activity;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_photo);
        rl_photo = findViewById(R.id.rl_photo);
        rl_photo.setOnClickListener(this);
        rl_gallery = findViewById(R.id.rl_gallery);
        rl_gallery.setOnClickListener(this);
        /*
         * 获取对话框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        p.y = 50; // 新位置Y坐标
        dialogWindow.setAttributes(p);
        this.setCancelable(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_photo:
                listener.onPhotoClick();
                this.dismiss();
                break;
            case R.id.rl_gallery:
                listener.onGalleryClick();
                this.dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onPhotoClick();

        void onGalleryClick();
    }

}
