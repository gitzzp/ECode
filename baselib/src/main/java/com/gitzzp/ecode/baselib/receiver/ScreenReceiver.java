package com.gitzzp.ecode.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/1 14:22
 * 类描述: 屏幕解锁广播
 */
public class ScreenReceiver extends BroadcastReceiver {

    private static final String TAG = ScreenReceiver.class.getSimpleName();

    private ScreenListener screenListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_ON)){
            if(screenListener!=null) screenListener.onScreenOn();
        }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            if(screenListener!=null) screenListener.onScreenOff();
        }
    }

    public void register(Context context,ScreenListener screenListener){
        try {
            IntentFilter filter = new IntentFilter();
            this.screenListener = screenListener;
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.registerReceiver(this,filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unRegister(Context context){
        try{
            context.unregisterReceiver(this);
            screenListener = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface ScreenListener{
        void onScreenOn();
        void onScreenOff();
    }

}
