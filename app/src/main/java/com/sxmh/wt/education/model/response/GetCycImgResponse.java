package com.sxmh.wt.education.model.response;

import java.util.List;

public class GetCycImgResponse {

    private List<CycleImgListBean> cycleImgList;

    public List<CycleImgListBean> getCycleImgList() {
        return cycleImgList;
    }

    public void setCycleImgList(List<CycleImgListBean> cycleImgList) {
        this.cycleImgList = cycleImgList;
    }

    public class CycleImgListBean {
        /**
         * st : 一级建造师
         * url : http://www.xuehuang.cn/Content_Stage/images/yijian1.jpg
         */

        private String st;
        private String url;

        public String getSt() {
            return st;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
