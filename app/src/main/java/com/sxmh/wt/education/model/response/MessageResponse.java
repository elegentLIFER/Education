package com.sxmh.wt.education.model.response;

import java.util.List;

public class MessageResponse {

    /**
     * informList : [{"id":"ff8080815b96bc5e015b9bd171800104","createDate":"2018-07-22","informTitle":"一建降价了"},{"id":"ff8080815b96bc5e015b9bd171800104","createDate":"2018-07-22","informTitle":"一建降价了"},{"id":"ff8080815b96bc5e015b9bd171800104","createDate":"2018-07-22","informTitle":"一建降价了"},{"id":"ff8080815b96bc5e015b9bd171800104","createDate":"2018-07-22","informTitle":"一建降价了"}]
     * informSum : 30
     */

    private int informSum;
    private List<InformListBean> informList;

    public int getInformSum() {
        return informSum;
    }

    public void setInformSum(int informSum) {
        this.informSum = informSum;
    }

    public List<InformListBean> getInformList() {
        return informList;
    }

    public void setInformList(List<InformListBean> informList) {
        this.informList = informList;
    }

    public static class InformListBean {
        /**
         * id : ff8080815b96bc5e015b9bd171800104
         * createDate : 2018-07-22
         * informTitle : 一建降价了
         */

        private String id;
        private String createDate;
        private String informTitle;

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

        public String getInformTitle() {
            return informTitle;
        }

        public void setInformTitle(String informTitle) {
            this.informTitle = informTitle;
        }
    }
}
