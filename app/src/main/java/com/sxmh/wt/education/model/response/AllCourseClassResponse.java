package com.sxmh.wt.education.model.response;

import java.util.List;

public class AllCourseClassResponse {

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
         * pId : ff8080815b96bc5e015b9bd171800104
         * courseClassLevel : 2
         * courseClassName : 管理
         * litimg : null
         */

        private String id;
        private String pId;
        private String courseClassLevel;
        private String courseClassName;
        private Object litimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getCourseClassLevel() {
            return courseClassLevel;
        }

        public void setCourseClassLevel(String courseClassLevel) {
            this.courseClassLevel = courseClassLevel;
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
    }
}
