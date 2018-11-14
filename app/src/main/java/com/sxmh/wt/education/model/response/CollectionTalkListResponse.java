package com.sxmh.wt.education.model.response;

import java.util.List;

public class CollectionTalkListResponse {

    private List<NetCourseListBean> netCourseList;

    public List<NetCourseListBean> getNetCourseList() {
        return netCourseList;
    }

    public void setNetCourseList(List<NetCourseListBean> netCourseList) {
        this.netCourseList = netCourseList;
    }

    public static class NetCourseListBean {
        /**
         * id : 2c92908d64f4d63301650dcd607d040e
         * litimg :
         * courseTeacher :
         * courseName : 14、1Z306000建设工程安全生产法律制度-2
         */

        private String id;
        private String litimg;
        private String courseTeacher;
        private String courseName;

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

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
