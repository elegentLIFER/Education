package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.view.BlueProgressView;
import com.sxmh.wt.education.view.RankView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvPaperListItemAdapter extends RecyclerView.Adapter<RvPaperListItemAdapter.RvThisViewHolder> {
    private static final String TAG = "RvPaperListItemAdapter";
    private Context context;
    private List<PaperCatalogResponse.PaperCatalogListBean> list;
    private OnItemClickListener listener;

    public RvPaperListItemAdapter(Context context, List<PaperCatalogResponse.PaperCatalogListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_paper_list, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, final int position) {
        PaperCatalogResponse.PaperCatalogListBean bean = list.get(position);
        Log.e(TAG, "onBindViewHolder: " + bean.getCount());
        holder.tvName.setText(bean.getCatalogName());
        holder.tvDidCount.setText(NUtil.getString(R.string.finish_status, bean.getDidCount() + "", bean.getCount() + ""));
        holder.rankView.setRank(bean.getStarLevel());
        holder.tvTeacher.setText(bean.getTeacher());
        float v = ((float) (bean.getDidCount())) / bean.getCount();
        int rank = (int) (v * 100);
        holder.blueProgressView.setRank(rank);
        holder.llOuter.setOnClickListener(view -> listener.onItemClick(position));

        boolean isActivated = bean.getDidCount() > 0;
        if (isActivated) {
            holder.tvActivation.setText(NUtil.getString(R.string.has_activated));
            holder.tvActivation.setTextColor(NUtil.getColor(R.color.colorTextDKGray));

            boolean hasFinish = bean.getDidCount() == bean.getCount();
            holder.tvFinishStatus.setText(NUtil.getString(hasFinish ? R.string.finished_all : R.string.finished));
            holder.tvDidCount.setVisibility(hasFinish ? View.GONE : View.VISIBLE);
        } else {
            holder.tvActivation.setText(NUtil.getString(R.string.not_activated));
            holder.tvActivation.setTextColor(NUtil.getColor(R.color.colorMainBlue));
        }

        // 如果没有题目则不显示此条目
        holder.llOuter.setVisibility(bean.getCount() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_did_count)
        TextView tvDidCount;
        @InjectView(R.id.rank_view)
        RankView rankView;
        @InjectView(R.id.blue_progress_view)
        BlueProgressView blueProgressView;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        @InjectView(R.id.tv_teacher)
        TextView tvTeacher;
        @InjectView(R.id.tv_activation)
        TextView tvActivation;
        @InjectView(R.id.tv_finish_status)
        TextView tvFinishStatus;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        listener = itemClickListener;
    }
}