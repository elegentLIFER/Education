package com.sxmh.wt.education.model.response.lesson;

import java.io.Serializable;
import java.util.List;

public class NetCourseResponse {

    private List<NetCourseListBean> netCourseList;

    public List<NetCourseListBean> getNetCourseList() {
        return netCourseList;
    }

    public void setNetCourseList(List<NetCourseListBean> netCourseList) {
        this.netCourseList = netCourseList;
    }

    public static class NetCourseListBean implements Serializable{
        /**
         * id : 2c92908d63ab58e00163ae8fab68002c
         * state : 2
         * catalogId : 2c92908d63ab58e00163ae88cea80026
         * litimg : http://120.77.242.84:8089/xhms/userfiles/files/AppFiles/image/视频.jpg
         * isCollect : false
         * teacher : 宿吉南
         * netCourseName : 01、18一建管理精讲第一章（1）（2）-1
         */

        private String id;
        private int state;
        private String catalogId;
        private String litimg;
        private boolean isCollect;
        private String teacher;
        private String netCourseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }

        public String getLitimg() {
            return litimg;
        }

        public void setLitimg(String litimg) {
            this.litimg = litimg;
        }

        public boolean isIsCollect() {
            return isCollect;
        }

        public void setIsCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getNetCourseName() {
            return netCourseName;
        }

        public void setNetCourseName(String netCourseName) {
            this.netCourseName = netCourseName;
        }
    }
}
