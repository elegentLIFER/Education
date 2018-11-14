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
import com.sxmh.wt.education.model.response.HomePageDataResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LessonHotAdapter extends RecyclerView.Adapter<LessonHotAdapter.RvThisViewHolder> {
    private Context context;
    private List<HomePageDataResponse.HotNetCourseListBean> list;
    private OnItemClickListener clickListener;

    public LessonHotAdapter(Context context, List<HomePageDataResponse.HotNetCourseListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_hot, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        HomePageDataResponse.HotNetCourseListBean bean = list.get(position);
        holder.tvTitle.setText(bean.getNetCourseName());
        holder.tvTeacher.setText(bean.getCourseTeacher());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.home_advise_image1).placeholder(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon);
        holder.llOuter.setOnClickListener(view ->  {
                if (clickListener != null) {
                    clickListener.OnItemClick(position);
                }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.tv_play_num)
        TextView tvPlayNum;

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
