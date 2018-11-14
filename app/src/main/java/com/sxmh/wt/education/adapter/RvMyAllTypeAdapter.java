package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.OrientationHelper;
import com.leochuan.ScaleLayoutManager;
import com.sxmh.wt.education.R;
import com.sxmh.wt.education.adapter.live.LessonLiveAdapter;
import com.sxmh.wt.education.adapter.live.MyLessonLiveAdapter;
import com.sxmh.wt.education.base.BaseActivity;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.MyCourseClassifyResponse;
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

public class RvMyAllTypeAdapter extends RecyclerView.Adapter<RvMyAllTypeAdapter.RvThisViewHolder> implements IView {
    private static final String TAG = "RvAllTypeAdapter";
    private Context context;
    private List<MyCourseClassifyResponse.CourseClassListBean> lessonTypeList;
    private OnItemClickListener listener;

    public RvMyAllTypeAdapter(Context context, String classId, List<MyCourseClassifyResponse.CourseClassListBean> lessonTypeList) {
        this.context = context;
        this.lessonTypeList = lessonTypeList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RvMyAllTypeAdapter.RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_item_shrinkable_view, parent, false);
        RvThisViewHolder viewHolder = new RvThisViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvMyAllTypeAdapter.RvThisViewHolder holder, final int position1) {
        ShrinkableView shrinkableView = holder.shrinkableView;
        if (position1 == 0) shrinkableView.setOpen(true);
        MyCourseClassifyResponse.CourseClassListBean bean = lessonTypeList.get(position1);
        shrinkableView.setTitleText(bean.getCourseClassName());

        MyLessonLiveAdapter lessonHotAdapter = new MyLessonLiveAdapter(context, bean.getNetCourseList());
        shrinkableView.getRv().setLayoutManager(new GridLayoutManager(context, 2));
        shrinkableView.getRv().setAdapter(lessonHotAdapter);
        lessonHotAdapter.setItemClickListener(new MyLessonLiveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listener.OnItemClick(position1, position);
            }

            @Override
            public void onTryListenClick(int position) {
                ToastUtil.newToast(context, "试听");
            }

            @Override
            public void onDownloadClick(int position) {
                ToastUtil.newToast(context, "下载");
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonTypeList.size();
    }

    @Override
    public void updateContent(int request, Object content) {
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

    class RvThisViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.shrinkable_view)
        ShrinkableView shrinkableView;

        public RvThisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int topPosition, int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
