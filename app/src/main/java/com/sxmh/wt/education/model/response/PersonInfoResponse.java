package com.sxmh.wt.education.model.response;

public class PersonInfoResponse {

    /**
     * id : 12ac21c5b4cd4f1c9ce66b19e68d14a2
     * accounts : test1
     * photo :
     * phoneBind : 15802701251
     * emailBind :
     * company :
     * position :
     * state : 1
     */

    private String id;
    private String accounts;
    private String photo;
    private String phoneBind;
    private String emailBind;
    private String company;
    private String position;
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneBind() {
        return phoneBind;
    }

    public void setPhoneBind(String phoneBind) {
        this.phoneBind = phoneBind;
    }

    public String getEmailBind() {
        return emailBind;
    }

    public void setEmailBind(String emailBind) {
        this.emailBind = emailBind;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
