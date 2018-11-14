package com.sxmh.wt.education.model.response.questionlib;

import java.io.Serializable;
import java.util.List;

public class PaperListResponse {

    private List<CourseClasslistBean> courseClasslist;

    public List<CourseClasslistBean> getCourseClasslist() {
        return courseClasslist;
    }

    public void setCourseClasslist(List<CourseClasslistBean> courseClasslist) {
        this.courseClasslist = courseClasslist;
    }

    public static class CourseClasslistBean {
        /**
         * courseClassId : 2c92908d5c33d9b7015c383d019b0d35
         * paperTypeList : [{"paperList":[{"paperSize":"3","paperListId":"2c92908d5c33d9b7015c382f504b0bea","paperListName":"消防技术实务"}],"paperTypeName":"真题练习","paperTypeId":"2c92908d5bfe123a015c0a0fa76700b8"},{"paperList":[{"paperSize":"6","paperListId":"2c92908d5c33d9b7015c38312f820bf1","paperListName":"消防技术实务"}],"paperTypeName":"模拟练习","paperTypeId":"ff8080815b0f91f4015b0f964a190003"},{"paperList":[{"paperSize":"44","paperListId":"2c92908d5c33d9b7015c3831aaae0bf5","paperListName":"消防技术实务"}],"paperTypeName":"章节练习","paperTypeId":"ff8080815b0f91f4015b0f9691a10005"}]
         */

        private String courseClassId;
        private List<PaperTypeListBean> paperTypeList;

        public String getCourseClassId() {
            return courseClassId;
        }

        public void setCourseClassId(String courseClassId) {
            this.courseClassId = courseClassId;
        }

        public List<PaperTypeListBean> getPaperTypeList() {
            return paperTypeList;
        }

        public void setPaperTypeList(List<PaperTypeListBean> paperTypeList) {
            this.paperTypeList = paperTypeList;
        }

        public static class PaperTypeListBean {
            /**
             * paperList : [{"paperSize":"3","paperListId":"2c92908d5c33d9b7015c382f504b0bea","paperListName":"消防技术实务"}]
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
                 * paperListId : 2c92908d5c33d9b7015c382f504b0bea
                 * paperListName : 消防技术实务
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
