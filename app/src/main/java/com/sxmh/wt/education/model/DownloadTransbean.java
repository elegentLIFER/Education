package com.sxmh.wt.education.model;


//data class DownloadTransbean(var courseName: String, var id: String, var imgUrl: String):Serializable

import java.io.Serializable;

public class DownloadTransbean implements Serializable{
    private String courseName;
    private String id;
    private String imgUrl;

    public DownloadTransbean(String courseName, String id, String imgUrl) {
        this.courseName = courseName;
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}