package com.sxmh.wt.education.model.response.questionlib;

import java.io.Serializable;
import java.util.List;

public class MyPaperResponse {

    private List<CourseClasslistBean> courseClasslist;

    public List<CourseClasslistBean> getCourseClasslist() {
        return courseClasslist;
    }

    public void setCourseClasslist(List<CourseClasslistBean> courseClasslist) {
        this.courseClasslist = courseClasslist;
    }

    public static class CourseClasslistBean {
        /**
         * id : 2c92908d5c1be42b015c2dce4f11000c
         * courseClassName : 管理
         * litimg : null
         * paperTypeList : [{"paperList":[{"paperSize":"3","paperListId":"2c92908d5c2f6c9b015c32c9c8920841","paperListName":"一建管理"}],"paperTypeName":"真题练习","paperTypeId":"2c92908d5bfe123a015c0a0fa76700b8"},{"paperList":[{"paperSize":"1","paperListId":"2c92908d5c2f6c9b015c32cb27ac0845","paperListName":"一建管理"}],"paperTypeName":"模拟练习","paperTypeId":"ff8080815b0f91f4015b0f964a190003"},{"paperList":[{"paperSize":"41","paperListId":"2c92908d5c2f6c9b015c32ce09680857","paperListName":"一建管理"}],"paperTypeName":"章节练习","paperTypeId":"ff8080815b0f91f4015b0f9691a10005"}]
         */

        private String id;
        private String courseClassName;
        private Object litimg;
        private List<PaperTypeListBean> paperTypeList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public Object getLitimg() {
            return litimg;
        }

        public void setLitimg(Object litimg) {
            this.litimg = litimg;
        }

        public List<PaperTypeListBean> getPaperTypeList() {
            return paperTypeList;
        }

        public void setPaperTypeList(List<PaperTypeListBean> paperTypeList) {
            this.paperTypeList = paperTypeList;
        }

        public static class PaperTypeListBean {
            /**
             * paperList : [{"paperSize":"3","paperListId":"2c92908d5c2f6c9b015c32c9c8920841","paperListName":"一建管理"}]
             * paperTypeName : 真题练习
             * paperTypeId : 2c92908d5bfe123a015c0a0fa76700b8
             */

            private String paperTypeName;
            private String paperTypeId;
            private List<PaperListBean> paperList;

            public String getPaperTypeName() {
                return paperTypeName;
            }

            public void setPaperTypeName(String paperTypeName) {
                this.paperTypeName = paperTypeName;
            }

            public String getPaperTypeId() {
                return paperTypeId;
            }

            public void setPaperTypeId(String paperTypeId) {
                this.paperTypeId = paperTypeId;
            }

            public List<PaperListBean> getPaperList() {
                return paperList;
            }

            public void setPaperList(List<PaperListBean> paperList) {
                this.paperList = paperList;
            }

            public static class PaperListBean implements Serializable{
                /**
                 * paperSize : 3
                 * paperListId : 2c92908d5c2f6c9b015c32c9c8920841
                 * paperListName : 一建管理
                 */

                private String paperSize;
                private String paperListId;
                private String paperListName;

                public String getPaperSize() {
                    return paperSize;
                }

                public void setPaperSize(String paperSize) {
                    this.paperSize = paperSize;
                }

                public String getPaperListId() {
                    return paperListId;
                }

                public void setPaperListId(String paperListId) {
                    this.paperListId = paperListId;
                }

                public String getPaperListName() {
                    return paperListName;
                }

                public void setPaperListName(String paperListName) {
                    this.paperListName = paperListName;
                }
            }
        }
    }
}
