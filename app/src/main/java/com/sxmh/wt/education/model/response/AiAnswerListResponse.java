package com.sxmh.wt.education.model.response;

import java.util.List;

public class AiAnswerListResponse {

    private List<AnswerListBean> answerList;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AnswerListBean> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerListBean> answerList) {
        this.answerList = answerList;
    }

    public static class AnswerListBean {
        /**
         * id : 40288025650e295d01650e2a82fa0003
         * answer : 2018年一级建造师考试时间为9月15、16日。
         * question : 一建什么时候考试
         */

        private String id;
        private String answer;
        private String question;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }
}
