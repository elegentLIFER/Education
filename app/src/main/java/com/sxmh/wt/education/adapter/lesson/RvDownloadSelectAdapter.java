package com.sxmh.wt.education.adapter.lesson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvDownloadSelectAdapter extends RecyclerView.Adapter<RvDownloadSelectAdapter.RvThisViewHolder> {
    private Context context;
    private List<NetCourseResponse.NetCourseListBean> courseBeanList;
    private List<Boolean> selectionList;

    public RvDownloadSelectAdapter(Context context, List<NetCourseResponse.NetCourseListBean> courseBeanList) {
        this.context = context;
        this.courseBeanList = courseBeanList;
        selectionList = new ArrayList<>();
        for (int i = 0; i < courseBeanList.size(); i++) {
            selectionList.add(false);
        }
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_download_select, parent, false);
        return new RvThisViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        holder.llOuter.setOnClickListener(v -> holder.checkbox.setChecked(!holder.checkbox.isChecked()));
        NetCourseResponse.NetCourseListBean bean = courseBeanList.get(position);
        int state = bean.getState();
        if (state != 2) {
//            holder.ivIcon.toNewStatus();
        }
        holder.tvName.setText(bean.getNetCourseName());
        holder.checkbox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)-> {
                selectionList.set(position,isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return courseBeanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.checkbox)
        CheckBox checkbox;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;
        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public List<Boolean> getSelectionList() {
        return selectionList;
    }
}
