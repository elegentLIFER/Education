package com.sxmh.wt.education.model.response.lesson;

import java.util.List;

public class NetCourseListResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private List<NetCourseListBean> netCourseList;

        public List<NetCourseListBean> getNetCourseList() {
            return netCourseList;
        }

        public void setNetCourseList(List<NetCourseListBean> netCourseList) {
            this.netCourseList = netCourseList;
        }

        public static class NetCourseListBean {
            /**
             * courseClassId : 2c92908d5c33d9b7015c383e317c0d8b
             * courseClassName : 消防安全案例分析
             * netCourseId : 2c92908d64a926b20164c618826b0613
             * litimg :
             * netCourseName : 2018一消案例基础精讲班
             */

            private String courseClassId;
            private String courseClassName;
            private String netCourseId;
            private String litimg;
            private String netCourseName;

            public String getCourseClassId() {
                return courseClassId;
            }

            public void setCourseClassId(String courseClassId) {
                this.courseClassId = courseClassId;
            }

            public String getCourseClassName() {
                return courseClassName;
            }

            public void setCourseClassName(String courseClassName) {
                this.courseClassName = courseClassName;
            }

            public String getNetCourseId() {
                return netCourseId;
            }

            public void setNetCourseId(String netCourseId) {
                this.netCourseId = netCourseId;
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
    }
}
