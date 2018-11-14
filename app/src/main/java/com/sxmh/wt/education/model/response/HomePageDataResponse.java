package com.sxmh.wt.education.model.response;

import java.io.Serializable;
import java.util.List;

public class HomePageDataResponse {

    private List<HotNetCourseListBean> hotNetCourseList;
    private List<RecomNetCourseListBean> recomNetCourseList;
    private List<TopInformListBean> topInformList;

    public List<HotNetCourseListBean> getHotNetCourseList() {
        return hotNetCourseList;
    }

    public void setHotNetCourseList(List<HotNetCourseListBean> hotNetCourseList) {
        this.hotNetCourseList = hotNetCourseList;
    }

    public List<RecomNetCourseListBean> getRecomNetCourseList() {
        return recomNetCourseList;
    }

    public void setRecomNetCourseList(List<RecomNetCourseListBean> recomNetCourseList) {
        this.recomNetCourseList = recomNetCourseList;
    }

    public List<TopInformListBean> getTopInformList() {
        return topInformList;
    }

    public void setTopInformList(List<TopInformListBean> topInformList) {
        this.topInformList = topInformList;
    }

    public static class HotNetCourseListBean implements Serializable{
        /**
         * id : 2c92908d64a926b20164cf0b23f507ad
         * litimg : http://120.77.242.84:8089/xhms/userfiles/files/AppFiles/image/视频.jpg
         * courseTeacher : 宿吉南
         * netCourseName : 消防安全技术实务（备考导学班）-1
         * loohNum : 1895
         */

        private String id;
        private String litimg;
        private String courseTeacher;
        private String netCourseName;
        private String loohNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLitimg() {
            return litimg;
        }

        public void setLitimg(String litimg) {
            this.litimg = litimg;
        }

        public String getCourseTeacher() {
            return courseTeacher;
        }

        public void setCourseTeacher(String courseTeacher) {
            this.courseTeacher = courseTeacher;
        }

        public String getNetCourseName() {
            return netCourseName;
        }

        public void setNetCourseName(String netCourseName) {
            this.netCourseName = netCourseName;
        }

        public String getLoohNum() {
            return loohNum;
        }

        public void setLoohNum(String loohNum) {
            this.loohNum = loohNum;
        }
    }

    public static class RecomNetCourseListBean {
        /**
         * id : 2c92908d60fd51d9016111fb10f505bb
         * litimg : http://120.77.242.84:8089/xhms/userfiles/files/AppFiles/image/视频.jpg
         * netCourseName : 01、施工方的项目管理-1
         */

        private String id;
        private String litimg;
        private String netCourseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLitimg() {
            return litimg;
        }

        public void setLitimg(String litimg) {
            this.litimg = litimg;
        }

        public String getNetCourseName() {
            return netCourseName;
        }

        public void setNetCourseName(String netCourseName) {
            this.netCourseName = netCourseName;
        }
    }

    public static class TopInformListBean {
        /**
         * id : 4028408165d0a5920165d0cd1a3b0012
         * title : 学煌教育APP上线了
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
