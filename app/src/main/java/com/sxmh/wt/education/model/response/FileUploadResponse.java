package com.sxmh.wt.education.model.response;

public class FileUploadResponse {

    /**
     * result : true
     * url : http: //www.xuehuang.cn//userFiles/appFiles/1540349406299.png
     */

    private boolean result;
    private String url;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
