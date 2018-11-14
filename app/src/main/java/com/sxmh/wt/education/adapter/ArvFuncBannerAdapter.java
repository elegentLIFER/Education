package com.sxmh.wt.education.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.util.NUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class ArvFuncBannerAdapter extends RecyclerView.Adapter<ArvFuncBannerAdapter.RvViewHolder> {
    private List<GetCycImgResponse.CycleImgListBean> list;
    private int imageH;
    private WeakReference<Context> weakReference;

    public ArvFuncBannerAdapter(Context context, List<GetCycImgResponse.CycleImgListBean> list) {
        this.list = list;
        weakReference = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(weakReference.get());
        RvViewHolder rvViewHolder = new RvViewHolder(imageView);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        ImageView itemView = (ImageView) holder.itemView;
        if (imageH == 0) imageH = NUtil.dp2px(140);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageH));
        itemView.setScaleType(ImageView.ScaleType.FIT_XY);
        String imgUrl = list.get(position).getUrl();
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.live_peixun).error(R.drawable.live_peixun);
        Glide.with(weakReference.get())
                .applyDefaultRequestOptions(requestOptions)
                .load(imgUrl)
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
