package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.CollectionTalkListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionTalkAdapter extends RecyclerView.Adapter<QuestionTalkAdapter.QuestionTalkViewHolder> {
    private Context context;
    private List<CollectionTalkListResponse.NetCourseListBean> netCourseListBeanList;
    private OnItemClickListener itemClickListener;

    public QuestionTalkAdapter(Context context, List<CollectionTalkListResponse.NetCourseListBean> netCourseListBeanList) {
        this.context = context;
        this.netCourseListBeanList = netCourseListBeanList;
    }

    @NonNull
    @Override
    public QuestionTalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_message_list, parent, false);
        QuestionTalkViewHolder viewHolder = new QuestionTalkViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionTalkViewHolder holder, int position) {
        CollectionTalkListResponse.NetCourseListBean netCourseListBean = netCourseListBeanList.get(position);
        holder.tvTitle.setText(netCourseListBean.getCourseName());
        holder.tvTeacher.setText(netCourseListBean.getCourseTeacher());
        holder.llOuter.setOnClickListener((v) -> {
            itemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return netCourseListBeanList.size();
    }

    class QuestionTalkViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public QuestionTalkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
