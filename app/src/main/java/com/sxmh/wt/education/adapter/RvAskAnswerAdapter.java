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
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RvAskAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IView {
    private static final String TAG = "RvAskAnswerAdapter";
    private Context context;
    private List<NetProblemClassResponse.NetProblemClassListBean> lessonTypeList;
    private OnItemClickListener listener;
    private String classId;

    public static final int VIEW_TYPE_BANNER = 100;
    private List<GetCycImgResponse.CycleImgListBean> urlList;
    private ArvFuncBannerAdapter bannerAdapter;

    private Net net;

    public RvAskAnswerAdapter(Context context, String classId, List<NetProblemClassResponse.NetProblemClassListBean> lessonTypeList) {
        this.context = context;
        this.lessonTypeList = lessonTypeList;
        this.classId = classId;
        urlList = new ArrayList<>();
        bannerAdapter = new ArvFuncBannerAdapter(context, urlList);

        net = new Net(this);
        net.getFunctionCycImg(Constant.BANNER_WEN_DA, classId);
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
            autoPlayRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ((int) context.getResources().getDimension(R.dimen.dimen_banner))));
            ScaleLayoutManager build = new ScaleLayoutManager.Builder(context, 0)
                    .setOrientation(OrientationHelper.HORIZONTAL)
                    .setMinScale(1)
                    .setMaxVisibleItemCount(1)
                    .build();
            autoPlayRecyclerView.setLayoutManager(build);
            return new BannerViewHolder(autoPlayRecyclerView);
        }

        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_more, parent, false);
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

        final int positionRight = position - 1;
        RvThisViewHolder viewHolder = (RvThisViewHolder) holder;
        NetProblemClassResponse.NetProblemClassListBean bean = lessonTypeList.get(positionRight);
        viewHolder.tvTitle.setText(bean.getNetProblemClassName());
        viewHolder.tvNum.setText(bean.getQueCount() + "");
        viewHolder.llOuter.setOnClickListener(view -> listener.OnItemClick(positionRight));
    }

    @Override
    public int getItemCount() {
        return lessonTypeList.size() + 1;
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
        }
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
