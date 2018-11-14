package com.sxmh.wt.education.model;

import com.sxmh.wt.education.model.response.AllCourseClassResponse;

import java.util.ArrayList;
import java.util.List;

public class TypeContainer {
    private AllCourseClassResponse.CourseClassListBean lv0Bean;
    private List<AllCourseClassResponse.CourseClassListBean> classListLv1;

    public TypeContainer() {
        classListLv1 = new ArrayList<>();
    }

    public AllCourseClassResponse.CourseClassListBean getLv0Bean() {
        return lv0Bean;
    }

    public void setLv0Bean(AllCourseClassResponse.CourseClassListBean lv0Bean) {
        this.lv0Bean = lv0Bean;
    }

    public List<AllCourseClassResponse.CourseClassListBean> getClassListLv1() {
        return classListLv1;
    }

    public void setClassListLv1(List<AllCourseClassResponse.CourseClassListBean> classListLv1) {
        this.classListLv1 = classListLv1;
    }
}
