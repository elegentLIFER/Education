package com.sxmh.wt.education.adapter.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.SearchResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvSearchTalkAdapter extends RecyclerView.Adapter<RvSearchTalkAdapter.QuestionTalkViewHolder> {
    private Context context;
    private List<SearchResponse.NetCourseListBean> netCourseListBeanList;
    private OnItemClickListener itemClickListener;

    public RvSearchTalkAdapter(Context context, List<SearchResponse.NetCourseListBean> netCourseListBeanList) {
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
        SearchResponse.NetCourseListBean netCourseListBean = netCourseListBeanList.get(position);
        holder.tvTitle.setText(netCourseListBean.getNetCourseName());
//        holder.tvTeacher.setText(netCourseListBean.);
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
