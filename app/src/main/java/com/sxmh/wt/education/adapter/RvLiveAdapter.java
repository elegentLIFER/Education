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
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvLiveAdapter extends RecyclerView.Adapter<RvLiveAdapter.LessonHotViewHolder> {
    private Context context;
    private List<LiveRoomListResponse.LiveListBean> list;
    private OnItemClickListener clickListener;

    public RvLiveAdapter(Context context, List<LiveRoomListResponse.LiveListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LessonHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_live, parent, false);
        LessonHotViewHolder viewHolder = new LessonHotViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHotViewHolder holder, int position) {
        LiveRoomListResponse.LiveListBean bean = list.get(position);
        holder.tvTitle.setText(bean.getRoomName());
        holder.tvTeacher.setText(bean.getTeacher());
        holder.tvTime.setText(bean.getLiveBeginTime());
        Glide.with(context)
                .load(bean.getLiveImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.shape61)
                        .error(R.drawable.shape61))
                .into(holder.ivIcon);
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

    class LessonHotViewHolder extends RecyclerView.ViewHolder {
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

        public LessonHotViewHolder(View itemView) {
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
