package com.sxmh.wt.education.model.response;

import java.io.Serializable;
import java.util.List;

public class MyWrongsListResponse {

    private List<PaperCatalogListBean> paperCatalogList;

    public List<PaperCatalogListBean> getPaperCatalogList() {
        return paperCatalogList;
    }

    public void setPaperCatalogList(List<PaperCatalogListBean> paperCatalogList) {
        this.paperCatalogList = paperCatalogList;
    }

    public static class PaperCatalogListBean implements Serializable{
        /**
         * id : 2c92908d5c39e169015c3d605ca50457
         * catalogName : 2K311010城镇道路工程结构与材料
         * paperId : 2c92908d5c39e169015c3d5b469a0417
         * checkMany : 2
         * checkOne : 13
         */

        private String id;
        private String catalogName;
        private String paperId;
        private int checkMany;
        private int checkOne;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public void setCatalogName(String catalogName) {
            this.catalogName = catalogName;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public int getCheckMany() {
            return checkMany;
        }

        public void setCheckMany(int checkMany) {
            this.checkMany = checkMany;
        }

        public int getCheckOne() {
            return checkOne;
        }

        public void setCheckOne(int checkOne) {
            this.checkOne = checkOne;
        }
    }
}
