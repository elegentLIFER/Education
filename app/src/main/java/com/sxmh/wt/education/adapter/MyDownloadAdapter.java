package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.DownLoadBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyDownloadAdapter extends RecyclerView.Adapter<MyDownloadAdapter.MyDownloadViewHolder> {
    private Context context;
    private List<DownLoadBean> downLoadBeanList;
    private OnItemClickListener itemClickListener;
    private boolean canCheck;

    public MyDownloadAdapter(Context context, List<DownLoadBean> downLoadBeanList) {
        this.context = context;
        this.downLoadBeanList = downLoadBeanList;
    }

    @NonNull
    @Override
    public MyDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_download, parent, false);
        MyDownloadViewHolder viewHolder = new MyDownloadViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDownloadViewHolder holder, int position) {
        DownLoadBean bean = downLoadBeanList.get(position);
        TextView tvProgress = holder.tvProgress;
        if (bean.isCanceled()) tvProgress.setText("已暂停");
        else tvProgress.setText(bean.getProgress());

        if (bean.isFinished()) tvProgress.setText("已完成");

        holder.cbSelect.setVisibility(canCheck ? View.VISIBLE : View.GONE);
        holder.tvTitle.setText(bean.getCourseName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.home_advise_image1).placeholder(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon);
        holder.llOuter.setOnClickListener(view -> itemClickListener.onItemClick(position));
        holder.cbSelect.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> bean.setToBeDeleted(isChecked));
    }

    @Override
    public int getItemCount() {
        return downLoadBeanList.size();
    }

    class MyDownloadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_progress)
        TextView tvProgress;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.cb_select)
        CheckBox cbSelect;

        public MyDownloadViewHolder(View itemView) {
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

    public void setCheckAble(boolean canCheck) {
        this.canCheck = canCheck;
        notifyDataSetChanged();
    }
}
