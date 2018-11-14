package com.sxmh.wt.education.model.response.lesson;

import java.io.Serializable;
import java.util.List;

public class NetCourseInfoResponse {

    private List<NetCourseInfoListBean> netCourseInfoList;

    public List<NetCourseInfoListBean> getNetCourseInfoList() {
        return netCourseInfoList;
    }

    public void setNetCourseInfoList(List<NetCourseInfoListBean> netCourseInfoList) {
        this.netCourseInfoList = netCourseInfoList;
    }

    public static class NetCourseInfoListBean implements Serializable{
        /**
         * playUrl : http://video.pinabc.cn/userfiles/files/files2018/18一级建造师/18一建（管理）/18一建管理（精讲班）宿吉南/03、18一建管理精讲第一章（1）（2）-3.mp4
         * id : 2c92908d63ab58e00163ae91bf970031
         * remark : 以新大纲新教材为标杆逐章精讲，题点结合深化理解。根据近三年考试分值分布情况，梳理考试必学目标内容。零基础及平时没时间学习的考生，结合考生科学指导学习计划。
         * teacherPhoto : http://120.77.242.84:8089/xhms/userfiles/files/AppFiles/teacherFiles/宿吉南.png
         * teacherInfo : 国家一级建造师，讲课风趣幽默，授课直击考点，能真正帮助学员吸收重点难点。
         * isCollect : false
         * teacherName : 宿吉南
         * lookNum : 414
         */

        private String playUrl;
        private String id;
        private String remark;
        private String teacherPhoto;
        private String teacherInfo;
        private boolean isCollect;
        private String teacherName;
        private int lookNum;

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTeacherPhoto() {
            return teacherPhoto;
        }

        public void setTeacherPhoto(String teacherPhoto) {
            this.teacherPhoto = teacherPhoto;
        }

        public String getTeacherInfo() {
            return teacherInfo;
        }

        public void setTeacherInfo(String teacherInfo) {
            this.teacherInfo = teacherInfo;
        }

        public boolean isIsCollect() {
            return isCollect;
        }

        public void setIsCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getLookNum() {
            return lookNum;
        }

        public void setLookNum(int lookNum) {
            this.lookNum = lookNum;
        }
    }
}
