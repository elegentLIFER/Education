package com.sxmh.wt.education.activity;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class User {
    private String id;
    private String score;
    private String isCorrect;

    public User(String id, String score, String isCorrect) {
        this.id = id;
        this.score = score;
        this.isCorrect = isCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public static void main(String[] args) {
        String rawString = "2c92908d63aefb0b0163de2a359a0f41#1*0," +
                "2c92908d63aefb0b0163de2a35990f3e#1*0," +
                "2c92908d63aefb0b0163de2a35990f3f#1*0," +
                "2c92908d63aefb0b0163de2a35970f3d#1*0," +
                "2c92908d63aefb0b0163de2a359a0f40#0*1," +
                "2c92908d63aefb0b0163de2a359a0f42#0*0";
        List<User> scoreList = new ArrayList<>();

        String[] split = rawString.split(",");
        int len = split.length;
        for (int i = 0; i < len; i++) {
            String one = split[i];
            String[] split1 = one.split("#");
            String[] split2 = split1[1].split("\\*");
            scoreList.add(new User(split1[0], split2[0], split2[1]));
        }


        Disposable subscribe = Observable.just(rawString)
                .concatMap(s -> Observable.fromArray(s.split(",")))
                .map(s -> {
                    String[] split1 = s.split("#");
                    String[] split2 = split1[1].split("\\*");
                    return new User(split1[0], split2[0], split2[1]); })
                .subscribe(score -> scoreList.add(score));


    }
}
