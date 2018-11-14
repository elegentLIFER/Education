package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.question_lib.PaperListActivity;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.util.NUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvMyQuestionLibItemItemAdapter extends RecyclerView.Adapter<RvMyQuestionLibItemItemAdapter.RvThisViewHolder> {
    public static final String PAPER_TYPE_NAME = "paper_type_name";
    public static final String PAPER_LIST_BEAN = "paper_list_bean";
    private Context context;
    private List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean.PaperListBean> beanList;
    private OnItemClickListener listener;
    private String paperTypeName;

    public RvMyQuestionLibItemItemAdapter(Context context, String paperTypeName, List<MyPaperResponse.CourseClasslistBean.PaperTypeListBean.PaperListBean> beanList) {
        this.context = context;
        this.paperTypeName = paperTypeName;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_qlib_rv, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, final int position) {
        MyPaperResponse.CourseClasslistBean.PaperTypeListBean.PaperListBean bean = beanList.get(position);
        holder.tvPaperTypeName.setText(NUtil.getString(R.string.paper_name, paperTypeName, bean.getPaperListName()));
        holder.tvNum.setText(bean.getPaperSize());
        holder.llOuter.setOnClickListener(view -> {
            Intent intent = new Intent(context, PaperListActivity.class);
            intent.putExtra(PaperListActivity.PAPER_LIST_NAME, bean.getPaperListName());
            intent.putExtra(PaperListActivity.PAPER_LIST_ID, bean.getPaperListId());
            intent.putExtra(PAPER_TYPE_NAME, paperTypeName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_paperTypeName)
        TextView tvPaperTypeName;
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
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}