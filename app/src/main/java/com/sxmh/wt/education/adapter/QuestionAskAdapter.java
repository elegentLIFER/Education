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
import com.sxmh.wt.education.model.response.CollectionPaperListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionAskAdapter extends RecyclerView.Adapter<QuestionAskAdapter.QuestionAskViewHolder> {
    private Context context;
    private List<CollectionPaperListResponse.QuestionListBean> questionListBeanList;
    private OnItemClickListener itemClickListener;

    public QuestionAskAdapter(Context context,List<CollectionPaperListResponse.QuestionListBean> questionListBeanList) {
        this.context = context;
        this.questionListBeanList = questionListBeanList;
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
        CollectionPaperListResponse.QuestionListBean bean = questionListBeanList.get(position);
        holder.tvTitle.setText(bean.getQuestionTitle());
        holder.llOuter.setOnClickListener(view -> {
            itemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return questionListBeanList.size();
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
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
