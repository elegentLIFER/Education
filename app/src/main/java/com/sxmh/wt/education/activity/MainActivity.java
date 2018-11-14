package com.sxmh.wt.education.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.accout.LoginActivity;
import com.sxmh.wt.education.activity.start.TypeSelectActivity;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.fragment.main.CategoryFragment;
import com.sxmh.wt.education.fragment.main.HomeFragment;
import com.sxmh.wt.education.fragment.main.MyFragment;
import com.sxmh.wt.education.fragment.main.SetFragment;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.BottomNavigation;

import butterknife.InjectView;

public class MainActivity extends BaseActivity implements BottomNavigation.OnBottomNaviClickListener {
    private static final String TAG = "MainActivity";
    @InjectView(R.id.bn_bottom_navi)
    BottomNavigation bnBottomNavi;

    private long exitTime;
    private HomeFragment homeFragment;
    private SetFragment setFragment;
    private CategoryFragment categoryFragment;
    private MyFragment myFragment;

    private FragmentManager fragmentManager;
    private boolean isFirst = true;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        getSavedUserInfo();

        bnBottomNavi.setOnBottomNavClickListener(this);
        initFragment();
    }

    private void getSavedUserInfo() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        String json = sp.getString(Constant.SP_KEY_USER, "");
        if (TextUtils.isEmpty(json)) return;
        User user = new Gson().fromJson(json, User.class);
        if (user.getValidTime() < System.currentTimeMillis()) {
            SharedPreferences.Editor edit = sp.edit();
            edit.clear();
            edit.apply();
            return;
        }
        ToastUtil.newToast(this, "已自动登录");
        User instance = User.getInstance();
        instance.setUserName(user.getUserName());
        instance.setMemberId(user.getMemberId());
        instance.setPhoneNum(user.getPhoneNum());
        instance.setPassword(user.getPassword());
        instance.setValidTime(user.getValidTime());
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        setFragment = new SetFragment();
        categoryFragment = new CategoryFragment();
        myFragment = new MyFragment();

        fragmentManager = getSupportFragmentManager();
        showFragment(homeFragment);
    }

    @Override
    public void onHomePageClick() {
        showFragment(homeFragment);
    }

    @Override
    public void onHomeClickAgain() {
        startActivity(new Intent(this, TypeSelectActivity.class));
        finish();
    }

    @Override
    public void onSetClick() {
        showFragment(setFragment);
    }

    @Override
    public void onCategoryClick() {
        showFragment(categoryFragment);
    }

    @Override
    public void onMyClick() {
        String memberId = User.getInstance().getMemberId();
        showFragment(myFragment);
        if (TextUtils.isEmpty(memberId)) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(homeFragment);
        transaction.hide(setFragment);
        transaction.hide(categoryFragment);
        transaction.hide(myFragment);
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_main, fragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    public void toCategoryFragment() {
        bnBottomNavi.setCategoryRadioChecked();
        categoryFragment.setWhich(isFirst, MyApplication.getCurrentLessonTypePosition());
        isFirst = false;
    }

    public void toHomeFragment() {
        bnBottomNavi.setHomeRadioChecked();
    }

    @Override
    public void updateContent(int request, Object content) {

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime < 2000) {
            MyApplication.getInstance().quitApp();
        }
        exitTime = System.currentTimeMillis();
        showToast(getString(R.string.click_again_quit_app));
    }
}
