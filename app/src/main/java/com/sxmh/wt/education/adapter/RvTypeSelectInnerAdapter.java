package com.sxmh.wt.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.activity.MainActivity;
import com.sxmh.wt.education.model.response.AllCourseClassResponse;

import java.util.List;

public class RvTypeSelectInnerAdapter extends RecyclerView.Adapter<RvTypeSelectInnerAdapter.RvThisViewHolder> {
    private Context context;
    private List<AllCourseClassResponse.CourseClassListBean> classListLv1;

    public RvTypeSelectInnerAdapter(Context context, List<AllCourseClassResponse.CourseClassListBean> classListLv1) {
        this.context = context;
        this.classListLv1 = classListLv1;
    }

    @NonNull
    @Override
    public RvThisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setPadding(0,50,0,50);
        textView.setLayoutParams(params);
        RvThisViewHolder rvThisViewHolder = new RvThisViewHolder(textView);
        return rvThisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvThisViewHolder holder, int position) {
        TextView itemView = (TextView) holder.itemView;
        AllCourseClassResponse.CourseClassListBean courseClassListBean = classListLv1.get(position);
        itemView.setText(courseClassListBean.getCourseClassName());
        itemView.setOnClickListener((view)->{

            MyApplication.setCurrentClassListLv1(classListLv1);
            MyApplication.setCurrentLessonTypePosition(position);
            String pId = courseClassListBean.getPId();
            MyApplication.setLv0Id(pId);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classListLv1.size();
    }

    class RvThisViewHolder extends RecyclerView.ViewHolder {

        public RvThisViewHolder(View itemView) {
            super(itemView);
        }
    }
}
