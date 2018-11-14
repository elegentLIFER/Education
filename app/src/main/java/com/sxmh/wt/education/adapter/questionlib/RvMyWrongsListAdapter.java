package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.MyWrongsListResponse;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvMyWrongsListAdapter extends RecyclerView.Adapter<RvMyWrongsListAdapter.RvThisViewHolder> {
    private Context context;
    private List<MyWrongsListResponse.PaperCatalogListBean> wrongsList;
    private RvMyWrongsListAdapter.OnItemClickListener listener;

    public void setListener(RvMyWrongsListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RvMyWrongsListAdapter(Context context, List<MyWrongsListResponse.PaperCatalogListBean> wrongsList) {
        this.context = context;
        this.wrongsList = wrongsList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_item_mywrongs, parent, false);
        RvThisViewHolder holder = new RvThisViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        MyWrongsListResponse.PaperCatalogListBean bean = wrongsList.get(position);
        holder.tvName.setText(bean.getCatalogName());
        holder.tvDanXuan.setText(bean.getCheckOne() + "");
        holder.tvDuoXuan.setText(bean.getCheckMany() + "");
        holder.llOuter.setOnClickListener((v -> listener.onItemClick(position)));
    }

    @Override
    public int getItemCount() {
        return wrongsList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_dan_xuan)
        TextView tvDanXuan;
        @InjectView(R.id.tv_duo_xuan)
        TextView tvDuoXuan;
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

    public void setItemClickListener(RvMyWrongsListAdapter.OnItemClickListener itemClickListener) {
        listener = itemClickListener;
    }
}
