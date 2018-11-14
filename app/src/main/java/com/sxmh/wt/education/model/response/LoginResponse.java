package com.sxmh.wt.education.model.response;

public class LoginResponse {

    /**
     * result : true
     * msg : 登录成功！
     * first : false
     * memberId : 12ac21c5b4cd4f1c9ce66b19e68d14a2
     * photo : http://www.xuehuang.cn//userFiles/appFiles/15409738474446ad6279999de10e6.jpg
     * validTime : 1542270403997
     */

    private boolean result;
    private String msg;
    private boolean first;
    private String memberId;
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

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
