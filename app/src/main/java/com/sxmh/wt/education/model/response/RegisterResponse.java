package com.sxmh.wt.education.model.response;

public class RegisterResponse {

    /**
     * result : false
     * msg : 注册失败！
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
