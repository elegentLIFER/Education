package com.sxmh.wt.education.util;

import android.os.Environment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Constant {
//                    public static final String BASE_URL = "http://120.77.242.84:8083/xhweb/"; // 测试
    public static final String BASE_URL = "http://www.xuehuang.cn/"; // 正式

    public static final String SHARE_URL = "http://www.xuehuang.cn/xhweb/mobileController.do?download";

    public static final String SP_THIS_APP = "sp_this_app";
    public static final String SP_KEY_RECENT_WATCH = "sp_key_recent_watch";
    public static final String SP_KEY_DOWNLOAD = "sp_key_download";
    public static final String SP_KEY_USER = "sp_key_user";
    public static final String SP_LOGIN_TIME = "sp_login_time";
    public static final String SP_IS_FIRST = "is_first";

    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/a_xuhuang";
    public static final String CACHE_PATH = APP_PATH + "/cache";
    public static final String VIDEO_PATH = APP_PATH + "/video";
    public static final String DOWNLOAD_TXT_PATH = Constant.VIDEO_PATH + "/" + "download.txt";

    public static final String REQUEST_NAME = "request_name";
    public static final String CLASS_ID = "class_id";
    public static final String IS_MY = "is_my";

    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_MULTIPLE = 1;
    public static final int TYPE_ANALYSE = 2;

    public static final String SEPARATER_COMMA = ",";
    public static final String SEPARATER_ID = "#";
    public static final String SEPARATER_STAR = "\\*";
    public static final String SEPARATER_JSON = "<===>";

    // 5.3.按考卷分类查询考题列表信息  FLAG
    public static final String FLAG_BEGIN_DO = "0";
    public static final String FLAG_ALL_ANALYSE = "1";
    public static final String FLAG_SEE_COLLECTION = "2";
    public static final String FLAG_SEE_WRONGS = "3";
    public static final String FLAG_DO_AGAIN = "4";
    public static final String FLAG_DO_CONTINUE = "5";

    public static final String KEY_LESSON_ID = "key_hot_lesson_id";
    public static final String KEY_LESSON_NAME = "key_lesson_name";

    public static final String BANNER_JIANG_TI = "1";
    public static final String BANNER_ZUO_TI = "2";
    public static final String BANNER_WEN_DA = "3";
    public static final String BANNER_LIVE = "4";


    public static final String FLAG_COLLECTION_JIANG_TI = "0";
    public static final String FLAG_COLLECTION_ZUO_TI = "1";
    public static final String FLAG_COLLECTION_LIVE = "2";

    public static final int LESSON_POWER_FREE = 0;
    public static final int LESSON_POWER_LOGIN = 1;
    public static final int LESSON_POWER_BUY = 2;

}
