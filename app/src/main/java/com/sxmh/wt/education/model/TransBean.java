package com.sxmh.wt.education.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransBean implements Parcelable {
    private String id;
    private String quesType;
    private boolean isCorrect;
    private boolean hasDone;
    private String score;

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(quesType);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
        dest.writeByte((byte) (hasDone ? 1 : 0));
        dest.writeString(score);
    }

    public static final Parcelable.Creator<TransBean> CREATOR = new Parcelable.Creator<TransBean>() {

        @Override
        public TransBean createFromParcel(Parcel source) {
            TransBean transBean = new TransBean();
            transBean.setId(source.readString());
            transBean.setQuesType(source.readString());
            transBean.setCorrect(source.readByte() != 0);
            transBean.setHasDone(source.readByte() != 0);
            transBean.setScore(source.readString());
            return transBean;
        }

        @Override
        public TransBean[] newArray(int size) {
            return new TransBean[size];
        }
    };
}
