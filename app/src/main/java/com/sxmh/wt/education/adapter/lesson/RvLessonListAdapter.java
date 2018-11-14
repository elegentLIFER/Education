package com.sxmh.wt.education.adapter.lesson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.sxmh.wt.education.view.RightTopStatusView2;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvLessonListAdapter extends RecyclerView.Adapter<RvLessonListAdapter.RvThisViewHolder> {
    private Context context;
    private List<NetCourseResponse.NetCourseListBean> courseBeanList;
    private OnItemClickListener clickListener;

    public RvLessonListAdapter(Context context, List<NetCourseResponse.NetCourseListBean> courseBeanList) {
        this.context = context;
        this.courseBeanList = courseBeanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson, parent, false);
        return new RvThisViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        holder.llOuter.setOnClickListener(v -> clickListener.OnItemClick(position));
        NetCourseResponse.NetCourseListBean bean = courseBeanList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.video).placeholder(R.drawable.video);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon.ivBottom);
        int state = bean.getState();
        holder.ivIcon.toNewStatus(state != 2);

        holder.tvTitle.setText(bean.getNetCourseName());
        holder.ivCollect.setImageResource(bean.isIsCollect() ? R.drawable.star_ : R.drawable.star);
        holder.tvDownloadVideo.setOnClickListener(v -> clickListener.OnDownloadClick(position));
        holder.ivCollect.setOnClickListener(v -> clickListener.OnCollectClick(position));
        holder.tvTeacher.setText(bean.getTeacher());
    }

    @Override
    public int getItemCount() {
        return courseBeanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        RightTopStatusView2 ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.tv_download_video)
        TextView tvDownloadVideo;
        @InjectView(R.id.iv_collect)
        ImageView ivCollect;
        @InjectView(R.id.tv_collect)
        TextView tvCollect;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnDownloadClick(int position);

        void OnCollectClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
