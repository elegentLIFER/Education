package com.sxmh.wt.education.model;

public class ConsultMessage {
    private boolean isMachine;
    private String content;

    public boolean isMachine() {
        return isMachine;
    }

    public void setMachine(boolean machine) {
        isMachine = machine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
