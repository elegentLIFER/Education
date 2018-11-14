package com.sxmh.wt.education.util;

import android.text.TextUtils;
import android.util.Log;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.base.IView;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.AdviseWayResponse;
import com.sxmh.wt.education.model.response.AiAnswerListResponse;
import com.sxmh.wt.education.model.response.AllCourseClassResponse;
import com.sxmh.wt.education.model.response.CancelCollectResponse;
import com.sxmh.wt.education.model.response.ChangePswResponse;
import com.sxmh.wt.education.model.response.CodeVerifyResponse;
import com.sxmh.wt.education.model.response.CollectResponse;
import com.sxmh.wt.education.model.response.CollectionLiveListResponse;
import com.sxmh.wt.education.model.response.CollectionPaperListResponse;
import com.sxmh.wt.education.model.response.CollectionTalkListResponse;
import com.sxmh.wt.education.model.response.FileUploadResponse;
import com.sxmh.wt.education.model.response.GetCodeResponse;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.GetHomeCycImgResponse;
import com.sxmh.wt.education.model.response.HomePageDataResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.LoginPhoneResponse;
import com.sxmh.wt.education.model.response.LoginResponse;
import com.sxmh.wt.education.model.response.MessageListResponse;
import com.sxmh.wt.education.model.response.MyCourseClassifyResponse;
import com.sxmh.wt.education.model.response.MyWrongsListResponse;
import com.sxmh.wt.education.model.response.NotificationContentResponse;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.model.response.PersonInfoChangeResponse;
import com.sxmh.wt.education.model.response.PersonInfoResponse;
import com.sxmh.wt.education.model.response.PrivacyContentResponse;
import com.sxmh.wt.education.model.response.RegisterResponse;
import com.sxmh.wt.education.model.response.ReplyResponse;
import com.sxmh.wt.education.model.response.SearchResponse;
import com.sxmh.wt.education.model.response.ask_answer.AskResponse;
import com.sxmh.wt.education.model.response.ask_answer.MyNetProblemClassResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetAnswerListResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemInfoResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemListResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseInfoResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;
import com.sxmh.wt.education.model.response.live.BeforeLiveListResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomInfoResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.model.response.questionlib.IsQuestionLogResponse;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionIdsResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionInfoResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionLogResponse;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Net {
    private static final String TAG = "Net";
    public static final int REQUEST_SPEC_TYPE_PAPER_LIST = 101;
    public static final int REQUEST_SPEC_TYPE_PAPER_CATALOG = 102;
    public static final int REQUEST_QUESTION_IDS = 103;
    public static final int REQUEST_QUESTION_INFO = 104;
    public static final int REQUEST_IS_QUES_LOG = 105;
    public static final int REQUEST_DO_QUES_POWER = 106;

    public static final int REQUEST_NET_PROBLEM = 107;
    public static final int REQUEST_NET_PROBLEM_LIST = 108;
    public static final int REQUEST_NET_PROBLEM_INFO = 109;
    public static final int REQUEST_NET_ANSWER_LIST = 110;

    public static final int REQUEST_BANNER = 111;
    public static final int REQUEST_ALL_TYPE = 112;
    public static final int REQUEST_SPEC_TYPE_LESSON = 113;

    public static final String COLLECT_FLAG_TELL = "0";
    public static final String COLLECT_FLAG_EXECISE = "1";
    public static final String COLLECT_FLAG_LIVE = "2";

    public static final int REQUEST_COLLECT = 114;
    public static final int REQUEST_CANCEL_COLLECT = 115;

    public static final int KEY_CYC_IMG = 116;
    public static final int KEY_HOT_LESSON = 117;
    public static final int KEY_RECM_LESSON = 118;
    public static final int KEY_NOTIFICATION = 119;
    public static final int KEY_LESSON_TYPE = 120;

    public static final int REQUEST_NET_COURSE = 121;
    public static final int REQUEST_NET_COURSE_INFO = 122;

    public static final int REQUEST_LIVE_LIST = 123;
    public static final int REQUEST_LIVE_INFO = 124;
    public static final int REQUEST_BEFORE_LIVE = 125;

    public static final int REQUEST_BY_ACCOUNT = 0;
    public static final int REQUEST_BY_PHONE = 1;
    public static final int REQUEST_GET_CODE = 128;

    public static final int KEY_GET_CODE = 129;
    public static final int KEY_REGISTER = 130;

    public static final int REQUEST_ASK = 131;
    public static final int REQUEST_REPLY = 132;
    public static final int REQUEST_MESSAGE_LIST = 133;

    public static final int REQUEST_MY_COLLECTION_LIST_TALK = 134;
    public static final int REQUEST_MY_COLLECTION_LIST_PAPER = 135;
    public static final int REQUEST_MY_COLLECTION_LIST_LIVE = 136;

    public static final int REQUEST_MY_LESSON_CLASSFIFY = 137;
    public static final int REQUEST_AI_ANSWER_LIST = 138;
    public static final int REQUEST_ALL_COURSE_CLASS = 139;

    public static final int REQUEST_PRIVACY = 140;
    public static final int REQUEST_ADVISE_WAY = 141;
    public static final int REQUEST_MY_COURSE_CLASSFIFY = 142;

    public static final int REQUEST_PERSON_INFO = 143;
    public static final int REQUEST_CHANGE_HEADER = 144;
    public static final int REQUEST_CHANGE_EMAIL = 145;

    public static final int REQUEST_UPLOAD_IMAGE = 146;
    public static final int REQUEST_CHANGE_PSW = 147;
    public static final int REQUEST_CODE_VERIFY = 148;
    public static final int REQUEST_MY_WRONGS = 149;

    public static final int REQUEST_APPONINT = 150;
    public static final int REQUEST_CANCEL_APPONINT = 151;
    public static final int REQUEST_LESSON_POWER = 152;
    public static final int REQUEST_LESSON_DOWNLOAD_POWER = 153;
    public static final int REQUEST_LIVE_WATCH_POWER = 154;
    public static final int REQUEST_SEARCH = 155;
    public static final int REQUEST_MY_PAPER = 156;

    protected WeakReference<IView> iView;

    public Net(IView iView) {
        this.iView = new WeakReference<>(iView);
    }

    public void getPaperList(String courseClassId) {
//        iView.get().showLoading();
        Call<PaperListResponse> call = MyApplication.getApiService().getPaperList("", courseClassId);
        call.enqueue(new Callback<PaperListResponse>() {
            @Override
            public void onResponse(Call<PaperListResponse> call, Response<PaperListResponse> response) {
                iView.get().cancelLoading();
                PaperListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_SPEC_TYPE_PAPER_LIST, body.getCourseClasslist());
                }
            }

            @Override
            public void onFailure(Call<PaperListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getPaperCatalog(String paperListId, String memberId) {
        iView.get().showLoading();
        Call<PaperCatalogResponse> call = MyApplication.getApiService().getPaperCatalog("", paperListId, memberId);
        call.enqueue(new Callback<PaperCatalogResponse>() {
            @Override
            public void onResponse(Call<PaperCatalogResponse> call, Response<PaperCatalogResponse> response) {
                iView.get().cancelLoading();
                PaperCatalogResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_SPEC_TYPE_PAPER_CATALOG, body.getPaperCatalogList());
                }
            }

            @Override
            public void onFailure(Call<PaperCatalogResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    /**
     * 5.3.按考卷分类查询考题列表信息
     */
    public void getPaperQuestion(String paperCatalogId, String paperListId, String flag) {
        iView.get().showLoading();
        String memberId = User.getInstance().getMemberId();
        Call<QuestionIdsResponse> call = MyApplication.getApiService().getPaperQuestion("", paperCatalogId, flag, paperListId, memberId);
        call.enqueue(new Callback<QuestionIdsResponse>() {
            @Override
            public void onResponse(Call<QuestionIdsResponse> call, Response<QuestionIdsResponse> response) {
                iView.get().cancelLoading();
                QuestionIdsResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_QUESTION_IDS, body);
                }
            }

            @Override
            public void onFailure(Call<QuestionIdsResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    /**
     * 5.4.通过考题编号获取题目信息
     */
    public void getQuestionInfo(String id, boolean isContinue) {
        iView.get().showLoading();
        Call<QuestionInfoResponse> call = MyApplication.getApiService().getQuestionInfo("", id, User.getInstance().getMemberId(), isContinue);
        call.enqueue(new Callback<QuestionInfoResponse>() {
            @Override
            public void onResponse(Call<QuestionInfoResponse> call, Response<QuestionInfoResponse> response) {
                iView.get().cancelLoading();
                QuestionInfoResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_QUESTION_INFO, body);
                }
            }

            @Override
            public void onFailure(Call<QuestionInfoResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doQuestionLog(String questionId, String paperId, String paperCatalogId, String result, String isCorrect, String queScore, String queTypeId) {
//        iView.get().showLoading();
        Call<QuestionLogResponse> call = MyApplication.getApiService().doQuestionLog("", User.getInstance().getMemberId(), questionId, paperId, paperCatalogId, result, isCorrect, queScore, queTypeId);
        call.enqueue(new Callback<QuestionLogResponse>() {
            @Override
            public void onResponse(Call<QuestionLogResponse> call, Response<QuestionLogResponse> response) {
                iView.get().cancelLoading();
                QuestionLogResponse body = response.body();
                if (body != null) {
//                    iView.get().showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<QuestionLogResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void isQuestionLog(String paperId, String paperCatalogId) {
        iView.get().showLoading();
        Call<IsQuestionLogResponse> call = MyApplication.getApiService().isQuestionLog("", User.getInstance().getMemberId(), paperId, paperCatalogId);
        call.enqueue(new Callback<IsQuestionLogResponse>() {
            @Override
            public void onResponse(Call<IsQuestionLogResponse> call, Response<IsQuestionLogResponse> response) {
                iView.get().cancelLoading();
                IsQuestionLogResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_IS_QUES_LOG, body.isResult());
                }
            }

            @Override
            public void onFailure(Call<IsQuestionLogResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doQuestionPower(String memberId,String paperId, String paperCatalogId) {
        iView.get().showLoading();
        Call<DoQuesPowerResponse> call = MyApplication.getApiService().doQuestionPower("", memberId, paperId, paperCatalogId);
        call.enqueue(new Callback<DoQuesPowerResponse>() {
            @Override
            public void onResponse(Call<DoQuesPowerResponse> call, Response<DoQuesPowerResponse> response) {
                iView.get().cancelLoading();
                DoQuesPowerResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_DO_QUES_POWER, body.isResult());
                    String msg = body.getMsg();
                    if (!TextUtils.isEmpty(msg)) {
                        iView.get().showToast(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<DoQuesPowerResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetProblemClass(String courseClassId) {
        iView.get().showLoading();
        Call<NetProblemClassResponse> call = MyApplication.getApiService().getNetProblemClass("", courseClassId);
        call.enqueue(new Callback<NetProblemClassResponse>() {
            @Override
            public void onResponse(Call<NetProblemClassResponse> call, Response<NetProblemClassResponse> response) {
                iView.get().cancelLoading();
                NetProblemClassResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_PROBLEM, body);
                }
            }

            @Override
            public void onFailure(Call<NetProblemClassResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyNetProblemCourseClass(String courseClassId) {
//        iView.get().showLoading();
        String memberId = User.getInstance().getMemberId();
        Call<MyNetProblemClassResponse> call = MyApplication.getApiService().getMyNetProblemCourseClass("", memberId, courseClassId);
        call.enqueue(new Callback<MyNetProblemClassResponse>() {
            @Override
            public void onResponse(Call<MyNetProblemClassResponse> call, Response<MyNetProblemClassResponse> response) {
                iView.get().cancelLoading();
                MyNetProblemClassResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_PROBLEM, body);
                }
            }

            @Override
            public void onFailure(Call<MyNetProblemClassResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetProblemList(String courseClassId, String pageNum, String pageSize, String memberId, String proContent, String flag) {
        iView.get().showLoading();
        Call<NetProblemListResponse> call = MyApplication.getApiService().getNetProblemList("", courseClassId, pageNum, pageSize, memberId, flag, proContent);
        call.enqueue(new Callback<NetProblemListResponse>() {
            @Override
            public void onResponse(Call<NetProblemListResponse> call, Response<NetProblemListResponse> response) {
                iView.get().cancelLoading();
                NetProblemListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_PROBLEM_LIST, body);
                }
            }

            @Override
            public void onFailure(Call<NetProblemListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetProblemInfo(String netProblemId) {
        iView.get().showLoading();
        Call<NetProblemInfoResponse> call = MyApplication.getApiService().getNetProblemInfo("", netProblemId);
        call.enqueue(new Callback<NetProblemInfoResponse>() {
            @Override
            public void onResponse(Call<NetProblemInfoResponse> call, Response<NetProblemInfoResponse> response) {
                iView.get().cancelLoading();
                NetProblemInfoResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_PROBLEM_INFO, body);
                }
            }

            @Override
            public void onFailure(Call<NetProblemInfoResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetAnswerList(String netProblemId) {
        iView.get().showLoading();
        Call<NetAnswerListResponse> call = MyApplication.getApiService().getNetAnswerList("", netProblemId);
        call.enqueue(new Callback<NetAnswerListResponse>() {
            @Override
            public void onResponse(Call<NetAnswerListResponse> call, Response<NetAnswerListResponse> response) {
                iView.get().cancelLoading();
                NetAnswerListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_ANSWER_LIST, body);
                }
            }

            @Override
            public void onFailure(Call<NetAnswerListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getFunctionCycImg(String function, String courseClassId) {
//        iView.get().showLoading();
        Call<GetCycImgResponse> call = MyApplication.getApiService().getFunctionCycImg(function, courseClassId);
        call.enqueue(new Callback<GetCycImgResponse>() {
            @Override
            public void onResponse(Call<GetCycImgResponse> call, Response<GetCycImgResponse> response) {
                iView.get().cancelLoading();
                GetCycImgResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_BANNER, body.getCycleImgList());
                }
            }

            @Override
            public void onFailure(Call<GetCycImgResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getCourseClassify(String pid) {
//        iView.get().showLoading();
        Call<LessonTypeResponse> call = MyApplication.getApiService().getCourseClassify("", pid, "2");
        call.enqueue(new Callback<LessonTypeResponse>() {
            @Override
            public void onResponse(Call<LessonTypeResponse> call, Response<LessonTypeResponse> response) {
                iView.get().cancelLoading();
                LessonTypeResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_ALL_TYPE, body.getCourseClassList());
                }
            }

            @Override
            public void onFailure(Call<LessonTypeResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetCourseList(String id) {
//        iView.get().showLoading();
        Call<NetCourseListResponse> call = MyApplication.getApiService().getNetCourseList("", id);
        call.enqueue(new Callback<NetCourseListResponse>() {
            @Override
            public void onResponse(Call<NetCourseListResponse> call, Response<NetCourseListResponse> response) {
                iView.get().cancelLoading();
                NetCourseListResponse body = response.body();
                if (body != null) {
                    List<NetCourseListResponse.ListBean> netCourseList = body.getList();
                    if (netCourseList != null)
                        iView.get().updateContent(REQUEST_SPEC_TYPE_LESSON, netCourseList);
                }
            }

            @Override
            public void onFailure(Call<NetCourseListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doCollect(String thisId, String flag) {
        iView.get().showLoading();
        Call<CollectResponse> call = MyApplication.getApiService().doCollect("", User.getInstance().getMemberId(), thisId, flag);
        call.enqueue(new Callback<CollectResponse>() {
            @Override
            public void onResponse(Call<CollectResponse> call, Response<CollectResponse> response) {
                iView.get().cancelLoading();
                CollectResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_COLLECT, body.isResult());
                    iView.get().showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<CollectResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doCancelCollect(String thisId, String flag) {
        iView.get().showLoading();
        Call<CancelCollectResponse> call = MyApplication.getApiService().doCancelCollect("", User.getInstance().getMemberId(), thisId, flag);
        call.enqueue(new Callback<CancelCollectResponse>() {
            @Override
            public void onResponse(Call<CancelCollectResponse> call, Response<CancelCollectResponse> response) {
                iView.get().cancelLoading();
                CancelCollectResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_CANCEL_COLLECT, body.isResult());
                    iView.get().showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<CancelCollectResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getCourseClassify() {
        iView.get().showLoading();
        final Call<LessonTypeResponse> homePageData = MyApplication.getApiService().getCourseClassify("1", MyApplication.getLv0Id(), "1");
        homePageData.enqueue(new Callback<LessonTypeResponse>() {
            @Override
            public void onResponse(Call<LessonTypeResponse> call, Response<LessonTypeResponse> response) {
                iView.get().cancelLoading();
                LessonTypeResponse body = response.body();
                iView.get().updateContent(KEY_LESSON_TYPE, body.getCourseClassList());
            }

            @Override
            public void onFailure(Call<LessonTypeResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                iView.get().showToast("数据获取失败");
            }
        });
    }

    public void getHomePageData() {
        iView.get().showLoading();
        final Call<HomePageDataResponse> homePageData = MyApplication.getApiService().getHomePageData();
        homePageData.enqueue(new Callback<HomePageDataResponse>() {
            @Override
            public void onResponse(Call<HomePageDataResponse> call, Response<HomePageDataResponse> response) {
                iView.get().cancelLoading();
                HomePageDataResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(KEY_HOT_LESSON, body.getHotNetCourseList());
                    iView.get().updateContent(KEY_RECM_LESSON, body.getRecomNetCourseList());
                    iView.get().updateContent(KEY_NOTIFICATION, body.getTopInformList());
                }
            }

            @Override
            public void onFailure(Call<HomePageDataResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                iView.get().showToast("数据获取失败");
            }
        });
    }

    public void getCycImg() {
        iView.get().showLoading();
        Call<GetHomeCycImgResponse> cycImg = MyApplication.getApiService().getHomeCycImg();
        cycImg.enqueue(new Callback<GetHomeCycImgResponse>() {
            @Override
            public void onResponse(Call<GetHomeCycImgResponse> call, Response<GetHomeCycImgResponse> response) {
                iView.get().cancelLoading();

                GetHomeCycImgResponse body = response.body();
                if (body != null) {
                    List<GetHomeCycImgResponse.FirstCycleImgBean> cycleImgList = body.getFirstCycleImg();
                    iView.get().updateContent(KEY_CYC_IMG, cycleImgList);
                }
            }

            @Override
            public void onFailure(Call<GetHomeCycImgResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                iView.get().cancelLoading();
                iView.get().showToast("获取数据失败");
            }
        });
    }

    public void getNetCourse(String netCourseListId, String memberId) {
        iView.get().showLoading();
        Call<NetCourseResponse> call = MyApplication.getApiService().getNetCourse("", netCourseListId, memberId);
        call.enqueue(new Callback<NetCourseResponse>() {
            @Override
            public void onResponse(Call<NetCourseResponse> call, Response<NetCourseResponse> response) {
                iView.get().cancelLoading();
                NetCourseResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_COURSE, body.getNetCourseList());
                }
            }

            @Override
            public void onFailure(Call<NetCourseResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetCourseInfo(String netCourseId) {
        iView.get().showLoading();
        Call<NetCourseInfoResponse> call = MyApplication.getApiService().getNetCourseInfo("", netCourseId, User.getInstance().getMemberId());
        call.enqueue(new Callback<NetCourseInfoResponse>() {
            @Override
            public void onResponse(Call<NetCourseInfoResponse> call, Response<NetCourseInfoResponse> response) {
                iView.get().cancelLoading();
                NetCourseInfoResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_NET_COURSE_INFO, body);
                }
            }

            @Override
            public void onFailure(Call<NetCourseInfoResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getLiveRoomList(String courseClassId) {
        iView.get().showLoading();
        Call<LiveRoomListResponse> call = MyApplication.getApiService().getLiveRoomList("", courseClassId);
        call.enqueue(new Callback<LiveRoomListResponse>() {
            @Override
            public void onResponse(Call<LiveRoomListResponse> call, Response<LiveRoomListResponse> response) {
                iView.get().cancelLoading();
                LiveRoomListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_LIVE_LIST, body);
                }
            }

            @Override
            public void onFailure(Call<LiveRoomListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getLiveRoomInfo(String liveRoomId) {
        iView.get().showLoading();
        Call<LiveRoomInfoResponse> call = MyApplication.getApiService().getLiveRoomInfo("", liveRoomId, User.getInstance().getMemberId());
        call.enqueue(new Callback<LiveRoomInfoResponse>() {
            @Override
            public void onResponse(Call<LiveRoomInfoResponse> call, Response<LiveRoomInfoResponse> response) {
                iView.get().cancelLoading();
                LiveRoomInfoResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_LIVE_INFO, body);
                }
            }

            @Override
            public void onFailure(Call<LiveRoomInfoResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getBeforeLiveList(String courseClassId) {
        iView.get().showLoading();
        Call<BeforeLiveListResponse> call = MyApplication.getApiService().getBeforeLiveList("", courseClassId, "10", "1");
        call.enqueue(new Callback<BeforeLiveListResponse>() {
            @Override
            public void onResponse(Call<BeforeLiveListResponse> call, Response<BeforeLiveListResponse> response) {
                iView.get().cancelLoading();
                BeforeLiveListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_BEFORE_LIVE, body.getNetCourseList());
                }
            }

            @Override
            public void onFailure(Call<BeforeLiveListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void upLoadImage(String path) {
        iView.get().showLoading();
        File file = new File(path);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        Call<FileUploadResponse> call = MyApplication.getApiService().upLoadImage(requestBody, part);
        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                iView.get().cancelLoading();
                FileUploadResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.isResult() + "");
                    if (body.isResult()) {
                        iView.get().updateContent(REQUEST_UPLOAD_IMAGE, body);
                    }
                }
            }

            @Override
            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getCode(String phone, String flag) {
        iView.get().showLoading();
        Call<GetCodeResponse> codeResponseCall = MyApplication.getApiService().getCode("", phone, flag);
        codeResponseCall.enqueue(new Callback<GetCodeResponse>() {
            @Override
            public void onResponse(Call<GetCodeResponse> call, Response<GetCodeResponse> response) {
                iView.get().cancelLoading();
                GetCodeResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    if (body.isResult()) {
                        iView.get().updateContent(REQUEST_GET_CODE, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCodeResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast("验证码未能获取");
            }
        });
    }

    public void loginPsw(String name, String psw, String deviceId, String deviceModel) {
        iView.get().showLoading();
        Call<LoginResponse> loginResponseCall = MyApplication.getApiService().loginByPsw(1, name, psw, deviceId, deviceModel);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                iView.get().cancelLoading();
                LoginResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_BY_ACCOUNT, body);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void loginPhone(String phone, String code, String deviceId, String deviceModel) {
        iView.get().showLoading();
        Call<LoginPhoneResponse> call = MyApplication.getApiService().loginByPhone(1, phone, code, deviceId, deviceModel);
        call.enqueue(new Callback<LoginPhoneResponse>() {
            @Override
            public void onResponse(Call<LoginPhoneResponse> call, Response<LoginPhoneResponse> response) {
                iView.get().cancelLoading();
                LoginPhoneResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    if (body.isResult()) {
                        iView.get().updateContent(REQUEST_BY_PHONE, body);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginPhoneResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void register(String userName, String psw, String phone, String code) {
        iView.get().showLoading();
        Call<RegisterResponse> register = MyApplication.getApiService().register("", userName, psw, phone, code);
        register.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                iView.get().cancelLoading();
                RegisterResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(KEY_REGISTER, body.isResult());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage().toString());
            }
        });
    }

    public void getNotificationContent(String notificationID) {
        iView.get().showLoading();
        Call<NotificationContentResponse> call = MyApplication.getApiService().getNotificationContent("", notificationID);
        call.enqueue(new Callback<NotificationContentResponse>() {
            @Override
            public void onResponse(Call<NotificationContentResponse> call, Response<NotificationContentResponse> response) {
                iView.get().cancelLoading();
                NotificationContentResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(0, body);
                }
            }

            @Override
            public void onFailure(Call<NotificationContentResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doNetProblem(String courseClassId, String problemTitle, String problemContent) {
        iView.get().showLoading();
        Call<AskResponse> call = MyApplication.getApiService().doNetProblem("", User.getInstance().getMemberId(), courseClassId, problemTitle, problemContent);
        call.enqueue(new Callback<AskResponse>() {
            @Override
            public void onResponse(Call<AskResponse> call, Response<AskResponse> response) {
                iView.get().cancelLoading();
                AskResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_ASK, body);
                }
            }

            @Override
            public void onFailure(Call<AskResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doNetAnswer(String netProblemId, String answerContent) {
        iView.get().showLoading();
        Call<ReplyResponse> call = MyApplication.getApiService().doNetAnswer("", User.getInstance().getMemberId(), netProblemId, answerContent);
        call.enqueue(new Callback<ReplyResponse>() {
            @Override
            public void onResponse(Call<ReplyResponse> call, Response<ReplyResponse> response) {
                iView.get().cancelLoading();
                ReplyResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_REPLY, body);
                }
            }

            @Override
            public void onFailure(Call<ReplyResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getInformList(String flag, String pageNum, String pageSize) {
        iView.get().showLoading();
        Call<MessageListResponse> call = MyApplication.getApiService().getInformList("", flag, pageNum, pageSize);
        call.enqueue(new Callback<MessageListResponse>() {
            @Override
            public void onResponse(Call<MessageListResponse> call, Response<MessageListResponse> response) {
                iView.get().cancelLoading();
                MessageListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MESSAGE_LIST, body);
                }
            }

            @Override
            public void onFailure(Call<MessageListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    /**
     * 隐私政策和
     *
     * @param flag
     */
    public void getPrivacy(String flag) {
        iView.get().showLoading();
        Call<PrivacyContentResponse> call = MyApplication.getApiService().getPrivacy("", flag, "1", "10");
        call.enqueue(new Callback<PrivacyContentResponse>() {
            @Override
            public void onResponse(Call<PrivacyContentResponse> call, Response<PrivacyContentResponse> response) {
                iView.get().cancelLoading();
                PrivacyContentResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_PRIVACY, body);
                }
            }

            @Override
            public void onFailure(Call<PrivacyContentResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyCollectTalkList() {
        iView.get().showLoading();
        String memberId = User.getInstance().getMemberId();
        Call<CollectionTalkListResponse> call = MyApplication.getApiService().getMyCollectTalkList("", memberId, "1", "10", Constant.FLAG_COLLECTION_JIANG_TI);
        call.enqueue(new Callback<CollectionTalkListResponse>() {
            @Override
            public void onResponse(Call<CollectionTalkListResponse> call, Response<CollectionTalkListResponse> response) {
                iView.get().cancelLoading();
                CollectionTalkListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_COLLECTION_LIST_TALK, body);
                }
            }

            @Override
            public void onFailure(Call<CollectionTalkListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyCollectPaperList() {
        iView.get().showLoading();
        Call<CollectionPaperListResponse> call = MyApplication.getApiService().getMyCollectPaperList("", User.getInstance().getMemberId(), "1", "10", Constant.FLAG_COLLECTION_ZUO_TI);
        call.enqueue(new Callback<CollectionPaperListResponse>() {
            @Override
            public void onResponse(Call<CollectionPaperListResponse> call, Response<CollectionPaperListResponse> response) {
                iView.get().cancelLoading();
                CollectionPaperListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_COLLECTION_LIST_PAPER, body);
                }
            }

            @Override
            public void onFailure(Call<CollectionPaperListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyCollectLiveList() {
        iView.get().showLoading();
        String memberId = User.getInstance().getMemberId();
        Call<CollectionLiveListResponse> call = MyApplication.getApiService().getMyCollectLiveList("", memberId, "1", "10", Constant.FLAG_COLLECTION_LIVE);
        call.enqueue(new Callback<CollectionLiveListResponse>() {
            @Override
            public void onResponse(Call<CollectionLiveListResponse> call, Response<CollectionLiveListResponse> response) {
                iView.get().cancelLoading();
                CollectionLiveListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_COLLECTION_LIST_LIVE, body);
                }
            }

            @Override
            public void onFailure(Call<CollectionLiveListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyCourseClassifyLv1( ) {
        iView.get().showLoading();
        Call<LessonTypeResponse> call = MyApplication.getApiService().getMyCourseClassifyLv1("", User.getInstance().getMemberId(),MyApplication.getLv0Id());
        call.enqueue(new Callback<LessonTypeResponse>() {
            @Override
            public void onResponse(Call<LessonTypeResponse> call, Response<LessonTypeResponse> response) {
                iView.get().cancelLoading();
                LessonTypeResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_LESSON_CLASSFIFY, body);
                }
            }

            @Override
            public void onFailure(Call<LessonTypeResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyCourseClassify(String courseClassifyId) {
//        iView.get().showLoading();
        Call<MyCourseClassifyResponse> call = MyApplication.getApiService().getMyCourseClassify("", courseClassifyId, User.getInstance().getMemberId());
        call.enqueue(new Callback<MyCourseClassifyResponse>() {
            @Override
            public void onResponse(Call<MyCourseClassifyResponse> call, Response<MyCourseClassifyResponse> response) {
                iView.get().cancelLoading();
                MyCourseClassifyResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_COURSE_CLASSFIFY, body);
                }
            }

            @Override
            public void onFailure(Call<MyCourseClassifyResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getAiAnswerList(String keyWord) {
        iView.get().showLoading();
        Call<AiAnswerListResponse> call = MyApplication.getApiService().getAiAnswerList("", keyWord);
        call.enqueue(new Callback<AiAnswerListResponse>() {
            @Override
            public void onResponse(Call<AiAnswerListResponse> call, Response<AiAnswerListResponse> response) {
                iView.get().cancelLoading();
                AiAnswerListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_AI_ANSWER_LIST, body);
                }
            }

            @Override
            public void onFailure(Call<AiAnswerListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getAllCourseClass() {
        iView.get().showLoading();
        Call<AllCourseClassResponse> call = MyApplication.getApiService().getAllCourseClass("");
        call.enqueue(new Callback<AllCourseClassResponse>() {
            @Override
            public void onResponse(Call<AllCourseClassResponse> call, Response<AllCourseClassResponse> response) {
                iView.get().cancelLoading();
                AllCourseClassResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_ALL_COURSE_CLASS, body);
                }
            }

            @Override
            public void onFailure(Call<AllCourseClassResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doSaveOpinion(String feedContent, String flag, String email, String otherContact) {
        iView.get().showLoading();
        Call<ReplyResponse> call = MyApplication.getApiService().doSaveOpinion("", User.getInstance().getMemberId(), feedContent, flag, email, otherContact);
        call.enqueue(new Callback<ReplyResponse>() {
            @Override
            public void onResponse(Call<ReplyResponse> call, Response<ReplyResponse> response) {
                iView.get().cancelLoading();
                ReplyResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_ALL_COURSE_CLASS, body);
                }
            }

            @Override
            public void onFailure(Call<ReplyResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getAdvisoryWay() {
        iView.get().showLoading();
        Call<AdviseWayResponse> call = MyApplication.getApiService().getAdvisoryWay("");
        call.enqueue(new Callback<AdviseWayResponse>() {
            @Override
            public void onResponse(Call<AdviseWayResponse> call, Response<AdviseWayResponse> response) {
                iView.get().cancelLoading();
                AdviseWayResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_ADVISE_WAY, body);
                }
            }

            @Override
            public void onFailure(Call<AdviseWayResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMemberInfo() {
        iView.get().showLoading();
        Call<PersonInfoResponse> call = MyApplication.getApiService().getMemberInfo("", User.getInstance().getMemberId());
        call.enqueue(new Callback<PersonInfoResponse>() {
            @Override
            public void onResponse(Call<PersonInfoResponse> call, Response<PersonInfoResponse> response) {
                iView.get().cancelLoading();
                PersonInfoResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_PERSON_INFO, body);
                }
            }

            @Override
            public void onFailure(Call<PersonInfoResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doAlterMemberInfo(String headerUrl) {
        iView.get().showLoading();
        Call<PersonInfoChangeResponse> call = MyApplication.getApiService().changeHeader("", User.getInstance().getMemberId(), headerUrl);
        call.enqueue(new Callback<PersonInfoChangeResponse>() {
            @Override
            public void onResponse(Call<PersonInfoChangeResponse> call, Response<PersonInfoChangeResponse> response) {
                iView.get().cancelLoading();
                PersonInfoChangeResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_CHANGE_HEADER, body);
                }
            }

            @Override
            public void onFailure(Call<PersonInfoChangeResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void changeEmail(String email) {
        iView.get().showLoading();
        Call<PersonInfoChangeResponse> call = MyApplication.getApiService().changeEmail("", User.getInstance().getMemberId(), email);
        call.enqueue(new Callback<PersonInfoChangeResponse>() {
            @Override
            public void onResponse(Call<PersonInfoChangeResponse> call, Response<PersonInfoChangeResponse> response) {
                iView.get().cancelLoading();
                PersonInfoChangeResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_CHANGE_EMAIL, body);
                }
            }

            @Override
            public void onFailure(Call<PersonInfoChangeResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doResetPwd(String psw, String memberId) {
        iView.get().showLoading();
        Call<ChangePswResponse> call = MyApplication.getApiService().doResetPwd("", memberId, psw);
        call.enqueue(new Callback<ChangePswResponse>() {
            @Override
            public void onResponse(Call<ChangePswResponse> call, Response<ChangePswResponse> response) {
                iView.get().cancelLoading();
                ChangePswResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_CHANGE_PSW, body);
                }
            }

            @Override
            public void onFailure(Call<ChangePswResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doVerifyCode(String phoneNum, String code) {
        iView.get().showLoading();
        Call<CodeVerifyResponse> call = MyApplication.getApiService().doVerifyCode("", phoneNum, code);
        call.enqueue(new Callback<CodeVerifyResponse>() {
            @Override
            public void onResponse(Call<CodeVerifyResponse> call, Response<CodeVerifyResponse> response) {
                iView.get().cancelLoading();
                CodeVerifyResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_CODE_VERIFY, body);
                }
            }

            @Override
            public void onFailure(Call<CodeVerifyResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getErrorQuestionList() {
        iView.get().showLoading();
        Call<MyWrongsListResponse> call = MyApplication.getApiService().getErrorQuestionList("", User.getInstance().getMemberId(), "1", "10");
        call.enqueue(new Callback<MyWrongsListResponse>() {
            @Override
            public void onResponse(Call<MyWrongsListResponse> call, Response<MyWrongsListResponse> response) {
                iView.get().cancelLoading();
                MyWrongsListResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_WRONGS, body);
                }
            }

            @Override
            public void onFailure(Call<MyWrongsListResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doAppointLive(String liveRoomId) {
        iView.get().showLoading();
        Call<CollectResponse> call = MyApplication.getApiService().doAppointLive("", User.getInstance().getMemberId(), liveRoomId, "0");
        call.enqueue(new Callback<CollectResponse>() {
            @Override
            public void onResponse(Call<CollectResponse> call, Response<CollectResponse> response) {
                iView.get().cancelLoading();
                CollectResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_APPONINT, body);
                }
            }

            @Override
            public void onFailure(Call<CollectResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void doCancelAppointLive(String liveRoomId) {
        iView.get().showLoading();
        Call<CollectResponse> call = MyApplication.getApiService().doAppointLive("", User.getInstance().getMemberId(), liveRoomId, "1");
        call.enqueue(new Callback<CollectResponse>() {
            @Override
            public void onResponse(Call<CollectResponse> call, Response<CollectResponse> response) {
                iView.get().cancelLoading();
                CollectResponse body = response.body();
                if (body != null) {
                    iView.get().showToast(body.getMsg());
                    iView.get().updateContent(REQUEST_CANCEL_APPONINT, body);
                }
            }

            @Override
            public void onFailure(Call<CollectResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getNetCoursePower(String netCourseId,String memberId) {
        iView.get().showLoading();
        Call<DoQuesPowerResponse> call = MyApplication.getApiService().getNetCoursePower("", memberId, netCourseId);
        call.enqueue(new Callback<DoQuesPowerResponse>() {
            @Override
            public void onResponse(Call<DoQuesPowerResponse> call, Response<DoQuesPowerResponse> response) {
                iView.get().cancelLoading();
                DoQuesPowerResponse body = response.body();
                if (body != null) {
                    String msg = body.getMsg();
                    if (!TextUtils.isEmpty(msg)) iView.get().showToast(msg);
                    iView.get().updateContent(REQUEST_LESSON_POWER, body);
                }
            }

            @Override
            public void onFailure(Call<DoQuesPowerResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void downloadVideoPower(String netCourseId) {
        iView.get().showLoading();
        Call<DoQuesPowerResponse> call = MyApplication.getApiService().downloadVideoPower("", User.getInstance().getMemberId(), netCourseId);
        call.enqueue(new Callback<DoQuesPowerResponse>() {
            @Override
            public void onResponse(Call<DoQuesPowerResponse> call, Response<DoQuesPowerResponse> response) {
                iView.get().cancelLoading();
                DoQuesPowerResponse body = response.body();
                if (body != null) {
                    String msg = body.getMsg();
                    if (!TextUtils.isEmpty(msg)) iView.get().showToast(msg);
                    iView.get().updateContent(REQUEST_LESSON_DOWNLOAD_POWER, body);
                }
            }

            @Override
            public void onFailure(Call<DoQuesPowerResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getLivePower(String liveRoom) {
        iView.get().showLoading();
        Call<DoQuesPowerResponse> call = MyApplication.getApiService().getLivePower("", User.getInstance().getMemberId(), liveRoom);
        call.enqueue(new Callback<DoQuesPowerResponse>() {
            @Override
            public void onResponse(Call<DoQuesPowerResponse> call, Response<DoQuesPowerResponse> response) {
                iView.get().cancelLoading();
                DoQuesPowerResponse body = response.body();
                if (body != null) {
                    String msg = body.getMsg();
                    if (!TextUtils.isEmpty(msg)) iView.get().showToast(msg);
                    iView.get().updateContent(REQUEST_LIVE_WATCH_POWER, body);
                }
            }

            @Override
            public void onFailure(Call<DoQuesPowerResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getSearch(String keyWord) {
        iView.get().showLoading();
        Call<SearchResponse> call = MyApplication.getApiService().getSearch("", keyWord, "1", "100");
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                iView.get().cancelLoading();
                SearchResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_SEARCH, body);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }

    public void getMyPaper(String courseClassifyId) {
//        iView.get().showLoading();
        Call<MyPaperResponse> call = MyApplication.getApiService().getMyPaper("", courseClassifyId, User.getInstance().getMemberId());
        call.enqueue(new Callback<MyPaperResponse>() {
            @Override
            public void onResponse(Call<MyPaperResponse> call, Response<MyPaperResponse> response) {
                iView.get().cancelLoading();
                MyPaperResponse body = response.body();
                if (body != null) {
                    iView.get().updateContent(REQUEST_MY_PAPER, body);
                }
            }

            @Override
            public void onFailure(Call<MyPaperResponse> call, Throwable t) {
                iView.get().cancelLoading();
                iView.get().showToast(t.getMessage());
            }
        });
    }
}
