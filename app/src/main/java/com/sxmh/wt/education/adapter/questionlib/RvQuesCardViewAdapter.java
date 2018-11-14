package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.Answer;
import com.sxmh.wt.education.model.TransBean;
import com.sxmh.wt.education.view.CardItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvQuesCardViewAdapter extends RecyclerView.Adapter<RvQuesCardViewAdapter.RvThisViewHolder> {
    private Context context;
    private List<Integer> list;
    private OnItemClickListener listener;
    private ArrayList<TransBean> transBeanList;
    private boolean showCorrectOrNot;

    public RvQuesCardViewAdapter(Context context, List<Integer> list, ArrayList<TransBean> transBeanList) {
        this.context = context;
        this.list = list;
        this.transBeanList = transBeanList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_card_item, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        CardItemView itemView = holder.cardItemView;
        int quesKey = list.get(position);
        itemView.setText(quesKey + "");
        itemView.setOnClickListener((view) -> listener.OnItemClick(position));
        TransBean transBean = transBeanList.get(quesKey - 1);
        if (showCorrectOrNot) {
            if (!transBean.isHasDone())
                itemView.setCardStatus(CardItemView.CARD_STATUS_NORMAL);
            else
                itemView.setCardStatus(transBean.isCorrect() ? CardItemView.CARD_STATUS_RIGHT : CardItemView.CARD_STATUS_WRONG);
        } else {
            itemView.setCardStatus(transBean.isHasDone() ? CardItemView.CARD_STATUS_DONE : CardItemView.CARD_STATUS_NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_item_view)
        CardItemView cardItemView;

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

    public boolean isShowCorrectOrNot() {
        return showCorrectOrNot;
    }

    public void setShowCorrectOrNot(boolean showCorrectOrNot) {
        this.showCorrectOrNot = showCorrectOrNot;
    }
}
