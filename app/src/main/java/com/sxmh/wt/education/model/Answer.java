package com.sxmh.wt.education.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Answer implements Serializable {
    private List<Boolean> selectionList;
    private String analyseAnswer;

    public boolean isDone() {
        for (boolean s : selectionList) {
            if (s) {
                return true;
            }
        }
        return false;
    }

    public void setSelectionList(List<Boolean> list) {
        if (selectionList == null) {
            selectionList = new ArrayList<>();
        }
        selectionList.clear();
        selectionList.addAll(list);
    }

    public List<Boolean> getSelectionList() {
        List<Boolean> booleans = new ArrayList<>();
        if (selectionList != null) {
            booleans.addAll(selectionList);
        }
        return booleans;
    }

    public String getAnalyseAnswer() {
        return analyseAnswer;
    }

    public void setAnalyseAnswer(String analyseAnswer) {
        this.analyseAnswer = analyseAnswer;
    }
}
