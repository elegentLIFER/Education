package com.sxmh.wt.education.util;

import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/6.
 */

public class FileUtils {
    private static String DIRECTORY="";

    /**
     * 生成图片路径文件夹
     * @return
     */
    public static String generateImageDirName(){
        File file=new File(DIRECTORY,"card");
        if (!file.exists()){
            file.mkdir();
        }
        return file.getPath();
    }

    /**
     * 生成图片名称
     * @return
     */
    public static String generateImageName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());
        String strDate = dateFormat.format(new Date());
        return "img_" + strDate +generateRandomNumber(10,20)+ ".png";
    }

    /**
     * 删除图片
     */
    public static void deleteImage(String filepath){
        if(TextUtils.isEmpty(filepath)){
            return;
        }
        File f = new File(filepath);
        if(f.exists()){
            f.delete();
        }
    }

    public static boolean isContainNumber(String company) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private static String generateRandomNumber(int bound1, int bound2){
        Random random=new Random();
        int randomInt1=random.nextInt(bound1);
        int randomInt2=random.nextInt(bound2);
        return randomInt1+""+randomInt2;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWithFile(dir);
    }

    private static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
