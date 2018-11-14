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

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.LessonTypeResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LessonTypeAdapter extends RecyclerView.Adapter<LessonTypeAdapter.LessonTypeViewHolder> {
    private Context context;
    private List<LessonTypeResponse.CourseClassListBean> list;
    private OnItemClickListener listener;

    public LessonTypeAdapter(Context context, List<LessonTypeResponse.CourseClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LessonTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_type, parent, false);
        LessonTypeViewHolder viewHolder = new LessonTypeViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonTypeViewHolder holder, final int position) {
        LessonTypeResponse.CourseClassListBean bean = list.get(position);
        holder.tvTitle.setText(bean.getCourseClassName());
        holder.tvSubTitle.setText(bean.getCourseClassName());
        holder.llOuter.setOnClickListener(view -> {
            listener.OnItemClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LessonTypeViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_sub_title)
        TextView tvSubTitle;
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;

        public LessonTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
