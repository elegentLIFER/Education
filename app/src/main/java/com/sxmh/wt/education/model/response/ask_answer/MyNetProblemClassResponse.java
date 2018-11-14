package com.sxmh.wt.education.model.response.ask_answer;

import java.util.List;

public class MyNetProblemClassResponse {

    /**
     * courseClassList : [{"id":"2c92908d5c1be42b015c2dce4f11000c","problemCount":0,"courseClassName":"管理"},{"id":"2c92908d5c1be42b015c2dcc4c930009","problemCount":0,"courseClassName":"法规"},{"id":"2c92908d5c1be42b015c2dce5940000e","problemCount":0,"courseClassName":"经济"},{"id":"2c92908d5c1be42b015c2dd19a0a0010","problemCount":0,"courseClassName":"建筑"},{"id":"2c92908d5c2dff05015c2e00c88d0003","problemCount":0,"courseClassName":"机电"},{"id":"ff8080815b96bc5e015b9bd20b170106","problemCount":0,"courseClassName":"市政"},{"id":"ff8080815b96bc5e015b9bd26dcc0108","problemCount":0,"courseClassName":"公路"},{"id":"2c92908d5c470312015c4784d7890137","problemCount":0,"courseClassName":"水利"},{"id":"2c92908d5c4036cd015c42a3cf770e92","problemCount":0,"courseClassName":"矿业"},{"id":"2c92908d5c470312015c49432ec904ee","problemCount":0,"courseClassName":"通信"},{"id":"2c92908d5c470312015c491bed180432","problemCount":0,"courseClassName":"港航"},{"id":"2c92908d5c470312015c475e13280005","problemCount":0,"courseClassName":"民航"},{"id":"2c92908d5c4036cd015c44082f781168","problemCount":0,"courseClassName":"铁路"}]
     * totalCount : 0
     */

    private int totalCount;
    private List<CourseClassListBean> courseClassList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CourseClassListBean> getCourseClassList() {
        return courseClassList;
    }

    public void setCourseClassList(List<CourseClassListBean> courseClassList) {
        this.courseClassList = courseClassList;
    }

    public static class CourseClassListBean {
        /**
         * id : 2c92908d5c1be42b015c2dce4f11000c
         * problemCount : 0
         * courseClassName : 管理
         */

        private String id;
        private int problemCount;
        private String courseClassName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getProblemCount() {
            return problemCount;
        }

        public void setProblemCount(int problemCount) {
            this.problemCount = problemCount;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }
    }
}
