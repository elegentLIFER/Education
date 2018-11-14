package com.sxmh.wt.education.model.response;

public class CodeVerifyResponse {

    /**
     * result : false
     * msg : 验证码错误或超时
     */

    private boolean result;
    private String msg;
    /**
     * memberId : e7ff2bc57a8a40c3b22b6a6c7fd364a1
     * memberName : wt
     */

    private String memberId;
    private String memberName;

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
}
