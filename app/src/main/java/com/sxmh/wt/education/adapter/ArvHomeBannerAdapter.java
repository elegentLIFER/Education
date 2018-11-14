package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.GetHomeCycImgResponse;
import com.sxmh.wt.education.util.NUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class ArvHomeBannerAdapter extends RecyclerView.Adapter<ArvHomeBannerAdapter.RvViewHolder> {
    private List<GetHomeCycImgResponse.FirstCycleImgBean> list;
    private int imageW;
    private WeakReference<Context> contextWeakReference;

    public ArvHomeBannerAdapter(Context context, List<GetHomeCycImgResponse.FirstCycleImgBean> list) {
        this.list = list;
        contextWeakReference = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(contextWeakReference.get());
        RvViewHolder rvViewHolder = new RvViewHolder(imageView);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        ImageView itemView = (ImageView) holder.itemView;
        if (imageW == 0) {
            DisplayMetrics dm = contextWeakReference.get().getResources().getDisplayMetrics();
            imageW = dm.widthPixels - 130;
        }
        itemView.setLayoutParams(new ViewGroup.LayoutParams(imageW, (int) (imageW * 0.4)));
        itemView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(contextWeakReference.get())
                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.shape_banner_placeholder))
                .load(list.get(position).getUrl())
                .into(itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RvViewHolder extends RecyclerView.ViewHolder {
        public RvViewHolder(View itemView) {
            super(itemView);
        }
    }
}
