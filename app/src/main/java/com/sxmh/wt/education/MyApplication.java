package com.sxmh.wt.education;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.sxmh.wt.education.model.response.AllCourseClassResponse;
import com.sxmh.wt.education.receiver.ExampleUtil;
import com.sxmh.wt.education.util.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;
import static com.sxmh.wt.education.BuildConfig.DEBUG;

/**
 * 这个类一般用于初始化一些数据，如屏幕的信息之类的和Activity的简单的管理啊，还有一些第三方SDK的初始化
 * Created by Wang Tao on 2018/4/9 0009.
 */

public class MyApplication extends Application {
    private List<Activity> activityList;
    public static MyApplication myApplication;

    private static ApiService service;

    private static List<AllCourseClassResponse.CourseClassListBean> courseClassList;
    private static List<AllCourseClassResponse.CourseClassListBean> currentClassListLv1;
    private static int currentLessonTypePosition;
    private static String lv0Id;

    public static final String FILE_NAME = "crash";
    private static final String PATH = Constant.APP_PATH + "/Crash/log/";
    private static final String FILE_NAME_SUFFIX = ".trace";

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
        myApplication = this;

        initAppFile();
        registerMessageReceiver();
        jPushInit();

        exceptionHandle();
    }

    /**
     * app文件夹创建
     */
    private void initAppFile() {
        Disposable subscribe = Observable.just(Constant.CACHE_PATH, Constant.VIDEO_PATH)
                .map(s -> new File(s))
                .filter(file -> !file.exists())
                .subscribe(file -> file.mkdirs());

        File file = new File(Constant.DOWNLOAD_TXT_PATH);
        if (file.exists()) return;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exceptionHandle() {
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            dumpExceptionToSDCard(e);
            // Java的默认异常处理。 如果是NullPointerException， 注释掉这行app会无响应，因为"AndroidRuntime: Shutting down VM"
            exceptionHandler.uncaughtException(t, e);
        });
    }

    /**
     * 将异常信息写入SD卡
     *
     * @param e
     */
    private void dumpExceptionToSDCard(Throwable e) { //如果SD卡不存在或无法使用，则无法将异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        File dir = new File(PATH); //如果目录下没有文件夹，就创建文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //得到当前年月日时分秒
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //在定义的Crash文件夹下创建文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        if (!file.exists()) file.listFiles();
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //写入时间
            pw.println(time);
            //写入手机信息
            dumpPhoneInfo(pw);
            pw.println();//换行
            e.printStackTrace(pw);
            pw.close();//关闭输入流
        } catch (Exception e1) {
            Log.e(TAG, "dump crash info failed");
        }
    }

    /**
     * 获取手机各项信息
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException { //得到包管理器
        PackageManager pm = getPackageManager(); //得到包对象
        PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES); //写入APP版本号
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode); //写入 Android 版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT); //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER); //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL); //CPU架构
        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        } else {
            pw.println(Build.CPU_ABI);
        }
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.sxmh.wt.education.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
//                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void jPushInit() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        JPushInterface.resumePush(this);
    }

    public static ApiService getApiService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ApiService.class);
        }
        return service;
    }

    /**
     * 获取MyApplication实例（单例）
     *
     * @return
     */
    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 删除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 退出APP
     */
    public void quitApp() {
        synchronized (activityList) {
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext())
                iterator.next().finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static int getCurrentLessonTypePosition() {
        return currentLessonTypePosition;
    }

    public static void setCurrentLessonTypePosition(int currentLessonTypePosition) {
        MyApplication.currentLessonTypePosition = currentLessonTypePosition;
    }

    public static List<AllCourseClassResponse.CourseClassListBean> getCurrentClassListLv1() {
        return currentClassListLv1;
    }

    public static void setCurrentClassListLv1
            (List<AllCourseClassResponse.CourseClassListBean> currentClassListLv1) {
        MyApplication.currentClassListLv1 = currentClassListLv1;
    }

    public static String getLv0Id() {
        return lv0Id;
    }

    public static void setLv0Id(String lv0Id) {
        MyApplication.lv0Id = lv0Id;
    }

    public static List<AllCourseClassResponse.CourseClassListBean> getCourseClassList() {
        return courseClassList;
    }

    public static void setCourseClassList(List<AllCourseClassResponse.CourseClassListBean> courseClassList) {
        MyApplication.courseClassList = courseClassList;
    }
}
