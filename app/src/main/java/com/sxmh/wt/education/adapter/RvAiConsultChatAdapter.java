package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.ChatMessage;
import com.sxmh.wt.education.model.ConsultMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvAiConsultChatAdapter extends RecyclerView.Adapter<RvAiConsultChatAdapter.RvThisViewHolder> {
    private static final String TAG = "RvAiConsultChatAdapter";
    private Context context;
    private List<ConsultMessage> consultMessageList;

    public RvAiConsultChatAdapter(Context context, List<ConsultMessage> consultMessageList) {
        this.context = context;
        this.consultMessageList = consultMessageList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_chat, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        ConsultMessage consultMessage = consultMessageList.get(position);
        if (!consultMessage.isMachine()) {
            holder.llLeft.setVisibility(View.GONE);
            holder.llRight.setVisibility(View.VISIBLE);
            holder.tvNameRight.setText("我");

            holder.tvContentRight.setVisibility(View.VISIBLE);
            holder.ivImageRight.setVisibility(View.GONE);
            holder.tvContentRight.setText(consultMessage.getContent());
        } else {
            holder.llLeft.setVisibility(View.VISIBLE);
            holder.llRight.setVisibility(View.GONE);
            holder.tvName.setText("智能顾问");

            holder.tvContent.setVisibility(View.VISIBLE);
            holder.ivImage.setVisibility(View.GONE);
            holder.tvContent.setText(consultMessage.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return consultMessageList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name_left)
        TextView tvName;
        @InjectView(R.id.tv_content_left)
        TextView tvContent;
        @InjectView(R.id.iv_send_image)
        ImageView ivImage;
        @InjectView(R.id.ll_left)
        LinearLayout llLeft;
        @InjectView(R.id.tv_name_right)
        TextView tvNameRight;
        @InjectView(R.id.tv_content_right)
        TextView tvContentRight;
        @InjectView(R.id.iv_image_right)
        ImageView ivImageRight;
        @InjectView(R.id.ll_right)
        LinearLayout llRight;
        @InjectView(R.id.iv_chat_head_right)
        ImageView ivChatHeadRight;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
