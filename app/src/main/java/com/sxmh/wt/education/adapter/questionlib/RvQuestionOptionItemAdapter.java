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
import com.sxmh.wt.education.view.OptionView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvQuestionOptionItemAdapter extends RecyclerView.Adapter<RvQuestionOptionItemAdapter.RvThisViewHolder> {
    private Context context;
    private List<String> optionList;
    private String[] optionRankList = {"A", "B", "C", "D", "E", "F"};
    private List<Boolean> selectionList;

    private OnOptionSelectListener listener;

    public RvQuestionOptionItemAdapter(Context context, List<String> optionList, List<Boolean> selectionList) {
        this.context = context;
        this.optionList = optionList;
        this.selectionList = selectionList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_question_option, parent, false);
        RvThisViewHolder rvThisViewHolder = new RvThisViewHolder(inflate);
        return rvThisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RvThisViewHolder holder, int position) {
        final OptionView optionView = holder.optionView;
        optionView.setText(optionRankList[position] + "");
        holder.tvOptionContent.setText(optionList.get(position) + "");
        holder.optionView.setChecked(selectionList.get(position));
        holder.llOuter.setOnClickListener(view -> listener.OnOptionSelect(position, !optionView.isChecked()));
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.option_view)
        OptionView optionView;
        @InjectView(R.id.tv_option_content)
        TextView tvOptionContent;
        @InjectView(R.id.ll_outer)
        LinearLayout llOuter;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnOptionSelectListener {
        void OnOptionSelect(int position, boolean isSelected);
    }

    public void setOnOptionSelectListener(OnOptionSelectListener listener) {
        this.listener = listener;
    }
}
