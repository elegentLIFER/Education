package com.sxmh.wt.education.model.response;

import java.util.List;

public class LessonTypeResponse {

    private List<CourseClassListBean> courseClassList;

    public List<CourseClassListBean> getCourseClassList() {
        return courseClassList;
    }

    public void setCourseClassList(List<CourseClassListBean> courseClassList) {
        this.courseClassList = courseClassList;
    }

    public static class CourseClassListBean {
        /**
         * id : ff8080815b96bc5e015b9bd171800104
         * courseClassName : 一级建造师
         */

        private String id;
        private String courseClassName;

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
    }
}
