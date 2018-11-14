package com.sxmh.wt.education.model.response;

import java.io.Serializable;
import java.util.List;

public class MessageListResponse {

    private List<InformListBean> informList;

    public List<InformListBean> getInformList() {
        return informList;
    }

    public void setInformList(List<InformListBean> informList) {
        this.informList = informList;
    }

    public static class InformListBean implements Serializable{
        /**
         * id : 2c92908d65ec1d8f0165efb0a54e0003
         * informContent : <html>
         <head>
         <title></title>
         </head>
         <body>dfasfdsfasdfad<img alt="cool" height="20" src="http://120.77.242.84:8083/xhms/plug-in/ckeditor/plugins/smiley/images/shades_smile.gif" title="cool" width="20" /></body>
         </html>

         * createDate : 2018-09-19 10:37:36
         * informTitle : fdsafasdfsa
         */

        private String id;
        private String informContent;
        private String createDate;
        private String informTitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInformContent() {
            return informContent;
        }

        public void setInformContent(String informContent) {
            this.informContent = informContent;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getInformTitle() {
            return informTitle;
        }

        public void setInformTitle(String informTitle) {
            this.informTitle = informTitle;
        }
    }
}
