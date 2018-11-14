package com.sxmh.wt.education.activity.lesson;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.MyDownloadAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.download.DownLoadManager;
import com.sxmh.wt.education.download.DownLoadObserver;
import com.sxmh.wt.education.download.DownloadInfo;
import com.sxmh.wt.education.model.DownLoadBean;
import com.sxmh.wt.education.model.DownloadTransbean;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.VideoEncryptUtil;
import com.sxmh.wt.education.view.TitleView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MyDownloadActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, MyDownloadAdapter.OnItemClickListener {
    private static final String TAG = "MyDownloadActivity";
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_my_download)
    RecyclerView rvMyDownload;
    @InjectView(R.id.tv_delete)
    TextView tvDelete;

    public static final String DOWNLOAD_BEAN = "download_bean";
    private boolean inEdit;
    private List<DownLoadBean> downLoadBeanList;
    private MyDownloadAdapter downloadAdapter;
    private Map<Integer, DownLoadObserver> observerMap;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_my_download;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);

        while (true) {
            System.out.println("哈哈哈哈哈哈哈哈哈");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isStop = false;

        downLoadBeanList = new ArrayList<>();
        getPreviousDownloadItems();
        getNewDownloadItems();

        downloadAdapter = new MyDownloadAdapter(this, downLoadBeanList);
        downloadAdapter.setItemClickListener(this);
        rvMyDownload.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMyDownload.setAdapter(downloadAdapter);
        rvMyDownload.setItemAnimator(null);

        beginDownload();
    }

    private boolean isStop;

    private void beginDownload() {
        if (observerMap == null) observerMap = new HashMap<>();
        for (int i = 0; i < downLoadBeanList.size(); i++) {
            DownLoadBean downLoadBean = downLoadBeanList.get(i);
            downLoadBean.setCanceled(false);

            DownLoadObserver downLoadObserver;
            if (observerMap.size() >= i) {
                int finalI = i;
                downLoadObserver = new DownLoadObserver() {

                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        if (isStop) d.dispose();
                        int current = (int) value.getProgress() / 1024 / 1024;
                        int max = (int) value.getTotal() / 1024 / 1024;
                        if (current == max) downLoadBean.setFinished(true);
                        downLoadBean.setProgress(current + "/" + max + "M");
                        downloadAdapter.notifyItemChanged(finalI);
                    }

                    @Override
                    public void onComplete() {
                        if (downloadInfo == null) return;
                        Toast.makeText(MyDownloadActivity.this,
                                downloadInfo.getFileName() + "-DownloadComplete",
                                Toast.LENGTH_SHORT).show();
                        VideoEncryptUtil.encrypt(Constant.VIDEO_PATH + "/" + downloadInfo.getFileName());
                    }
                };
                observerMap.put(i, downLoadObserver);
            } else {
                downLoadObserver = observerMap.get(i);
            }
            if (!downLoadBean.isCanceled())
                DownLoadManager.getInstance().download(downLoadBean.getInfoBean().getPlayUrl(), downLoadObserver);
        }
    }

    private void getNewDownloadItems() {
        Intent intent = getIntent();
        ArrayList<DownLoadBean> listBean = (ArrayList<DownLoadBean>) intent.getSerializableExtra(DOWNLOAD_BEAN);
        if (listBean != null && listBean.size() > 0) {
            loop:
            for (int i = 0; i < listBean.size(); i++) {
                DownLoadBean transbean = listBean.get(i);
                for (int j = 0; j < downLoadBeanList.size(); j++) {
                    if (downLoadBeanList.get(j).getInfoBean().getId().equals(transbean.getInfoBean().getId()))
                        continue loop;
                }
                downLoadBeanList.add(transbean);
            }
        }
    }

    private void getPreviousDownloadItems() {
        try {
            FileReader reader = new FileReader(Constant.DOWNLOAD_TXT_PATH);
            char[] chars = new char[1024 * 10];
            int num = reader.read(chars);
            reader.close();
            if (num == -1) return;

            String gsonString = String.valueOf(chars, 0, num);
            Disposable subscribe = Observable.just(gsonString)
                    .concatMap(s -> Observable.fromArray(s.split(Constant.SEPARATER_JSON)))
                    .filter(s -> !TextUtils.isEmpty(s))
                    .map(s -> new Gson().fromJson(s, DownLoadBean.class))
                    .subscribe(downLoadBean -> downLoadBeanList.add(downLoadBean));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        DownLoadBean downLoadBean = downLoadBeanList.get(position);
        if (downLoadBean.isFinished()) {
            toLessonWatchActivity(downLoadBean);
            return;
        }

        if (!downLoadBean.isCanceled()) {
            DownLoadManager.getInstance().cancel(downLoadBean.getInfoBean().getPlayUrl());
            downLoadBean.setCanceled(true);
            downloadAdapter.notifyDataSetChanged();
            return;
        }

        DownLoadManager.getInstance().download(downLoadBean.getInfoBean().getPlayUrl(), observerMap.get(position));

        downLoadBean.setCanceled(false);
        downloadAdapter.notifyDataSetChanged();
    }

    private void toLessonWatchActivity(DownLoadBean downLoadBean) {
        Intent intent = new Intent(this, LessonWatchActivity.class);
        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, new DownloadTransbean(downLoadBean.getCourseName(), downLoadBean.getInfoBean().getId(), downLoadBean.getLitimg()));
        startActivity(intent);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
        if (inEdit) toEditStatus(false);
        else toEditStatus(true);
    }

    private void toEditStatus(boolean toEditStatus) {
        if (toEditStatus) {
            titleView.setBackgroundColor(NUtil.getColor(R.color.colorMainBlue));
            titleView.setTvRight("完成");
            titleView.setIvBack(R.drawable.back_normal);
            titleView.setTvRightColor(Color.WHITE);
            titleView.setTvTitleColor(Color.WHITE);
            downloadAdapter.setCheckAble(true);
            tvDelete.setVisibility(View.VISIBLE);
            inEdit = true;
        } else {
            titleView.setBackgroundColor(Color.WHITE);
            titleView.setTvRight("编辑");
            titleView.setIvBack(R.drawable.back_);
            titleView.setTvRightColor(R.color.colorMainBlue);
            titleView.setTvTitleColor(R.color.colorTextBlack);
            downloadAdapter.setCheckAble(false);
            tvDelete.setVisibility(View.GONE);
            inEdit = false;
        }
    }

    @Override
    public void updateContent(int request, Object content) {

    }

    @OnClick(R.id.tv_delete)
    public void onViewClicked() {
        for (int i = downLoadBeanList.size() - 1; i >= 0; i--) {
            DownLoadBean downLoadBean = downLoadBeanList.get(i);
            if (downLoadBean.isToBeDeleted()) {
                downLoadBeanList.remove(downLoadBean);
                DownLoadManager.getInstance().cancel(downLoadBean.getInfoBean().getPlayUrl());
                File file = new File(downLoadBean.getFilePath());
                if (file.exists()) file.delete();
            }
        }
        downloadAdapter.notifyDataSetChanged();
        toEditStatus(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAllDownload();

        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        int size = downLoadBeanList.size();
        for (int i = 0; i < size; i++) {
            sb.append(Constant.SEPARATER_JSON);
            sb.append(gson.toJson(downLoadBeanList.get(i), DownLoadBean.class));
        }

        try {
            FileWriter fileWriter = new FileWriter(Constant.DOWNLOAD_TXT_PATH);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isStop = true;
    }

    private void stopAllDownload() {
        for (int i = 0; i < downLoadBeanList.size(); i++) {
            DownLoadBean downLoadBean = downLoadBeanList.get(i);
            if (!downLoadBean.isCanceled()) {
                DownLoadManager.getInstance().cancel(downLoadBean.getInfoBean().getPlayUrl());
                downLoadBean.setCanceled(true);
                downloadAdapter.notifyItemChanged(i);
            }
        }
    }
}


//package com.sxmh.wt.education.activity.lesson;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.sxmh.wt.education.R;
//import com.sxmh.wt.education.adapter.MyDownloadAdapter;
//import com.sxmh.wt.education.base.BaseActivity;
//import com.sxmh.wt.education.download.DownLoadManager;
//import com.sxmh.wt.education.download.DownLoadObserver;
//import com.sxmh.wt.education.download.DownloadInfo;
//import com.sxmh.wt.education.model.DownLoadBean;
//import com.sxmh.wt.education.model.DownloadTransbean;
//import com.sxmh.wt.education.util.Constant;
//import com.sxmh.wt.education.util.NUtil;
//import com.sxmh.wt.education.util.VideoEncryptUtil;
//import com.sxmh.wt.education.view.TitleView;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.InjectView;
//import butterknife.OnClick;
//import io.reactivex.Observable;
//import io.reactivex.disposables.Disposable;
//
//public class MyDownloadActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, MyDownloadAdapter.OnItemClickListener {
//    private static final String TAG = "MyDownloadActivity";
//    @InjectView(R.id.title_view)
//    TitleView titleView;
//    @InjectView(R.id.rv_my_download)
//    RecyclerView rvMyDownload;
//    @InjectView(R.id.tv_delete)
//    TextView tvDelete;
//
//    public static final String DOWNLOAD_BEAN = "download_bean";
//    private boolean inEdit;
//    private List<DownLoadBean> downLoadBeanList;
//    private MyDownloadAdapter downloadAdapter;
//
//    @Override
//    protected int initLayoutId() {
//        return R.layout.activity_my_download;
//    }
//
//    @Override
//    protected void initData() {
//        titleView.setOnTitleViewClickListener(this);
//
//        downLoadBeanList = new ArrayList<>();
//        downloadAdapter = new MyDownloadAdapter(this, downLoadBeanList);
//        downloadAdapter.setItemClickListener(this);
//        rvMyDownload.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvMyDownload.setAdapter(downloadAdapter);
//
//        getPreviousDownloadItems();
//
//        Intent intent = getIntent();
//        ArrayList<DownLoadBean> listBean = (ArrayList<DownLoadBean>) intent.getSerializableExtra(DOWNLOAD_BEAN);
//        if (listBean != null && listBean.size() > 0) {
//            loop:
//            for (int i = 0; i < listBean.size(); i++) {
//                DownLoadBean transbean = listBean.get(i);
//                for (int j = 0; j < downLoadBeanList.size(); j++) {
//                    if (downLoadBeanList.get(j).getInfoBean().getId().equals(transbean))
//                        continue loop;
//                }
//                downLoadBeanList.add(transbean);
//            }
//            downloadAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void getPreviousDownloadItems() {
//        try {
//            FileReader reader = new FileReader(Constant.DOWNLOAD_TXT_PATH);
//            char[] chars = new char[1024 * 10];
//            int num = reader.read(chars);
//            reader.close();
//            if (num == -1) return;
//            String gsonString = String.valueOf(chars, 0, num);
//            Disposable subscribe = Observable.just(gsonString)
//                    .concatMap(s -> Observable.fromArray(s.split(Constant.SEPARATER_JSON)))
//                    .filter(s -> !TextUtils.isEmpty(s))
//                    .map(s -> new Gson().fromJson(s, DownLoadBean.class))
//                    .subscribe(downLoadBean -> downLoadBeanList.add(downLoadBean));
//            downloadAdapter.notifyDataSetChanged();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onItemClick(int position) {
//        DownLoadBean downLoadBean = downLoadBeanList.get(position);
//        if (downLoadBean.isFinished()) {
//            toLessonWatchActivity(downLoadBean);
//            return;
//        }
//
//        if (!downLoadBean.isCanceled()) {
//            DownLoadManager.getInstance().cancel(downLoadBean.getInfoBean().getPlayUrl());
//            downLoadBean.setCanceled(true);
//            downloadAdapter.notifyDataSetChanged();
//            return;
//        }
//
//        DownLoadManager.getInstance().download(downLoadBean.getInfoBean().getPlayUrl(), new DownLoadObserver() {
//            @Override
//            public void onNext(DownloadInfo value) {
//                super.onNext(value);
//                int current = (int) value.getProgress() / 1024 / 1024;
//                int max = (int) value.getTotal() / 1024 / 1024;
//                if (current == max) downLoadBean.setFinished(true);
//                downLoadBean.setProgress(current + "/" + max + "M");
//                downloadAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onComplete() {
//                if (downloadInfo == null) return;
//                Toast.makeText(MyDownloadActivity.this,
//                        downloadInfo.getFileName() + "-DownloadComplete",
//                        Toast.LENGTH_SHORT).show();
//                VideoEncryptUtil.encrypt(Constant.VIDEO_PATH + "/" + downloadInfo.getFileName());
//            }
//        });
//        downLoadBean.setCanceled(false);
//        downloadAdapter.notifyDataSetChanged();
//    }
//
//    private void toLessonWatchActivity(DownLoadBean downLoadBean) {
//        Intent intent = new Intent(this, LessonWatchActivity.class);
//        intent.putExtra(LessonWatchActivity.DOWNLOAD_TRANSBEAN, new DownloadTransbean(downLoadBean.getCourseName(), downLoadBean.getInfoBean().getId(), downLoadBean.getLitimg()));
//        startActivity(intent);
//    }
//
//    @Override
//    public void onBackClick() {
//        finish();
//    }
//
//    @Override
//    public void onTitleRightClick() {
//        if (inEdit) toEditStatus(false);
//        else toEditStatus(true);
//    }
//
//    private void toEditStatus(boolean toEditStatus) {
//        if (toEditStatus) {
//            titleView.setBackgroundColor(NUtil.getColor(R.color.colorMainBlue));
//            titleView.setTvRight("完成");
//            titleView.setIvBack(R.drawable.back_normal);
//            titleView.setTvRightColor(Color.WHITE);
//            titleView.setTvTitleColor(Color.WHITE);
//            downloadAdapter.setCheckAble(true);
//            tvDelete.setVisibility(View.VISIBLE);
//            inEdit = true;
//        } else {
//            titleView.setBackgroundColor(Color.WHITE);
//            titleView.setTvRight("编辑");
//            titleView.setIvBack(R.drawable.back_);
//            titleView.setTvRightColor(R.color.colorMainBlue);
//            titleView.setTvTitleColor(R.color.colorTextBlack);
//            downloadAdapter.setCheckAble(false);
//            tvDelete.setVisibility(View.GONE);
//            inEdit = false;
//        }
//    }
//
//    @Override
//    public void updateContent(int request, Object content) {
//
//    }
//
//    @OnClick(R.id.tv_delete)
//    public void onViewClicked() {
//        for (int i = 0; i < downLoadBeanList.size(); i++) {
//            DownLoadBean downLoadBean = downLoadBeanList.get(i);
//            if (downLoadBean.isToBeDeleted()) {
//                downLoadBeanList.remove(downLoadBean);
//                DownLoadManager.getInstance().cancel(downLoadBean.getInfoBean().getPlayUrl());
//                File file = new File(downLoadBean.getFilePath());
//                if (file.exists()) file.delete();
//            }
//        }
//        downloadAdapter.notifyDataSetChanged();
//        toEditStatus(false);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Gson gson = new Gson();
//        StringBuilder sb = new StringBuilder();
//        int size = downLoadBeanList.size();
//        for (int i = 0; i < size; i++) {
//            sb.append(Constant.SEPARATER_JSON);
//            sb.append(gson.toJson(downLoadBeanList.get(i), DownLoadBean.class));
//        }
//
//        try {
//            FileWriter fileWriter = new FileWriter(Constant.DOWNLOAD_TXT_PATH);
//            fileWriter.write(sb.toString());
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
