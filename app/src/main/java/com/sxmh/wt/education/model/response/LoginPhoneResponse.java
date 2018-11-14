package com.sxmh.wt.education.model.response;

public class LoginPhoneResponse {

    /**
     * result : true
     * msg : 登录成功
     * memberId : e7ff2bc57a8a40c3b22b6a6c7fd364a1
     * memberName : wt
     * photo : http://120.77.242.84:8083/xhweb/userfiles/appFiles/1540432445236180-4.jpg
     * validTime : 1542270578385
     */

    private boolean result;
    private String msg;
    private String memberId;
    private String memberName;
    private String photo;
    private String validTime;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}
