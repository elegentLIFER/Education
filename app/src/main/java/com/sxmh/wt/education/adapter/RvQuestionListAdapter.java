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
import com.sxmh.wt.education.model.response.ask_answer.NetProblemListResponse;
import com.sxmh.wt.education.view.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvQuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OnItemClickListener clickListener;
    private List<NetProblemListResponse.NetProblemListBean> beanList;

    public static final int VIEW_TYPE_FOOTER = 101;

    private boolean isDataFinished;

    public RvQuestionListAdapter(Context context, List<NetProblemListResponse.NetProblemListBean> beanList) {
        this.beanList = beanList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == beanList.size()) return VIEW_TYPE_FOOTER;
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.footer_view, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(inflate);
            return footerViewHolder;
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_ask_answer, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType == VIEW_TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvText.setText(isDataFinished ? "已经是最后一条了" : "正在加载中..");
            return;
        }

        if (position != getItemCount()) {
            NetProblemListResponse.NetProblemListBean bean = beanList.get(position);
            RvThisViewHolder thisViewHolder = (RvThisViewHolder) holder;
            thisViewHolder.tvAuthor.setText(bean.getCreateName());
            thisViewHolder.tvTeacher.setText(bean.getCreateDate());
            int problemState = bean.getProblemState();
            if (problemState == 0) {
                thisViewHolder.tvStatus.setText("未解答");
            } else if (problemState == 1) {
                thisViewHolder.tvStatus.setText("已解答");
            } else {
                thisViewHolder.tvStatus.setVisibility(View.GONE);
            }
            thisViewHolder.tvName.setText(bean.getProblemTitle());
            thisViewHolder.tvType.setText(bean.getCourseClassName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ask_answer_header).error(R.drawable.ask_answer_header);
            Glide.with(context).load(bean.getPhoto()).apply(requestOptions).into(((RvThisViewHolder) holder).ivHeader);
            thisViewHolder.llOuter.setOnClickListener(view -> {
                if (clickListener != null) clickListener.OnItemClick(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size() + 1;
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_type)
        TextView tvType;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_status)
        TextView tvStatus;
        @InjectView(R.id.iv_header)
        CircleImageView ivHeader;
        @InjectView(R.id.tv_author)
        TextView tvAuthor;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_text)
        TextView tvText;

        public FooterViewHolder(View itemView) {
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

    public void setDataFinished(boolean dataFinished) {
        isDataFinished = dataFinished;
        notifyDataSetChanged();
    }
}
