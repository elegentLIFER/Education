package com.sxmh.wt.education.model.response.ask_answer;

import java.util.List;

public class NetProblemClassResponse {

    /**
     * netProblemClassList : [{"queCount":14,"netProblemClassName":"管理","netProblemClassId":"2c92908d5c1be42b015c2dce4f11000c"},{"queCount":7,"netProblemClassName":"法规","netProblemClassId":"2c92908d5c1be42b015c2dcc4c930009"},{"queCount":12,"netProblemClassName":"经济","netProblemClassId":"2c92908d5c1be42b015c2dce5940000e"},{"queCount":6,"netProblemClassName":"建筑","netProblemClassId":"2c92908d5c1be42b015c2dd19a0a0010"},{"queCount":6,"netProblemClassName":"机电","netProblemClassId":"2c92908d5c2dff05015c2e00c88d0003"},{"queCount":10,"netProblemClassName":"市政","netProblemClassId":"ff8080815b96bc5e015b9bd20b170106"},{"queCount":8,"netProblemClassName":"公路","netProblemClassId":"ff8080815b96bc5e015b9bd26dcc0108"},{"queCount":6,"netProblemClassName":"水利","netProblemClassId":"2c92908d5c470312015c4784d7890137"},{"queCount":5,"netProblemClassName":"矿业","netProblemClassId":"2c92908d5c4036cd015c42a3cf770e92"},{"queCount":5,"netProblemClassName":"通信","netProblemClassId":"2c92908d5c470312015c49432ec904ee"},{"queCount":6,"netProblemClassName":"港航","netProblemClassId":"2c92908d5c470312015c491bed180432"},{"queCount":7,"netProblemClassName":"民航","netProblemClassId":"2c92908d5c470312015c475e13280005"},{"queCount":6,"netProblemClassName":"铁路","netProblemClassId":"2c92908d5c4036cd015c44082f781168"}]
     * totalCount : 98
     */

    private int totalCount;
    private List<NetProblemClassListBean> netProblemClassList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<NetProblemClassListBean> getNetProblemClassList() {
        return netProblemClassList;
    }

    public void setNetProblemClassList(List<NetProblemClassListBean> netProblemClassList) {
        this.netProblemClassList = netProblemClassList;
    }

    public static class NetProblemClassListBean {
        /**
         * queCount : 14
         * netProblemClassName : 管理
         * netProblemClassId : 2c92908d5c1be42b015c2dce4f11000c
         */

        private int queCount;
        private String netProblemClassName;
        private String netProblemClassId;

        public int getQueCount() {
            return queCount;
        }

        public void setQueCount(int queCount) {
            this.queCount = queCount;
        }

        public String getNetProblemClassName() {
            return netProblemClassName;
        }

        public void setNetProblemClassName(String netProblemClassName) {
            this.netProblemClassName = netProblemClassName;
        }

        public String getNetProblemClassId() {
            return netProblemClassId;
        }

        public void setNetProblemClassId(String netProblemClassId) {
            this.netProblemClassId = netProblemClassId;
        }
    }
}
