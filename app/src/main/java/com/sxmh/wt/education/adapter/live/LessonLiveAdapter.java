package com.sxmh.wt.education.adapter.live;

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
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LessonLiveAdapter extends RecyclerView.Adapter<LessonLiveAdapter.LessonLiveViewHolder> {
    private Context context;
    private List<NetCourseListResponse.ListBean.NetCourseListBean> beanList;
    private OnItemClickListener listener;

    public LessonLiveAdapter(Context context, List<NetCourseListResponse.ListBean.NetCourseListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public LessonLiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_live, parent, false);
        LessonLiveViewHolder viewHolder = new LessonLiveViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonLiveViewHolder holder, final int position) {
        NetCourseListResponse.ListBean.NetCourseListBean bean = beanList.get(position);
        holder.tvTitle.setText(bean.getNetCourseName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.home_advise_image1).placeholder(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLitimg()).apply(requestOptions).into(holder.ivIcon);

        holder.tvTryListen.setOnClickListener(view -> listener.onTryListenClick(position));
        holder.tvDownload.setOnClickListener(view ->listener.onDownloadClick(position));
        holder.llOuter.setOnClickListener(view ->listener.onItemClick(position));
}

    @Override
    public int getItemCount() {
        return beanList.size();
    }

class LessonLiveViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_name)
    TextView tvTitle;
    @InjectView(R.id.ll_outer)
    LinearLayout llOuter;
    @InjectView(R.id.iv_icon)
    ImageView ivIcon;
    @InjectView(R.id.tv_try_listen)
    TextView tvTryListen;
    @InjectView(R.id.tv_download)
    TextView tvDownload;

    public LessonLiveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}

public interface OnItemClickListener {
    void onItemClick(int position);

    void onTryListenClick(int position);

    void onDownloadClick(int position);

}

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}