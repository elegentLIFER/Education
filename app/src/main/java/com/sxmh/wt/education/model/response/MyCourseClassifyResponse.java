package com.sxmh.wt.education.model.response;

import java.util.List;

public class MyCourseClassifyResponse {

    private List<CourseClassListBean> courseClassList;

    public List<CourseClassListBean> getCourseClassList() {
        return courseClassList;
    }

    public void setCourseClassList(List<CourseClassListBean> courseClassList) {
        this.courseClassList = courseClassList;
    }

    public static class CourseClassListBean {
        /**
         * id : 2c92908d5c1be42b015c2dce4f11000c
         * courseClassName : 管理
         * netCourseList : [{"courseClassId":"2c92908d5c1be42b015c2dce4f11000c","courseClassName":"管理","netCourseId":"2c92908d63ab58e00163ae8896d10022","litimg":"","netCourseName":"2018一建管理基础精讲班"},{"courseClassId":"2c92908d5c1be42b015c2dce4f11000c","courseClassName":"管理","netCourseId":"2c92908d6521d6a1016531c754e4038c","litimg":"","netCourseName":"2018一建管理冲刺预测班"},{"courseClassId":"2c92908d5c1be42b015c2dce4f11000c","courseClassName":"管理","netCourseId":"2c92908d6541404301655aff9d810b5d","litimg":"","netCourseName":"2018一建管理习题解析班"},{"courseClassId":"2c92908d5c1be42b015c2dce4f11000c","courseClassName":"管理","netCourseId":"2c92908d654140430165834a739c18e0","litimg":"","netCourseName":"2018一建管理答题技巧班"}]
         * litimg : null
         */

        private String id;
        private String courseClassName;
        private Object litimg;
        private List<NetCourseListBean> netCourseList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public Object getLitimg() {
            return litimg;
        }

        public void setLitimg(Object litimg) {
            this.litimg = litimg;
        }

        public List<NetCourseListBean> getNetCourseList() {
            return netCourseList;
        }

        public void setNetCourseList(List<NetCourseListBean> netCourseList) {
            this.netCourseList = netCourseList;
        }

        public static class NetCourseListBean {
            /**
             * courseClassId : 2c92908d5c1be42b015c2dce4f11000c
             * courseClassName : 管理
             * netCourseId : 2c92908d63ab58e00163ae8896d10022
             * litimg :
             * netCourseName : 2018一建管理基础精讲班
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
