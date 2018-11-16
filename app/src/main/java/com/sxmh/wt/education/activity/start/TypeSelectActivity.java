package com.sxmh.wt.education.activity.start;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.RvTypeSelectAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.model.TypeContainer;
import com.sxmh.wt.education.model.response.AllCourseClassResponse;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;

public class TypeSelectActivity extends BaseActivity {
    private static final String TAG = "TypeSelectActivity";
    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    private RvTypeSelectAdapter rvTypeSelectAdapter;
    private List<TypeContainer> containerList;

    @Override
    protected int initLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initData() {
        net.getAllCourseClass();
        containerList = new ArrayList<>();
        rvTypeSelectAdapter = new RvTypeSelectAdapter(this, containerList);
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(rvTypeSelectAdapter);
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_ALL_COURSE_CLASS) {
            AllCourseClassResponse response = (AllCourseClassResponse) content;
            List<AllCourseClassResponse.CourseClassListBean> courseClassList = response.getCourseClassList();
            MyApplication.setCourseClassList(courseClassList);

            containerList.clear();

            Disposable disposable = Observable.fromIterable(courseClassList)
                    .filter(courseClassListBean -> "0".equals(courseClassListBean.getCourseClassLevel()))
                    .subscribe(courseClassListBean -> {
                        TypeContainer container = new TypeContainer();
                        container.setLv0Bean(courseClassListBean);
                        containerList.add(container);
                    });

            for (int i = 0; i < containerList.size(); i++) {
                TypeContainer container = containerList.get(i);
                String id = container.getLv0Bean().getId();

                Disposable disposable1 = Observable.fromIterable(courseClassList)
                        .filter((courseClassListBean -> "1".equals(courseClassListBean.getCourseClassLevel()) && id.equals(courseClassListBean.getPId())))
                        .subscribe(courseClassListBean -> container.getClassListLv1().add(courseClassListBean));
            }
            rvTypeSelectAdapter.notifyDataSetChanged();
        }
    }
}
