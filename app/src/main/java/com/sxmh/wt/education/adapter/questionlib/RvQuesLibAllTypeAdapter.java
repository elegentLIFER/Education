package com.sxmh.wt.education.adapter.questionlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.ArvFuncBannerAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.view.ShrinkableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvQuesLibAllTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IView {
    private Context context;
    private List<LessonTypeResponse.CourseClassListBean> list;

    public static final int VIEW_TYPE_BANNER = 100;

    private List<GetCycImgResponse.CycleImgListBean> urlList;
    private ArvFuncBannerAdapter bannerAdapter;
    private List<PaperListResponse.CourseClasslistBean> listList;

    private Net net;

    public RvQuesLibAllTypeAdapter(Context context, String courseClassId, List<LessonTypeResponse.CourseClassListBean> list, List<PaperListResponse.CourseClasslistBean> listList) {
        this.context = context;
        this.list = list;
        this.listList = listList;

        urlList = new ArrayList<>();
        bannerAdapter = new ArvFuncBannerAdapter(context, urlList);

        net = new Net(this);
        net.getFunctionCycImg(Constant.BANNER_ZUO_TI, courseClassId);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_BANNER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_BANNER) {
            AutoPlayRecyclerView autoPlayRecyclerView = new AutoPlayRecyclerView(context);
            autoPlayRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.dimen_banner)));
            ScaleLayoutManager build = new ScaleLayoutManager.Builder(context, 0)
                    .setOrientation(OrientationHelper.HORIZONTAL)
                    .setMinScale(1)
                    .setMaxVisibleItemCount(1)
                    .build();
            autoPlayRecyclerView.setBackgroundColor(Color.WHITE);
            autoPlayRecyclerView.setLayoutManager(build);
            return new BannerViewHolder(autoPlayRecyclerView);
        }

        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_shrinkable_view, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == VIEW_TYPE_BANNER) {
            AutoPlayRecyclerView autoPlayRecyclerView = (AutoPlayRecyclerView) ((BannerViewHolder) holder).itemView;
            autoPlayRecyclerView.setAdapter(bannerAdapter);
            return;
        }

        int positionRight = position - 1;
        ShrinkableView shrinkableView = ((RvThisViewHolder) holder).shrinkableView;
        shrinkableView.setOpen(true);
        shrinkableView.setTitleText(list.get(positionRight).getCourseClassName());
        RvQuestionLibItemAdapter libItemAdapter = new RvQuestionLibItemAdapter(context, listList.get(positionRight).getPaperTypeList());
        shrinkableView.getRv().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        shrinkableView.getRv().setAdapter(libItemAdapter);
    }

    @Override
    public int getItemCount() {
        return listList.size() + 1;
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_BANNER) {
            urlList.clear();
            urlList.addAll((List<GetCycImgResponse.CycleImgListBean>) content);
            bannerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        ((BaseActivity) context).showLoading();
    }

    @Override
    public void cancelLoading() {
        ((BaseActivity) context).cancelLoading();
    }

    @Override
    public void showToast(String error) {
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.shrinkable_view)
        ShrinkableView shrinkableView;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
