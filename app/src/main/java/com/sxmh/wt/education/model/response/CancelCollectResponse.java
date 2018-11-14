package com.sxmh.wt.education.model.response;

public class CancelCollectResponse {

    /**
     * result : true
     * msg : 取消成功
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
