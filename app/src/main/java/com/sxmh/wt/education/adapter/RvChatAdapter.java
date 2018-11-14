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
import com.sxmh.wt.education.model.ChatMessage;
import com.sxmh.wt.education.util.NUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvChatAdapter extends RecyclerView.Adapter<RvChatAdapter.RvThisViewHolder> {
    private Context context;
    private List<ChatMessage> list;

    public RvChatAdapter(Context context, List<ChatMessage> list) {
        this.context = context;
        this.list = list;
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
        ChatMessage chatMessage = list.get(position);
        ChatMessage.ChatBean chatBean = chatMessage.getChat().get(0);
        String user = chatMessage.getUser();
        String content = chatBean.getContent();

        long l = System.currentTimeMillis();
        holder.tvDate.setText(NUtil.getFormatDate(l));

        if (chatMessage.isMySend()) {
            holder.llLeft.setVisibility(View.GONE);
            holder.llRight.setVisibility(View.VISIBLE);
            holder.tvNameRight.setText(user);

            String type = chatBean.getType();
            if ("text".equals(type)) {
                holder.tvContentRight.setVisibility(View.VISIBLE);
                holder.ivImageRight.setVisibility(View.GONE);
                holder.tvContentRight.setText(chatMessage.getChat().get(0).getContent());
            } else if ("image".equals(type)) {
                holder.tvContentRight.setVisibility(View.GONE);
                holder.ivImageRight.setVisibility(View.VISIBLE);
                Glide.with(context).load(content).into(holder.ivImageRight);
            }
        } else {
            holder.llLeft.setVisibility(View.VISIBLE);
            holder.llRight.setVisibility(View.GONE);
            holder.tvName.setText(user);
            String type = chatBean.getType();
            if ("text".equals(type)) {
                holder.tvContent.setVisibility(View.VISIBLE);
                holder.ivImage.setVisibility(View.GONE);
                holder.tvContent.setText(chatMessage.getChat().get(0).getContent());
            } else if ("image".equals(type)) {
                holder.tvContent.setVisibility(View.GONE);
                holder.ivImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(content).into(holder.ivImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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
        @InjectView(R.id.tv_date)
        TextView tvDate;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
