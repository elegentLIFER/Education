package com.sxmh.wt.education.model.response;

import java.util.List;

public class SearchResponse {

    private List<NetCourseListBean> netCourseList;
    private List<PaperQuestionListBean> paperQuestionList;
    private List<LiveRoomListBean> liveRoomList;
    private List<NetProblemListBean> netProblemList;

    public List<NetCourseListBean> getNetCourseList() {
        return netCourseList;
    }

    public void setNetCourseList(List<NetCourseListBean> netCourseList) {
        this.netCourseList = netCourseList;
    }

    public List<PaperQuestionListBean> getPaperQuestionList() {
        return paperQuestionList;
    }

    public void setPaperQuestionList(List<PaperQuestionListBean> paperQuestionList) {
        this.paperQuestionList = paperQuestionList;
    }

    public List<LiveRoomListBean> getLiveRoomList() {
        return liveRoomList;
    }

    public void setLiveRoomList(List<LiveRoomListBean> liveRoomList) {
        this.liveRoomList = liveRoomList;
    }

    public List<NetProblemListBean> getNetProblemList() {
        return netProblemList;
    }

    public void setNetProblemList(List<NetProblemListBean> netProblemList) {
        this.netProblemList = netProblemList;
    }

    public static class NetCourseListBean {
        /**
         * id : 2c92908d63ab58e00163ae8fab68002c
         * catalogName : 一级建造师-管理
         * state : 2
         * netCourseName : 01、18一建管理精讲第一章（1）（2）-1
         */

        private String id;
        private String catalogName;
        private int state;
        private String netCourseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getNetCourseName() {
            return netCourseName;
        }

        public void setNetCourseName(String netCourseName) {
            this.netCourseName = netCourseName;
        }
    }

    public static class PaperQuestionListBean {
        /**
         * id : 2c92908d5c2e7ccc015c2eefb49603c5
         * paperName : 一建法规
         * questionTitle : <html>
         <head>
         <title></title>
         </head>
         <body>甲家旁边有一建筑工地正在施工，某日，一货车经过甲家门前，由于颠簸掉落货物一件，被甲拾得据为己有。其后，甲发现有利可图，遂在门前洒落许多砖石，次日，果然又拾得两袋车上倾落的货包，关于甲行为性质的说法，正确的有（）。</body>
         </html>

         */

        private String id;
        private String paperName;
        private String questionTitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPaperName() {
            return paperName;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }

        public void setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
        }
    }

    public static class LiveRoomListBean {
        /**
         * id : 2c92908d66536a2d01665cfedaab0009
         * watchPri : 0
         * liveEndTime : 18:00
         * liveBeginTime : 15:00
         * roomName : 一建管理
         * courseClassName : 一级建造师
         * liveBeginDate : 2018-10-10
         */

        private String id;
        private int watchPri;
        private String liveEndTime;
        private String liveBeginTime;
        private String roomName;
        private String courseClassName;
        private String liveBeginDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getWatchPri() {
            return watchPri;
        }

        public void setWatchPri(int watchPri) {
            this.watchPri = watchPri;
        }

        public String getLiveEndTime() {
            return liveEndTime;
        }

        public void setLiveEndTime(String liveEndTime) {
            this.liveEndTime = liveEndTime;
        }

        public String getLiveBeginTime() {
            return liveBeginTime;
        }

        public void setLiveBeginTime(String liveBeginTime) {
            this.liveBeginTime = liveBeginTime;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getLiveBeginDate() {
            return liveBeginDate;
        }

        public void setLiveBeginDate(String liveBeginDate) {
            this.liveBeginDate = liveBeginDate;
        }
    }

    public static class NetProblemListBean {
        /**
         * id : 0b0415bf9f914d44815e7b1edb7b37be
         * proTitle : 2018年一建实务案例班专题五（项目合同与成本管理）
         * courseClassName : 一级建造师-建筑
         */

        private String id;
        private String proTitle;
        private String courseClassName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProTitle() {
            return proTitle;
        }

        public void setProTitle(String proTitle) {
            this.proTitle = proTitle;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }
    }
}
