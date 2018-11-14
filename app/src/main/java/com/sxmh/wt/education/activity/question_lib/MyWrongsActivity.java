package com.sxmh.wt.education.activity.question_lib;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.questionlib.RvMyWrongsListAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.response.MyWrongsListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.TitleView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import butterknife.InjectView;

public class MyWrongsActivity extends BaseActivity implements TitleView.OnTitleViewClickListener, RvMyWrongsListAdapter.OnItemClickListener {
    @InjectView(R.id.title_view)
    TitleView titleView;
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    private List<MyWrongsListResponse.PaperCatalogListBean> wrongsList;
    private RvMyWrongsListAdapter wrongsListAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_my_wrongs;
    }

    @Override
    protected void initData() {
        titleView.setOnTitleViewClickListener(this);
        wrongsList = new ArrayList<>();

        wrongsListAdapter = new RvMyWrongsListAdapter(this, wrongsList);
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(wrongsListAdapter);
        wrongsListAdapter.setItemClickListener(this);

        net.getErrorQuestionList();
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_MY_WRONGS) {
            MyWrongsListResponse response = (MyWrongsListResponse) content;
            wrongsList.clear();
            wrongsList.addAll(response.getPaperCatalogList());
            wrongsListAdapter.notifyDataSetChanged();

            if (wrongsList.size() == 0)
                ToastUtil.newToast(this, getString(R.string.no_course));
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onTitleRightClick() {
    }

    @Override
    public void onItemClick(int position) {
        MyWrongsListResponse.PaperCatalogListBean bean = wrongsList.get(position);
        Intent intent = new Intent(this, QuesAnalyseActivity.class);
        intent.putExtra(PaperListActivity.PAPER_LIST_ID, bean.getPaperId());
        intent.putExtra(PaperListActivity.PAPER_CATALOG_LIST_BEAN, bean.getId());
        intent.putExtra(PaperListActivity.FLAG_WHICH_OPERATE, Constant.FLAG_SEE_WRONGS);
        intent.putExtra(TestResultActivity.POSITION, position);
        startActivity(intent);
    }
}