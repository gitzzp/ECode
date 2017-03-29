package com.gitzzp.ecode;

import android.app.Application;

import com.gitzzp.ecode.baselib.ECode;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 16:10
 * 类描述:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ECode.init(this);
    }
}
