package com.sxmh.wt.education.model.response;

public class NotificationContentResponse {

    /**
     * title : true
     * createDate : 2018-7-23
     * details : 一级建造师报名了一级建造师报名了一级建造师报名了一级建造师报名了一级建造师报名了一级建造师报名了
     */

    private boolean title;
    private String createDate;
    private String details;

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
