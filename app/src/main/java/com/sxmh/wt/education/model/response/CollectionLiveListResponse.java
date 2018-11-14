package com.sxmh.wt.education.model.response;

import java.util.List;

public class CollectionLiveListResponse {

    private List<LiveRoomListBean> liveRoomList;

    public List<LiveRoomListBean> getLiveRoomList() {
        return liveRoomList;
    }

    public void setLiveRoomList(List<LiveRoomListBean> liveRoomList) {
        this.liveRoomList = liveRoomList;
    }

    public static class LiveRoomListBean {
        /**
         * id : 2c92908d66536a2d01665cfedaab0009
         * liveEndTime : 18:00
         * liveBeginTime : 15:00
         * roomName : 一建管理
         * teacher : 苏济南
         * liveBeginDate : 2018-10-10
         */

        private String id;
        private String liveEndTime;
        private String liveBeginTime;
        private String roomName;
        private String teacher;
        private String liveBeginDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getLiveBeginDate() {
            return liveBeginDate;
        }

        public void setLiveBeginDate(String liveBeginDate) {
            this.liveBeginDate = liveBeginDate;
        }
    }
}
