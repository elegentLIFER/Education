package com.sxmh.wt.education.model.response.live;

import java.util.List;

public class LiveRoomListResponse {

    private List<HotLiveListBean> hotLiveList;
    private List<LiveListBean> liveList;

    public List<HotLiveListBean> getHotLiveList() {
        return hotLiveList;
    }

    public void setHotLiveList(List<HotLiveListBean> hotLiveList) {
        this.hotLiveList = hotLiveList;
    }

    public List<LiveListBean> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<LiveListBean> liveList) {
        this.liveList = liveList;
    }

    public static class HotLiveListBean {
        /**
         * id : 2c92908d64fb5b100164fd3267080003
         * liveEndTime : 10:00
         * liveBeginTime : 08:30
         * roomName : 二建法规
         * liveImage :
         * liveBeginDate : 2018-08-03
         */

        private String id;
        private String liveEndTime;
        private String liveBeginTime;
        private String roomName;
        private String liveImage;
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

        public String getLiveImage() {
            return liveImage;
        }

        public void setLiveImage(String liveImage) {
            this.liveImage = liveImage;
        }

        public String getLiveBeginDate() {
            return liveBeginDate;
        }

        public void setLiveBeginDate(String liveBeginDate) {
            this.liveBeginDate = liveBeginDate;
        }
    }

    public static class LiveListBean {
        /**
         * id : 2c92908d6522c35f016523058a450004
         * liveEndTime : 16:00
         * liveBeginTime : 15:00
         * roomName : room1
         * liveImage :
         * teacher : liu
         * liveBeginDate : 2018-08-11
         */

        private String id;
        private String liveEndTime;
        private String liveBeginTime;
        private String roomName;
        private String liveImage;
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

        public String getLiveImage() {
            return liveImage;
        }

        public void setLiveImage(String liveImage) {
            this.liveImage = liveImage;
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
