package com.sxmh.wt.education.activity.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.util.Constant;

import butterknife.InjectView;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    private MyPagerAdapter pagerAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
    }

    private void saveIsFirstStatus() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_THIS_APP, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(Constant.SP_IS_FIRST, false);
        edit.commit();
    }

    private void toTypeSelectActivity() {
        Intent intent = new Intent(SplashActivity.this, TypeSelectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateContent(int request, Object content) {
    }

    private class MyPagerAdapter extends PagerAdapter {

        private int[] ids = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            LayoutInflater from = LayoutInflater.from(SplashActivity.this);
//            View view = from.inflate(R.layout.splash_view, null);
//            ImageView imageView = view.findViewById(R.id.iv_send_image);
//            Glide.with(SplashActivity.this).load(R.drawable.live_peixun).into(imageView);
//
//            Button btEnter = view.findViewById(R.id.bt_enter);
//            btEnter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    toTypeSelectActivity();
//                    saveIsFirstStatus();
//                }
//            });
//            container.addView(view);

            ImageView imageView = new ImageView(SplashActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(ids[position]);
            imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            imageView.setOnClickListener((v -> {
                if (position == 3) {
                    toTypeSelectActivity();
                    saveIsFirstStatus();
                }
            }));
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(((View) object));
        }
    }
}
