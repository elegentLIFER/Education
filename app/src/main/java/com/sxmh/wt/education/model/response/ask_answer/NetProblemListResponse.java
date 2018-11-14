package com.sxmh.wt.education.model.response.ask_answer;

import java.util.List;

public class NetProblemListResponse {

    /**
     * netProblemList : [{"id":"1ce952e5a2324914948ee6ea5731c5d5","problemState":0,"courseClassName":"管理","createDate":"2018-11-06 17:26:54","photo":"http://www.xuehuang.cn//userFiles/appFiles/1541487561844.png","createName":"test1","problemTitle":"测试"}]
     * isExceedViewPageNum : false
     */

    private boolean isExceedViewPageNum;
    private List<NetProblemListBean> netProblemList;

    public boolean isIsExceedViewPageNum() {
        return isExceedViewPageNum;
    }

    public void setIsExceedViewPageNum(boolean isExceedViewPageNum) {
        this.isExceedViewPageNum = isExceedViewPageNum;
    }

    public List<NetProblemListBean> getNetProblemList() {
        return netProblemList;
    }

    public void setNetProblemList(List<NetProblemListBean> netProblemList) {
        this.netProblemList = netProblemList;
    }

    public static class NetProblemListBean {
        /**
         * id : 1ce952e5a2324914948ee6ea5731c5d5
         * problemState : 0
         * courseClassName : 管理
         * createDate : 2018-11-06 17:26:54
         * photo : http://www.xuehuang.cn//userFiles/appFiles/1541487561844.png
         * createName : test1
         * problemTitle : 测试
         */

        private String id;
        private int problemState;
        private String courseClassName;
        private String createDate;
        private String photo;
        private String createName;
        private String problemTitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getProblemState() {
            return problemState;
        }

        public void setProblemState(int problemState) {
            this.problemState = problemState;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getProblemTitle() {
            return problemTitle;
        }

        public void setProblemTitle(String problemTitle) {
            this.problemTitle = problemTitle;
        }
    }
}
