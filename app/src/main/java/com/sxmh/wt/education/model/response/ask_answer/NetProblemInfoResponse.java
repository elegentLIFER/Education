package com.sxmh.wt.education.model.response.ask_answer;

public class NetProblemInfoResponse {

    /**
     * id : 767acf4571734d08ad2c0c41d3513ff7
     * courseClassName : 管理
     * problemTitle : 膜哦哦哦
     * problemContent : 膜西
     * problemState : 0
     * createName : Test6
     * photo : http://120.77.242.84:8083/xhweb/userfiles/appFiles/1537168749103.png
     * createDate : 2018-17-12  15:17:41
     */

    private String id;
    private String courseClassName;
    private String problemTitle;
    private String problemContent;
    private int problemState;
    private String createName;
    private String photo;
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseClassName() {
        return courseClassName;
    }

    public void setCourseClassName(String courseClassName) {
        this.courseClassName = courseClassName;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public int getProblemState() {
        return problemState;
    }

    public void setProblemState(int problemState) {
        this.problemState = problemState;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

