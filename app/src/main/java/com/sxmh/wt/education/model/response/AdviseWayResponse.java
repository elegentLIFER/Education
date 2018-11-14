package com.sxmh.wt.education.model.response;

import java.util.List;

public class AdviseWayResponse {

    private List<AdvisoryWayListBean> advisoryWayList;

    public List<AdvisoryWayListBean> getAdvisoryWayList() {
        return advisoryWayList;
    }

    public void setAdvisoryWayList(List<AdvisoryWayListBean> advisoryWayList) {
        this.advisoryWayList = advisoryWayList;
    }

    public static class AdvisoryWayListBean {
        /**
         * advisoryType : 0
         * number : 2885030819
         * wayType : 0
         */

        private int advisoryType;
        private String number;
        private int wayType;

        public int getAdvisoryType() {
            return advisoryType;
        }

        public void setAdvisoryType(int advisoryType) {
            this.advisoryType = advisoryType;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getWayType() {
            return wayType;
        }

        public void setWayType(int wayType) {
            this.wayType = wayType;
        }
    }
}
