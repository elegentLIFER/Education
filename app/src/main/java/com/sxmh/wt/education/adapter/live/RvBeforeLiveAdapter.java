package com.sxmh.wt.education.adapter.live;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.live.BeforeLiveListResponse;
import com.sxmh.wt.education.util.NUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvBeforeLiveAdapter extends RecyclerView.Adapter<RvBeforeLiveAdapter.RvThisViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<BeforeLiveListResponse.NetCourseListBean> list;

    public RvBeforeLiveAdapter(Context context, List<BeforeLiveListResponse.NetCourseListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_before_live, parent, false);
        RvThisViewHolder holder = new RvThisViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, final int position) {
        BeforeLiveListResponse.NetCourseListBean bean = list.get(position);
        holder.tvName.setText(bean.getNetCourseName());
        holder.tvHuikan.setOnClickListener((v -> listener.onItemClick(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_huikan)
        TextView tvHuikan;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
