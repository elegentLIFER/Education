package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxmh.wt.education.model.response.HomePageDataResponse;

import java.util.List;

public class ArvNotificationAdapter extends RecyclerView.Adapter<ArvNotificationAdapter.ArvNotiViewHolder> {
    private List<HomePageDataResponse.TopInformListBean> list;
    private Context context;
    private OnItemClickListener listener;

    public ArvNotificationAdapter(Context context, List<HomePageDataResponse.TopInformListBean> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ArvNotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        ArvNotiViewHolder rvViewHolder = new ArvNotiViewHolder(textView);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArvNotiViewHolder holder, final int position) {
        TextView itemView = (TextView) holder.itemView;
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        itemView.setGravity(Gravity.CENTER);
        itemView.setMaxLines(1);
        itemView.setText(list.get(position).getTitle());
        itemView.setOnClickListener(view -> listener.OnItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArvNotiViewHolder extends RecyclerView.ViewHolder {
        public ArvNotiViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener{
        void OnItemClicked(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
