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

public class LessonAdviseAdapter extends RecyclerView.Adapter<LessonAdviseAdapter.LessonAdviseViewHolder> {
    private Context context;
    private List<HomePageDataResponse.RecomNetCourseListBean> list;
    private OnItemClickListener clickListener;

    public LessonAdviseAdapter(Context context, List<HomePageDataResponse.RecomNetCourseListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LessonAdviseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_advise, parent, false);
        LessonAdviseViewHolder viewHolder = new LessonAdviseViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdviseViewHolder holder, final int position) {
        HomePageDataResponse.RecomNetCourseListBean bean = list.get(position);
        holder.tvTitle.setText(bean.getNetCourseName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.home_advise_image1).placeholder(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon);
        holder.llOuter.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LessonAdviseViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.tv_name)
        TextView tvTitle;

        public LessonAdviseViewHolder(View itemView) {
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
