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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageActivityAdapter extends RecyclerView.Adapter<MessageActivityAdapter.QuestionAskViewHolder> {
    private Context context;
    private List<String> titleList = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public MessageActivityAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionAskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_question_ask, parent, false);
        QuestionAskViewHolder viewHolder = new QuestionAskViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAskViewHolder holder, int position) {
        holder.llOuter.setOnClickListener(view ->
                itemClickListener.onItemClick());
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class QuestionAskViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_content_left)
        TextView tvContent;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        public QuestionAskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
