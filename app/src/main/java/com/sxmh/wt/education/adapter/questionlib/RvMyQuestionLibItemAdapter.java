package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvMyQuestionLibItemAdapter extends RecyclerView.Adapter<RvMyQuestionLibItemAdapter.RvThisViewHolder> {
    private Context context;
    private List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean> beanList;
    private OnItemClickListener listener;

    public RvMyQuestionLibItemAdapter(Context context, List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recycler_view, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, final int position) {
        MyPaperResponse.CourseClasslistBean.PaperTypeListBean bean = beanList.get(position);
        List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean.PaperListBean> paperList = bean.getPaperList();
        RvMyQuestionLibItemItemAdapter itemItemAdapter = new RvMyQuestionLibItemItemAdapter(context, bean.getPaperTypeName(), paperList);
        holder.rvContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.rvContent.setAdapter(itemItemAdapter);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rv_content)
        RecyclerView rvContent;
        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}