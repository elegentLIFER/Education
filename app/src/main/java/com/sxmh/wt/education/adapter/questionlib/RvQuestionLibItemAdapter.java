package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvQuestionLibItemAdapter extends RecyclerView.Adapter<RvQuestionLibItemAdapter.RvThisViewHolder> {
    private Context context;
    private List<PaperListResponse.CourseClasslistBean.PaperTypeListBean> beanList;
    private OnItemClickListener listener;

    public RvQuestionLibItemAdapter(Context context, List<PaperListResponse.CourseClasslistBean.PaperTypeListBean> beanList) {
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
        PaperListResponse.CourseClasslistBean.PaperTypeListBean listBean = beanList.get(position);
        RvQuestionLibItemItemAdapter itemItemAdapter = new RvQuestionLibItemItemAdapter(context, listBean.getPaperTypeName(), listBean.getPaperList());
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