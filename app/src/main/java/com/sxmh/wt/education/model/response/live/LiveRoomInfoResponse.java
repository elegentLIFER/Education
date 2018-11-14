package com.sxmh.wt.education.model.response.live;

import java.io.Serializable;

public class LiveRoomInfoResponse implements Serializable {

    /**
     * id : 2c92908d66536a2d01665cfedaab0009
     * romeCode : 001
     * roomName : 一建管理
     * stuContent :
     * teacher : 苏济南
     * teacherIntro : 国家一级建造师，讲课风趣幽默，授课直击考点，能真正帮助学员吸收重点难点。
     * teacherImage : http://120.77.242.84:8089/xhms/userfiles/files/AppFiles/teacherFiles/宿吉南.png
     * courseClassId : ff8080815b96bc5e015b9bd171800104
     * screenUrl : rtmp://live.pinabc.cn/AppName/001-screen
     * cameraUrl : rtmp://live.pinabc.cn/AppName/001-camera
     * isShowCamera : false
     * chatUrl : ws://120.77.242.84:8000/chat/001
     * isCollect : false
     * isAppoint : false
     */

    private String id;
    private String romeCode;
    private String roomName;
    private String stuContent;
    private String teacher;
    private String teacherIntro;
    private String teacherImage;
    private String courseClassId;
    private String screenUrl;
    private String cameraUrl;
    private boolean isShowCamera;
    private String chatUrl;
    private boolean isCollect;
    private boolean isAppoint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRomeCode() {
        return romeCode;
    }

    public void setRomeCode(String romeCode) {
        this.romeCode = romeCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStuContent() {
        return stuContent;
    }

    public void setStuContent(String stuContent) {
        this.stuContent = stuContent;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherIntro() {
        return teacherIntro;
    }

    public void setTeacherIntro(String teacherIntro) {
        this.teacherIntro = teacherIntro;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getCourseClassId() {
        return courseClassId;
    }

    public void setCourseClassId(String courseClassId) {
        this.courseClassId = courseClassId;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public String getCameraUrl() {
        return cameraUrl;
    }

    public void setCameraUrl(String cameraUrl) {
        this.cameraUrl = cameraUrl;
    }

    public boolean isIsShowCamera() {
        return isShowCamera;
    }

    public void setIsShowCamera(boolean isShowCamera) {
        this.isShowCamera = isShowCamera;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public boolean isIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public boolean isIsAppoint() {
        return isAppoint;
    }

    public void setIsAppoint(boolean isAppoint) {
        this.isAppoint = isAppoint;
    }
}
