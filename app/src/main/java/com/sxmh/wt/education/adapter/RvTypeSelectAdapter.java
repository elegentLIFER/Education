package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxmh.wt.education.model.TypeContainer;
import com.sxmh.wt.education.view.ShrinkableView;

import java.util.List;

public class RvTypeSelectAdapter extends RecyclerView.Adapter<RvTypeSelectAdapter.RvThisViewHolder> {
    private Context context;
    private List<TypeContainer> typeContainerList;

    public RvTypeSelectAdapter(Context context, List<TypeContainer> typeContainerList) {
        this.context = context;
        this.typeContainerList = typeContainerList;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShrinkableView shrinkableView = new ShrinkableView(context);
        RvThisViewHolder rvThisViewHolder = new RvThisViewHolder(shrinkableView);
        return rvThisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        TypeContainer typeContainer = typeContainerList.get(position);
        ShrinkableView shrinkableView = holder.shrinkableView;
        shrinkableView.setTitleText(typeContainer.getLv0Bean().getCourseClassName());
        shrinkableView.setOpen(true);
        shrinkableView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        RvTypeSelectInnerAdapter adapter = new RvTypeSelectInnerAdapter(context, typeContainer.getClassListLv1());
        shrinkableView.getRv().setAdapter(adapter);
        shrinkableView.getRv().setLayoutManager(new GridLayoutManager(context, 3));
    }

    @Override
    public int getItemCount() {
        return typeContainerList.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        ShrinkableView shrinkableView;
        public RvThisViewHolder(View itemView) {
            super(itemView);
            shrinkableView = ((ShrinkableView) itemView);
        }
    }
}
