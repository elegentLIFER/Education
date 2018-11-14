package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecentWatchAdapter extends RecyclerView.Adapter<RecentWatchAdapter.RvThisViewHolder> {
    private Context context;
    private OnItemClickListener clickListener;
    private List<NetCourseResponse.NetCourseListBean> netCourseListBeanList;

    public RecentWatchAdapter(Context context, List<NetCourseResponse.NetCourseListBean> netCourseListBeanList) {
        this.context = context;
        this.netCourseListBeanList = netCourseListBeanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_recent_watch, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        NetCourseResponse.NetCourseListBean bean = netCourseListBeanList.get(position);
        holder.tvTitle.setText(bean.getNetCourseName());
        holder.tvTeacher.setText(bean.getTeacher());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.home_advise_image1).error(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon);
        holder.tvPlayNum.setVisibility(View.GONE);
        holder.llOuter.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return netCourseListBeanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.tv_play_num)
        TextView tvPlayNum;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
