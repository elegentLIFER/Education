package com.sxmh.wt.education.adapter.ask_answer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.ask_answer.NetAnswerListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvHistoryReplyAdapter extends RecyclerView.Adapter<RvHistoryReplyAdapter.RvThisViewHolder> {
    private Context context;
    private List<NetAnswerListResponse.NetAnswerListBean> answerBeanList;

    public RvHistoryReplyAdapter(Context context, List<NetAnswerListResponse.NetAnswerListBean> answerBeanList) {
        this.context = context;
        this.answerBeanList = answerBeanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_lv_history_reply, null);
        RvThisViewHolder rvThisViewHolder = new RvThisViewHolder(inflate);
        return rvThisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        NetAnswerListResponse.NetAnswerListBean bean = answerBeanList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ask_answer_header).error(R.drawable.ask_answer_header);
        Glide.with(context).load(bean.getPhoto()).apply(requestOptions).into(holder.ivHeader);
        holder.tvName.setText(bean.getCreateName());
        holder.tvTime.setText(bean.getCreateDate());
        holder.wvContent.loadDataWithBaseURL(null, bean.getAnswerContent(), "text/html", "UTF-8", null);
    }

    @Override
    public int getItemCount() {
        return answerBeanList.size();
    }


    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.wv_content)
        WebView wvContent;
        @InjectView(R.id.iv_header)
        ImageView ivHeader;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_teacher)
        TextView tvTime;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
