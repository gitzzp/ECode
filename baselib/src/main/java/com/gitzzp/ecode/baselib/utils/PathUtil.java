package com.gitzzp.ecode.baselib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 创建人：gitzzp
 * 创建日期:16/12/29 15:23
 * 类描述: 初始化程序中需要的各种目录
 */
public class PathUtil {
    public static String pathPrefix;
    public static final String historyPathName = "/chat/";
    public static final String imagePathName = "/image/";
    public static final String voicePathName = "/voice/";
    public static final String filePathName = "/file/";
    public static final String videoPathName = "/video/";
    public static final String netdiskDownloadPathName = "/netdisk/";
    public static final String tempPathName = "/temp/";
    private static File storageDir = null;
    private static PathUtil instance = null;
    private File voicePath = null;
    private File imagePath = null;
    private File historyPath = null;
    private File videoPath = null;
    private File filePath;

    private PathUtil() {
    }

    public static PathUtil getInstance() {
        if(instance == null) {
            instance = new PathUtil();
        }

        return instance;
    }

    /**
     *
     * @param var1 自定义名字 可为 null
     * @param var2 自定义名字 可为 ""
     * @param var3 context
     */
    public void initDirs(String var1, String var2, Context var3) {
        String var4 = var3.getPackageName();
        pathPrefix = "/Android/data/" + var4 + "/";
        this.voicePath = generateVoicePath(var1, var2, var3);
        if(!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        this.imagePath = generateImagePath(var1, var2, var3);
        if(!this.imagePath.exists()) {
            this.imagePath.mkdirs();
        }

        this.historyPath = generateHistoryPath(var1, var2, var3);
        if(!this.historyPath.exists()) {
            this.historyPath.mkdirs();
        }

        this.videoPath = generateVideoPath(var1, var2, var3);
        if(!this.videoPath.exists()) {
            this.videoPath.mkdirs();
        }

        this.filePath = generateFiePath(var1, var2, var3);
        if(!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

    }

    public void initDirs(Context context) {
        String var1 = null;
        String var2 = "";
        String var4 = context.getPackageName();
        pathPrefix = "/Android/data/" + var4 + "/";
        this.voicePath = generateVoicePath(var1, var2, context);
        if(!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        this.imagePath = generateImagePath(var1, var2, context);
        if(!this.imagePath.exists()) {
            this.imagePath.mkdirs();
        }

        this.historyPath = generateHistoryPath(var1, var2, context);
        if(!this.historyPath.exists()) {
            this.historyPath.mkdirs();
        }

        this.videoPath = generateVideoPath(var1, var2, context);
        if(!this.videoPath.exists()) {
            this.videoPath.mkdirs();
        }

        this.filePath = generateFiePath(var1, var2, context);
        if(!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

        this.filePath = generateDownloadPath(var1, var2, context);
        if(!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

        this.filePath = generateTempPath(var1, var2, context);
        if(!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

    }

    public File getImagePath() {
        return this.imagePath;
    }

    public File getVoicePath() {
        return this.voicePath;
    }

    public File getFilePath() {
        return this.filePath;
    }

    public File getVideoPath() {
        return this.videoPath;
    }

    public File getHistoryPath() {
        return this.historyPath;
    }

    public File getDownloadPath() {
        return this.historyPath;
    }
    public File getTempPath() {
        return this.historyPath;
    }

    private static File getStorageDir(Context context) {
        if(storageDir == null) {
            File var1 = Environment.getExternalStorageDirectory();
            if(var1.exists()) {
                return var1;
            }

            storageDir = context.getFilesDir();
        }

        return storageDir;
    }

    private static File generateImagePath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + imagePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + imagePathName;
        }

        return new File(getStorageDir(context), var3);
    }

    private static File generateVoicePath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + voicePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + voicePathName;
        }

        return new File(getStorageDir(context), var3);
    }

    private static File generateFiePath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + filePathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + filePathName;
        }

        return new File(getStorageDir(context), var3);
    }

    private static File generateVideoPath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + videoPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + videoPathName;
        }

        return new File(getStorageDir(context), var3);
    }

    private static File generateHistoryPath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + historyPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + historyPathName;
        }

        return new File(getStorageDir(context), var3);
    }
    private static File generateDownloadPath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + netdiskDownloadPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + netdiskDownloadPathName;
        }

        return new File(getStorageDir(context), var3);
    }
    private static File generateTempPath(String var0, String var1, Context context) {
        String var3 = null;
        if(var0 == null) {
            var3 = pathPrefix + var1 + tempPathName;
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + tempPathName;
        }

        return new File(getStorageDir(context), var3);
    }

}

