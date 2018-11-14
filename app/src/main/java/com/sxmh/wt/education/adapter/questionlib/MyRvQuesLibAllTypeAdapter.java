package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.ShrinkableView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyRvQuesLibAllTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyPaperResponse.CourseClasslistBean> list;

    public MyRvQuesLibAllTypeAdapter(Context context, List<MyPaperResponse.CourseClasslistBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_shrinkable_view, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ShrinkableView shrinkableView = ((RvThisViewHolder) holder).shrinkableView;
        shrinkableView.setOpen(true);
        shrinkableView.setTitleText(list.get(position).getCourseClassName());
        List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean> paperTypeList = list.get(position).getPaperTypeList();
        RvMyQuestionLibItemAdapter libItemAdapter = new RvMyQuestionLibItemAdapter(context, paperTypeList);
        shrinkableView.getRv().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        shrinkableView.getRv().setAdapter(libItemAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.shrinkable_view)
        ShrinkableView shrinkableView;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
