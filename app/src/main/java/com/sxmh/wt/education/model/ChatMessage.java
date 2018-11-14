package com.sxmh.wt.education.model;

import java.util.List;

public class ChatMessage {

    /**
     * type : chat
     * user : zhang5
     * time : 2018-8-20 14:46:48
     * chat : [{"type":"text","content":"1"}]
     */

    private String type;
    private String user;
    private String time;
    private List<ChatBean> chat;
    private boolean isMySend;

    public boolean isMySend() {
        return isMySend;
    }

    public void setMySend(boolean mySend) {
        isMySend = mySend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ChatBean> getChat() {
        return chat;
    }

    public void setChat(List<ChatBean> chat) {
        this.chat = chat;
    }

    public static class ChatBean {
        /**
         * type : text
         * content : 1
         */

        private String type;
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
