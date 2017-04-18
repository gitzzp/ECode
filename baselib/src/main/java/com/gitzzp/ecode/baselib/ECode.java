package com.gitzzp.ecode.baselib;

import android.app.Application;
import android.content.Context;

import com.gitzzp.ecode.baselib.receiver.NetWorkUtil;
import com.gitzzp.ecode.baselib.utils.PathUtil;


/**
 * 创建人：gitzzp
 * 创建日期:17/3/22 18:52
 * 类描述:
 */
public class ECode {
    private static boolean isDebug = true;
    private static Application application;

    /**
     * 需要在Application中的onCreate方法中调用
     * @param application ApplicationContext
     */
    public static void init(Application application){
        if(ECode.application == null)
            ECode.application = application;
        //初始化程序所需文件目录
        PathUtil.getInstance().initDirs(application);
        //注册网络监听
        NetWorkUtil.initNetWorkUtil(application);
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static Context getContext(){
        return application;
    }
}
