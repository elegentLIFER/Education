package com.sxmh.wt.education.model.response.questionlib;

public class DoQuesPowerResponse {

    /**
     * result : false
     * msg : 您好！您处于未登录状态，请先登录！
     */

    private boolean result;
    private String msg;

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
}
