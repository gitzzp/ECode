package com.gitzzp.ecode.baselib.utils;

import android.util.Log;

import com.gitzzp.ecode.baselib.ECode;


/**
 * 日志工具类 发布时可以统一关闭
 * Created by gitzzp on 16/7/18.
 */

public class LogUtil {
    public static void v(String tag, String msg){
        if(!ECode.isDebug()) return;
        Log.v(tag,msg);
    }
    public static void d(String tag, String msg){
        if(!ECode.isDebug()) return;
        Log.d(tag,msg);
    }
    public static void i(String tag, String msg){
        if(!ECode.isDebug()) return;
        Log.i(tag,msg);
    }
    public static void w(String tag, String msg){
        if(!ECode.isDebug()) return;
        Log.w(tag,msg);
    }
    public static void e(String tag, String msg){
        if(!ECode.isDebug()) return;
        Log.e(tag,msg);
    }
}
