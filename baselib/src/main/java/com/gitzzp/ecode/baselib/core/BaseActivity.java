package com.gitzzp.ecode.baselib.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.gitzzp.ecode.baselib.receiver.NetWorkUtil;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/23 12:15
 * 类描述:
 */
public class BaseActivity extends FragmentActivity implements NetWorkUtil.NetworkCallback {

    protected Activity mContext;
    NetWorkUtil.NetWorkState netWorkState = NetWorkUtil.NetWorkState.NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.addActivity(this);
        netWorkState = NetWorkUtil.checkNetWorkState();
        NetWorkUtil.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkUtil.unRegister(this);
        AppManager.finishActivity(this);
    }

    @Override
    public void onNetworkChanged(NetWorkUtil.NetWorkState netWorkState) {
        this.netWorkState = netWorkState;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            AppManager.finishActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void finishThis(){
        AppManager.finishActivity(this);
    }
}
