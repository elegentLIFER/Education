package com.sxmh.wt.education.model.response;

import java.io.Serializable;
import java.util.List;

public class PaperCatalogResponse {

    private List<PaperCatalogListBean> paperCatalogList;

    public List<PaperCatalogListBean> getPaperCatalogList() {
        return paperCatalogList;
    }

    public void setPaperCatalogList(List<PaperCatalogListBean> paperCatalogList) {
        this.paperCatalogList = paperCatalogList;
    }

    public static class PaperCatalogListBean implements Serializable{
        /**
         * starLevel : 4
         * id : 2c92908d5c33d9b7015c384aa2c70f32
         * pId : null
         * didCount : 0
         * catalogName : 一级消防工程师技术实务模拟一
         * count : 20
         * teacher :
         * memberNum : 0
         */

        private int starLevel;
        private String id;
        private Object pId;
        private int didCount;
        private String catalogName;
        private int count;
        private String teacher;
        private int memberNum;

        public int getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(int starLevel) {
            this.starLevel = starLevel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPId() {
            return pId;
        }

        public void setPId(Object pId) {
            this.pId = pId;
        }

        public int getDidCount() {
            return didCount;
        }

        public void setDidCount(int didCount) {
            this.didCount = didCount;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }
    }
}
