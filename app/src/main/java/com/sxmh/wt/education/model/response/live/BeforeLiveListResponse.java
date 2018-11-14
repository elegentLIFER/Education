package com.sxmh.wt.education.model.response.live;

import java.util.List;

public class BeforeLiveListResponse {

    /**
     * netCourseList : [{"id":"2c92908d62096dc3016227d55b5601fc","createDate":"2018-05-15  12:05:17","netCourseName":"02、2Z201000 建设工程基本法律知识 -2"},{"id":"2c92908d62096dc3016227d5d14201ff","createDate":"2018-05-15  12:05:47","netCourseName":"03、2Z201000 建设工程基本法律知识- 3"},{"id":"2c92908d62096dc3016227d631250201","createDate":"2018-06-15  12:06:12","netCourseName":"04、2Z202000 施工许可法律制度"},{"id":"2c92908d62096dc3016227d68e780203","createDate":"2018-06-15  12:06:35","netCourseName":"05、2Z203000 建设工程发承包制度 1"},{"id":"2c92908d62096dc3016227d6f2d00205","createDate":"2018-07-15  12:07:01","netCourseName":"06、2Z203000 建设工程发承包制度 2"},{"id":"2c92908d62918c1a0162dbf7b5ca0694","createDate":"2018-34-19  11:34:27","netCourseName":"08、18二建市政冲刺预测班-8"},{"id":"2c92908d62918c1a0162dbf81e7e0696","createDate":"2018-34-19  11:34:54","netCourseName":"09、18二建市政冲刺预测班-9"},{"id":"2c92908d62918c1a0162dbf88fd80698","createDate":"2018-35-19  11:35:23","netCourseName":"10、18二建市政冲刺预测班-10"},{"id":"2c92908d62918c1a0162dbf8fa75069a","createDate":"2018-35-19  11:35:50","netCourseName":"11、18二建市政冲刺预测班-11"}]
     * count : 0
     */

    private int count;
    private List<NetCourseListBean> netCourseList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NetCourseListBean> getNetCourseList() {
        return netCourseList;
    }

    public void setNetCourseList(List<NetCourseListBean> netCourseList) {
        this.netCourseList = netCourseList;
    }

    public static class NetCourseListBean {
        /**
         * id : 2c92908d62096dc3016227d55b5601fc
         * createDate : 2018-05-15  12:05:17
         * netCourseName : 02、2Z201000 建设工程基本法律知识 -2
         */

        private String id;
        private String createDate;
        private String netCourseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getNetCourseName() {
            return netCourseName;
        }

        public void setNetCourseName(String netCourseName) {
            this.netCourseName = netCourseName;
        }
    }
}
