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
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.CollectionLiveListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CollectionLiveAdapter extends RecyclerView.Adapter<CollectionLiveAdapter.CollectionLiveViewHolder> {
    private Context context;
    private OnItemClickListener itemClickListener;
    private List<CollectionLiveListResponse.LiveRoomListBean> liveRoomListBeanList;

    public CollectionLiveAdapter(Context context, List<CollectionLiveListResponse.LiveRoomListBean> liveRoomListBeanList) {
        this.context = context;
        this.liveRoomListBeanList = liveRoomListBeanList;
    }

    @NonNull
    @Override
    public CollectionLiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_live, parent, false);
        CollectionLiveViewHolder viewHolder = new CollectionLiveViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionLiveViewHolder holder, int position) {
        CollectionLiveListResponse.LiveRoomListBean bean = liveRoomListBeanList.get(position);
        holder.tvTeacher.setText(bean.getTeacher());
        holder.tvTime.setText(bean.getLiveBeginDate() + " " + bean.getLiveBeginTime() + "-" + bean.getLiveEndTime());
        holder.tvTitle.setText(bean.getRoomName());
        holder.llOuter.setOnClickListener(view -> {
            itemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return liveRoomListBeanList.size();
    }

    class CollectionLiveViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTime;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public CollectionLiveViewHolder(View itemView) {
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
