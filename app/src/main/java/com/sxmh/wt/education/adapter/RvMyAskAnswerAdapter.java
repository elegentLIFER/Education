package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.ask_answer.MyNetProblemClassResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvMyAskAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    private static final String TAG = "RvAskAnswerAdapter";
    private Context context;
    private List<MyNetProblemClassResponse.CourseClassListBean> lessonTypeList;
    private OnItemClickListener listener;

    public RvMyAskAnswerAdapter(Context context, List<MyNetProblemClassResponse.CourseClassListBean> lessonTypeList) {
        this.context = context;
        this.lessonTypeList = lessonTypeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_more, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RvThisViewHolder viewHolder = (RvThisViewHolder) holder;
        MyNetProblemClassResponse.CourseClassListBean bean = lessonTypeList.get(position);
        viewHolder.tvTitle.setText(bean.getCourseClassName());
        viewHolder.tvNum.setText(bean.getProblemCount() + "");
        viewHolder.llOuter.setOnClickListener(view -> listener.OnItemClick(position));
    }

    @Override
    public int getItemCount() {
        return lessonTypeList.size() ;
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvTitle;
        @InjectView(R.id.tv_num)
        TextView tvNum;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
