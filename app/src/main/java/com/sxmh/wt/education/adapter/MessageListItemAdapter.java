package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.MessageListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageListItemAdapter extends RecyclerView.Adapter<MessageListItemAdapter.RvThisViewHolder> {
    private Context context;
    private List<MessageListResponse.InformListBean> beanList;
    private OnItemClickListener itemClickListener;

    public MessageListItemAdapter(Context context, List<MessageListResponse.InformListBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_message_list, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        MessageListResponse.InformListBean bean = beanList.get(position);
        holder.tvTitle.setText(bean.getInformTitle());
        holder.tvTime.setText(bean.getCreateDate());
        holder.llOuter.setOnClickListener((view) -> itemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_teacher)
        TextView tvTime;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
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
