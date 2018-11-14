package com.sxmh.wt.education.model.response;

import java.util.List;

public class CollectionPaperListResponse {

    private List<QuestionListBean> questionList;

    public List<QuestionListBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionListBean> questionList) {
        this.questionList = questionList;
    }

    public static class QuestionListBean {
        /**
         * id : 2c92908d5c2f6c9b015c331eaa511086
         * questionTitle : 采用施工承包管理模式，一般情况下，（）。
         */

        private String id;
        private String questionTitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }

        public void setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
        }
    }
}
