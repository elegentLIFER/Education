package com.sxmh.wt.education.model;

import com.sxmh.wt.education.model.response.lesson.NetCourseInfoResponse;
import com.sxmh.wt.education.util.Constant;

import java.io.Serializable;
import java.util.List;

public class DownLoadBean implements Serializable {
    private String courseName;
    private String litimg;
    private String progress;
    private NetCourseInfoResponse.NetCourseInfoListBean downLoadBean;
    private boolean isCanceled;
    private boolean isFinished;
    private boolean toBeDeleted;

    public DownLoadBean(NetCourseInfoResponse.NetCourseInfoListBean downLoadBean) {
        this.downLoadBean = downLoadBean;
        setProgress("开始下载");
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public NetCourseInfoResponse.NetCourseInfoListBean getInfoBean() {
        return downLoadBean;
    }

    public void setDownLoadBean(NetCourseInfoResponse.NetCourseInfoListBean downLoadBean) {
        this.downLoadBean = downLoadBean;
    }

    public String getLitimg() {
        return litimg;
    }

    public void setLitimg(String litimg) {
        this.litimg = litimg;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isToBeDeleted() {
        return toBeDeleted;
    }

    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }

    public String getFilePath() {
        String playUrl = downLoadBean.getPlayUrl();
        String[] split = playUrl.split("/");
        String last = split[split.length - 1];
        return Constant.VIDEO_PATH + "/" + last;
    }
}
