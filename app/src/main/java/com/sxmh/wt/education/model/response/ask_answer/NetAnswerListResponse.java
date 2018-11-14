package com.sxmh.wt.education.model.response.ask_answer;

import java.util.List;

public class NetAnswerListResponse {

    private List<NetAnswerListBean> netAnswerList;

    public List<NetAnswerListBean> getNetAnswerList() {
        return netAnswerList;
    }

    public void setNetAnswerList(List<NetAnswerListBean> netAnswerList) {
        this.netAnswerList = netAnswerList;
    }

    public static class NetAnswerListBean {
        /**
         * answerContent : <p style="margin-top: 10px; margin-bottom: 10px; padding: 0px; line-height: 24px; font-family: 宋体, arial, Helvetica, Tahoma, sans-serif; font-size: 14px; white-space: normal;">【正确答案】ABCE</p><p style="margin-top: 10px; margin-bottom: 10px; padding: 0px; line-height: 24px; font-family: 宋体, arial, Helvetica, Tahoma, sans-serif; font-size: 14px; white-space: normal;">　　【答案解析】本题考查的是工程项目管理信息系统的功能。成本控制的功能：（1）投标估算的数据计算和分析；（2）计划施工成本；（3）计算实际成本；（4）计划成本与实际成本的比较分析；（5）根据工程的进展进行施工成本预测等。</p><p><br/></p>
         * createDate : 2017-27-05  09:27:09
         * photo : http://120.77.242.84:8083/xhweb/userfiles/appFiles/1537168749103.png
         * createName : Test6
         * userType : 0
         */

        private String answerContent;
        private String createDate;
        private String photo;
        private String createName;
        private int userType;

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
