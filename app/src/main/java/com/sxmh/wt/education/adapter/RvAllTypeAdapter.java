package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.activity.lesson.DownloadSelectActivity;
import com.sxmh.wt.education.adapter.live.LessonLiveAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.RightTopStatusView;
import com.sxmh.wt.education.view.ShrinkableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvAllTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IView {
    private static final String TAG = "RvAllTypeAdapter";
    private Context context;
    private List<LessonTypeResponse.CourseClassListBean> lessonTypeList;
    private OnItemClickListener listener;

    public static final int VIEW_TYPE_BANNER = 100;
    public static final int VIEW_TYPE_BUTTON_PAD = 101;

    private List<GetCycImgResponse.CycleImgListBean> urlList;
    private ArvFuncBannerAdapter bannerAdapter;
    private List<NetCourseListResponse.ListBean> dataList;

    private Net net;

    public RvAllTypeAdapter(Context context, String classId, List<LessonTypeResponse.CourseClassListBean> lessonTypeList, List<NetCourseListResponse.ListBean> dataList) {
        this.context = context;
        this.lessonTypeList = lessonTypeList;
        this.dataList = dataList;

        urlList = new ArrayList<>();
        bannerAdapter = new ArvFuncBannerAdapter(context, urlList);

        net = new Net(this);
        net.getFunctionCycImg(Constant.BANNER_JIANG_TI, classId);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_BANNER;
        }
        if (position == 1) {
            return VIEW_TYPE_BUTTON_PAD;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_BANNER) {
            AutoPlayRecyclerView autoPlayRecyclerView = new AutoPlayRecyclerView(context);
            autoPlayRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ((int) context.getResources().getDimension(R.dimen.dimen_banner))));
            ScaleLayoutManager build = new ScaleLayoutManager.Builder(context, 0)
                    .setOrientation(OrientationHelper.HORIZONTAL)
                    .setMinScale(1)
                    .setMaxVisibleItemCount(1)
                    .build();
            autoPlayRecyclerView.setBackgroundColor(Color.WHITE);
            autoPlayRecyclerView.setLayoutManager(build);
            return new BannerViewHolder(autoPlayRecyclerView);
        }
        if (viewType == VIEW_TYPE_BUTTON_PAD) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.button_pad, parent, false);
            return new ButtonPadViewHolder(inflate);
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

        if (itemViewType == VIEW_TYPE_BUTTON_PAD) {
            ButtonPadViewHolder padViewHolder = (ButtonPadViewHolder) holder;
            padViewHolder.rtsvNetSchoolTalk.setOnClickListener(view -> listener.OnNetSchoolTalkClick());
            padViewHolder.rtsvAskAnswerOnline.setOnClickListener(view -> listener.OnAskAnswerOnlineClick());
            padViewHolder.rtsvLive.setOnClickListener(view -> listener.OnLiveClick());
            padViewHolder.rtsvWorkOnline.setOnClickListener(view -> listener.OnWorkOnlineClick());
            return;
        }

        int positionRight = position - 2;
        ShrinkableView shrinkableView = ((RvThisViewHolder) holder).shrinkableView;
        shrinkableView.setOpen(true);
        shrinkableView.setTitleText(lessonTypeList.get(positionRight).getCourseClassName());

        LessonLiveAdapter lessonHotAdapter = new LessonLiveAdapter(context, dataList.get(positionRight).getNetCourseList());
        shrinkableView.getRv().setLayoutManager(new GridLayoutManager(context, 2));
        shrinkableView.getRv().setAdapter(lessonHotAdapter);
        lessonHotAdapter.setItemClickListener(new LessonLiveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listener.OnItemClick(positionRight, position);
            }

            @Override
            public void onTryListenClick(int position) {
                ToastUtil.newToast(context, "试听");
            }

            @Override
            public void onDownloadClick(int position) {
                listener.OnDownloadClick(positionRight, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 2;
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

    class ButtonPadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rtsv_net_school_talk)
        RightTopStatusView rtsvNetSchoolTalk;
        @InjectView(R.id.rtsv_work_online)
        RightTopStatusView rtsvWorkOnline;
        @InjectView(R.id.rtsv_live)
        RightTopStatusView rtsvLive;
        @InjectView(R.id.rtsv_ask_answer_online)
        RightTopStatusView rtsvAskAnswerOnline;

        public ButtonPadViewHolder(View itemView) {
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

    public interface OnItemClickListener {
        void OnNetSchoolTalkClick();

        void OnAskAnswerOnlineClick();

        void OnLiveClick();

        void OnWorkOnlineClick();

        void OnItemClick(int topPosition, int position);

        void OnDownloadClick(int topPosition, int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
