package com.sxmh.wt.education;

import com.sxmh.wt.education.model.response.AdviseWayResponse;
import com.sxmh.wt.education.model.response.AiAnswerListResponse;
import com.sxmh.wt.education.model.response.AllCourseClassResponse;
import com.sxmh.wt.education.model.response.ChangePswResponse;
import com.sxmh.wt.education.model.response.CodeVerifyResponse;
import com.sxmh.wt.education.model.response.CollectionLiveListResponse;
import com.sxmh.wt.education.model.response.CollectionPaperListResponse;
import com.sxmh.wt.education.model.response.CollectionTalkListResponse;
import com.sxmh.wt.education.model.response.FileUploadResponse;
import com.sxmh.wt.education.model.response.MessageListResponse;
import com.sxmh.wt.education.model.response.MyCourseClassifyResponse;
import com.sxmh.wt.education.model.response.MyWrongsListResponse;
import com.sxmh.wt.education.model.response.PersonInfoChangeResponse;
import com.sxmh.wt.education.model.response.PersonInfoResponse;
import com.sxmh.wt.education.model.response.PrivacyContentResponse;
import com.sxmh.wt.education.model.response.ReplyResponse;
import com.sxmh.wt.education.model.response.SearchResponse;
import com.sxmh.wt.education.model.response.ask_answer.AskResponse;
import com.sxmh.wt.education.model.response.CancelCollectResponse;
import com.sxmh.wt.education.model.response.CollectResponse;
import com.sxmh.wt.education.model.response.GetCodeResponse;
import com.sxmh.wt.education.model.response.GetCycImgResponse;
import com.sxmh.wt.education.model.response.GetHomeCycImgResponse;
import com.sxmh.wt.education.model.response.HomePageDataResponse;
import com.sxmh.wt.education.model.response.LessonTypeResponse;
import com.sxmh.wt.education.model.response.ask_answer.MyNetProblemClassResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetAnswerListResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemClassResponse;
import com.sxmh.wt.education.model.response.PaperCatalogResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemInfoResponse;
import com.sxmh.wt.education.model.response.ask_answer.NetProblemListResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseInfoResponse;
import com.sxmh.wt.education.model.response.live.AppointResponse;
import com.sxmh.wt.education.model.response.live.BeforeLiveListResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomInfoResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomListResponse;
import com.sxmh.wt.education.model.response.LoginPhoneResponse;
import com.sxmh.wt.education.model.response.LoginResponse;
import com.sxmh.wt.education.model.response.MessageResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseResponse;
import com.sxmh.wt.education.model.response.lesson.NetCourseListResponse;
import com.sxmh.wt.education.model.response.NotificationContentResponse;
import com.sxmh.wt.education.model.response.RegisterResponse;
import com.sxmh.wt.education.model.response.questionlib.DoQuesPowerResponse;
import com.sxmh.wt.education.model.response.questionlib.IsQuestionLogResponse;
import com.sxmh.wt.education.model.response.questionlib.MyPaperResponse;
import com.sxmh.wt.education.model.response.questionlib.PaperListResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionIdsResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionInfoResponse;
import com.sxmh.wt.education.model.response.questionlib.QuestionLogResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2018/5/9 0009.
 */

public interface ApiService {

    /**
     * 获取功能轮播图
     *
     * @param function
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do?getCycleImg")
    Call<GetCycImgResponse> getFunctionCycImg(@Field("function") String function, @Field("courseClass") String courseClass);

    /**
     * 获取首页轮播图
     *
     * @return
     */
    @POST("appV2Controller.do?getFirstCycleImg")
    Call<GetHomeCycImgResponse> getHomeCycImg();

    /**
     * 获取首页热门数据
     *
     * @return
     */
    @POST("appV2Controller.do?getHomePageData")
    Call<HomePageDataResponse> getHomePageData();

    /**
     * 获取课程分类
     *
     * @param courseClassify
     * @param pId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LessonTypeResponse> getCourseClassify(@Field("getCourseClassify") String courseClassify, @Field("pId") String pId, @Field("courseClassLevel") String courseClassLevel);

    /**
     * 账户密码登录
     *
     * @param doPwdLogin
     * @param userName
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LoginResponse> loginByPsw(@Field("doPwdLogin") int doPwdLogin, @Field("userName") String userName, @Field("password") String password, @Field("deviceId") String deviceId, @Field("deviceModel") String deviceModel);

    /**
     * 手机号登录
     *
     * @param doPwdLogin
     * @param phoneNum
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LoginPhoneResponse> loginByPhone(@Field("doPhoLogin") int doPwdLogin, @Field("phoneNum") String phoneNum, @Field("code") String code, @Field("deviceId") String deviceId, @Field("deviceModel") String deviceModel);

    /**
     * 获取验证码
     *
     * @param getcode
     * @param phoneNum
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<GetCodeResponse> getCode(@Field("getCode") String getcode, @Field("phoneNum") String phoneNum, @Field("flag") String flag);

    /**
     * 注册
     *
     * @param doRegist
     * @param userName
     * @param psw
     * @param phone
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<RegisterResponse> register(@Field("doRegist") String doRegist, @Field("accounts") String userName, @Field("loginPassword") String psw, @Field("phonebind") String phone, @Field("code") String code);

    /**
     * 获取通知详情
     *
     * @param getTopInform
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NotificationContentResponse> getNotificationContent(@Field("getTopInform") String getTopInform, @Field("id") String id);

    /**
     * 3.1 查询讲题列表
     *
     * @param courseClassifyId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetCourseListResponse> getNetCourseList(@Field("getNetCourseList") String getNetCourseList, @Field("courseClassifyId") String courseClassifyId);

    /**
     * 3.2 获取讲题课件列表
     *
     * @param netCourseListId
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetCourseResponse> getNetCourse(@Field("getNetCourse") String getNetCourse, @Field("netCourseListId") String netCourseListId, @Field("memberId") String memberId);

    /**
     * 3.3. 获取讲题课件详情
     *
     * @param getNetCourseInfo
     * @param netCourseId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetCourseInfoResponse> getNetCourseInfo(@Field("getNetCourseInfo") String getNetCourseInfo, @Field("netCourseId") String netCourseId, @Field("memberId") String memberId);

    /**
     * 获取消息列表
     *
     * @param getInformList
     * @param flag          0 系统消息  1 活动消息  2 学习提醒
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MessageResponse> getMessage(@Field("getInformList") String getInformList, @Field("flag") String flag, @Field("pageNum") String pageNum, @Field("pageSize") String pageSize);

    /**
     * 获取隐私政策和
     *
     * @param getInformList
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PrivacyContentResponse> getPrivacy(@Field("getInformList") String getInformList, @Field("flag") String flag, @Field("pageNum") String pageNum, @Field("pageSize") String pageSize);

    /**
     * 获取直播间列表
     *
     * @param getLiveList
     * @param courseClassId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LiveRoomListResponse> getLiveRoomList(@Field("getLiveList") String getLiveList, @Field("courseClassId") String courseClassId);

    /**
     * 获取直播间信息
     *
     * @param getLiveRoom
     * @param liveRoomId
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LiveRoomInfoResponse> getLiveRoomInfo(@Field("getLiveRoom") String getLiveRoom, @Field("liveRoomId") String liveRoomId, @Field("memberId") String memberId);

    /**
     * 预约与取消预约
     *
     * @param doAppointLive
     * @param memberId
     * @param liveRoomId
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<AppointResponse> appointOrCancel(@Field("doAppointLive") String doAppointLive, @Field("memberId") String memberId, @Field("liveRoomId") String liveRoomId, @Field("flag") String flag);

    /**
     * 4.5.获取回看直播列表
     *
     * @param getBeforeLiveList
     * @param courseClassId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<BeforeLiveListResponse> getBeforeLiveList(@Field("getBeforeLiveList") String getBeforeLiveList, @Field("courseClassId") String courseClassId, @Field("pageSize") String pageSize, @Field("pageNum") String pageNum);

    /**
     * 讨论区上传图片
     *
     * @param uploadFiles
     * @param imgs
     * @return
     */
    @Multipart
//    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<FileUploadResponse> upLoadImage(@Part("uploadFiles") RequestBody uploadFiles, @Part MultipartBody.Part imgs);

    /**
     * 5.1按课程分类查询考卷信息
     *
     * @param getPaperList
     * @param courseClassId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PaperListResponse> getPaperList(@Field("getPaperList") String getPaperList, @Field("courseClassId") String courseClassId);

    /**
     * 5.2按考卷查询考卷分类信息
     *
     * @param getPaperCatalog
     * @param paperListId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PaperCatalogResponse> getPaperCatalog(@Field("getPaperCatalog") String getPaperCatalog, @Field("paperListId") String paperListId, @Field("memberId") String memberId);

    /**
     * 5.3.按考卷分类查询考题列表信息
     *
     * @param getPaperQuestion
     * @param paperCatalogId
     * @param paperListId
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<QuestionIdsResponse> getPaperQuestion(@Field("getPaperQuestion") String getPaperQuestion, @Field("paperCatalogId") String paperCatalogId, @Field("flag") String flag, @Field("paperListId") String paperListId,
                                               @Field("memberId") String memberId);

    /**
     * 5.4.通过考题编号获取题目信息
     *
     * @param getPaperCatalog
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<QuestionInfoResponse> getQuestionInfo(@Field("getQuestionInfo") String getPaperCatalog, @Field("id") String id, @Field("memberId") String memberId, @Field("isContinue") boolean isContinue);

    /**
     * 收藏
     *
     * @param doCollect
     * @param memberId
     * @param thisId
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CollectResponse> doCollect(@Field("doCollect") String doCollect, @Field("memberId") String memberId, @Field("thisId") String thisId, @Field("flag") String flag);


    /**
     * 取消收藏
     *
     * @param doCancelCollect
     * @param memberId
     * @param thisId
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CancelCollectResponse> doCancelCollect(@Field("doCancelCollect") String doCancelCollect, @Field("memberId") String memberId, @Field("thisId") String thisId, @Field("flag") String flag);


    /**
     * 5.5.插入做题记录接口
     *
     * @param doQuestionLog
     * @param memberId
     * @param questionId
     * @param paperId
     * @param paperCatalogId
     * @param result
     * @param isCorrect
     * @param queScore
     * @param queTypeId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<QuestionLogResponse> doQuestionLog(@Field("doQuestionLog") String doQuestionLog, @Field("memberId") String memberId, @Field("questionId") String questionId,
                                            @Field("paperId") String paperId, @Field("paperCatalogId") String paperCatalogId, @Field("result") String result,
                                            @Field("isCorrect") String isCorrect, @Field("queScore") String queScore, @Field("queTypeId") String queTypeId);

    /**
     * 5.6.查询是否有做题记录接口
     *
     * @param isQuestionLog
     * @param memberId
     * @param paperListId
     * @param paperCatalogId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<IsQuestionLogResponse> isQuestionLog(@Field("isQuestionLog") String isQuestionLog, @Field("memberId") String memberId,
                                              @Field("paperListId") String paperListId, @Field("paperCatalogId") String paperCatalogId);

    /**
     * 5.7.判断是否有做题权限接口
     *
     * @param doQuestionPower
     * @param memberId
     * @param paperId
     * @param paperCatalogId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<DoQuesPowerResponse> doQuestionPower(@Field("doQuestionPower") String doQuestionPower, @Field("memberId") String memberId,
                                              @Field("paperId") String paperId, @Field("paperCatalogId") String paperCatalogId);

    /**
     * 6.1.查询当前分类下问题数量和分类信息
     *
     * @param getNetProblemClass
     * @param courseClassId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetProblemClassResponse> getNetProblemClass(@Field("getNetProblemClass") String getNetProblemClass, @Field("courseClassId") String courseClassId);

    /**
     * 8.5.按课程分类查询我的答疑
     *
     * @param getMyNetProblemCourseClass
     * @param memberId
     * @param courseClassifyId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MyNetProblemClassResponse> getMyNetProblemCourseClass(@Field("getMyNetProblemCourseClass") String getMyNetProblemCourseClass, @Field("memberId") String memberId, @Field("courseClassifyId") String courseClassifyId);

    /**
     * 6.2.查询问题列表  无附加参数
     *
     * @param getNetProblemList
     * @param courseClassId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetProblemListResponse> getNetProblemList(@Field("getNetProblemList") String getNetProblemList, @Field("courseClassId") String courseClassId,
                                                   @Field("pageNum") String pageNum, @Field("pageSize") String pageSize,
                                                   @Field("memberId") String memberId, @Field("flag") String flag, @Field("proContent") String proContent);

    /**
     * 6.2.查询问题列表  我的问答时用
     *
     * @param getNetProblemList
     * @param courseClassId
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetProblemListResponse> getMyNetProblemList(@Field("getNetProblemList") String getNetProblemList, @Field("courseClassId") String courseClassId,
                                                     @Field("pageNum") String pageNum, @Field("pageSize") String pageSize, @Field("memberId") String memberId, @Field("flag") Integer flag);


    /**
     * 6.3.根据问题编号查询问题详情
     *
     * @param getNetProblemInfo
     * @param netProblemId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetProblemInfoResponse> getNetProblemInfo(@Field("getNetProblemInfo") String getNetProblemInfo, @Field("netProblemId") String netProblemId);

    /**
     * 6.4.根据问题编号查询答疑列表
     *
     * @param getNetAnswerList
     * @param netProblemId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<NetAnswerListResponse> getNetAnswerList(@Field("getNetAnswerList") String getNetAnswerList, @Field("netProblemId") String netProblemId);

    /**
     * 提问
     *
     * @param doNetProblem
     * @param memberId
     * @param courseClassId
     * @param problemTitle
     * @param problemContent
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<AskResponse> doNetProblem(@Field("doNetProblem") String doNetProblem, @Field("memberId") String memberId, @Field("courseClassId") String courseClassId
            , @Field("problemTitle") String problemTitle, @Field("problemContent") String problemContent);

    /**
     * 回复
     *
     * @param doNetAnswer
     * @param memberId
     * @param netProblemId
     * @param answerContent
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<ReplyResponse> doNetAnswer(@Field("doNetAnswer") String doNetAnswer, @Field("memberId") String memberId, @Field("netProblemId") String netProblemId
            , @Field("answerContent") String answerContent);

    /**
     * 查询消息列表
     *
     * @param getInformList
     * @param flag
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MessageListResponse> getInformList(@Field("getInformList") String getInformList, @Field("flag") String flag, @Field("pageNum") String pageNum
            , @Field("pageSize") String pageSize);

    /**
     * 8.6.收藏的讲题列表
     *
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CollectionTalkListResponse> getMyCollectTalkList(@Field("getMyCollectList") String getMyCollectList, @Field("memberId") String memberId, @Field("pageNum") String pageNum, @Field("pageSize") String pageSize, @Field("flag") String flag);

    /**
     * 8.6.收藏的试卷列表
     *
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CollectionPaperListResponse> getMyCollectPaperList(@Field("getMyCollectList") String getMyCollectList, @Field("memberId") String memberId, @Field("pageNum") String pageNum, @Field("pageSize") String pageSize, @Field("flag") String flag);

    /**
     * 8.6.收藏的直播列表
     *
     * @param getMyCollectList
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @param flag
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CollectionLiveListResponse> getMyCollectLiveList(@Field("getMyCollectList") String getMyCollectList, @Field("memberId") String memberId, @Field("pageNum") String pageNum, @Field("pageSize") String pageSize, @Field("flag") String flag);

    /**
     * 8.11.获取我的讲题二级分类
     *
     * @param getMyCourseClassifyLv1
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<LessonTypeResponse> getMyCourseClassifyLv1(@Field("getMyCourseClassifyLv1") String getMyCourseClassifyLv1, @Field("memberId") String memberId, @Field("pId") String pId);

    /**
     * 8.3.获取我的课程分类信息
     *
     * @param getMyCourseClassify
     * @param courseClassifyId
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MyCourseClassifyResponse> getMyCourseClassify(@Field("getMyCourseClassify") String getMyCourseClassify, @Field("courseClassifyId") String courseClassifyId, @Field("memberId") String memberId);

    /**
     * 8.8.智能顾问列表
     *
     * @param getAiAnswerList
     * @param keyWord
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<AiAnswerListResponse> getAiAnswerList(@Field("getAiAnswerList") String getAiAnswerList, @Field("keyWord") String keyWord);

    /**
     * 2.7.获取全部分类信息
     *
     * @param getAllCourseClass
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<AllCourseClassResponse> getAllCourseClass(@Field("getAllCourseClass") String getAllCourseClass);

    /**
     * 反馈意见
     *
     * @param doSaveOpinion
     * @param memberId
     * @param feedContent
     * @param flag
     * @param email
     * @param otherContact
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<ReplyResponse> doSaveOpinion(@Field("doSaveOpinion") String doSaveOpinion, @Field("memberId") String memberId, @Field("feedContent") String feedContent,
                                      @Field("flag") String flag, @Field("email") String email, @Field("otherContact") String otherContact);

    /**
     * 联系方式
     *
     * @param getAdvisoryWay
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<AdviseWayResponse> getAdvisoryWay(@Field("getAdvisoryWay") String getAdvisoryWay);

    /**
     * 获取个人信息
     *
     * @param getMemberInfo
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PersonInfoResponse> getMemberInfo(@Field("getMemberInfo") String getMemberInfo, @Field("memberId") String memberId);

    /**
     * 修改头像
     *
     * @param doAlterMemberInfo
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PersonInfoChangeResponse> changeHeader(@Field("doAlterMemberInfo") String doAlterMemberInfo, @Field("id") String memberId, @Field("photo") String photo);

    /**
     * 修改邮箱
     *
     * @param doAlterMemberInfo
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<PersonInfoChangeResponse> changeEmail(@Field("doAlterMemberInfo") String doAlterMemberInfo, @Field("id") String memberId, @Field("emailbind") String emailbind);

    /**
     * 修改密码前验证验证码
     *
     * @param doVerifyCode
     * @param phoneNum
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CodeVerifyResponse> doVerifyCode(@Field("doVerifyCode") String doVerifyCode, @Field("phoneNum") String phoneNum, @Field("code") String code);

    /**
     * 修改密码
     *
     * @param doResetPwd
     * @param memberId
     * @param newPwd
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<ChangePswResponse> doResetPwd(@Field("doResetPwd") String doResetPwd, @Field("memberId") String memberId, @Field("newPwd") String newPwd);

    /**
     * 8.7.获取有错题的试卷分类列表
     *
     * @param getErrorQuestionList
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MyWrongsListResponse> getErrorQuestionList(@Field("getErrorQuestionList") String getErrorQuestionList, @Field("memberId") String memberId,
                                                    @Field("pageNum") String pageNum, @Field("pageSize") String pageSize);

    /**
     * 直播预约
     *
     * @param doAppointLive
     * @param memberId
     * @param liveRoomId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<CollectResponse> doAppointLive(@Field("doAppointLive") String doAppointLive, @Field("memberId") String memberId,
                                        @Field("liveRoomId") String liveRoomId, @Field("flag") String flag);

    /**
     * 查看讲题课程权限
     *
     * @param getNetCoursePower
     * @param memberId
     * @param netCourseId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<DoQuesPowerResponse> getNetCoursePower(@Field("getNetCoursePower") String getNetCoursePower, @Field("memberId") String memberId,
                                                @Field("netCourseId") String netCourseId);

    /**
     * 视频下载权限
     *
     * @param downloadVideoPower
     * @param memberId
     * @param netCourseId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<DoQuesPowerResponse> downloadVideoPower(@Field("downloadVideoPower") String downloadVideoPower, @Field("memberId") String memberId,
                                                 @Field("netCourseId") String netCourseId);

    /**
     * 直播观看权限
     *
     * @param getLivePower
     * @param memberId
     * @param liveRoomId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<DoQuesPowerResponse> getLivePower(@Field("getLivePower") String getLivePower, @Field("memberId") String memberId,
                                           @Field("liveRoomId") String liveRoomId);

    /**
     * 综合搜索
     *
     * @param getSearch
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<SearchResponse> getSearch(@Field("getSearch") String getSearch, @Field("keyWord") String keyWord,
                                   @Field("pageNum") String pageNum, @Field("pageSize") String pageSize);

    /**
     * 8.4.查询我的试卷列表
     *
     * @param getMyPaper
     * @param courseClassifyId
     * @param memberId
     * @return
     */
    @FormUrlEncoded
    @POST("appV2Controller.do")
    Call<MyPaperResponse> getMyPaper(@Field("getMyPaper") String getMyPaper, @Field("courseClassifyId") String courseClassifyId,
                                     @Field("memberId") String memberId);

}