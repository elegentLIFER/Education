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
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LessonLiveHotAdapter extends RecyclerView.Adapter<LessonLiveHotAdapter.RvThisViewHolder> {
    private Context context;
    private List<LiveRoomListResponse.HotLiveListBean> beanList;
    private OnItemClickListener listener;

    public LessonLiveHotAdapter(Context context, List<LiveRoomListResponse.HotLiveListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_lesson_live_hot, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, final int position) {
        LiveRoomListResponse.HotLiveListBean bean = beanList.get(position);
        holder.tvTitle.setText(bean.getRoomName());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.home_advise_image1);
        options.error(R.drawable.home_advise_image1);
        Glide.with(context).load(bean.getLiveImage()).apply(options).into(holder.ivIcon);
        holder.llOuter.setOnClickListener(view -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;

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