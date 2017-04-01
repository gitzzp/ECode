package com.gitzzp.ecode.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.gitzzp.ecode.baselib.ECode;
import com.gitzzp.ecode.baselib.utils.LogUtil;

/**
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * 创建人：gitzzp
 * 创建日期:17/3/31 18:32
 * 类描述: 电话状态监听
 */
public class PhoneReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneReceiver.class.getSimpleName();

    private static PhoneListener phoneListener;


    public void register(Context context,PhoneListener phoneListener){
        try {
            IntentFilter filter = new IntentFilter();
            this.phoneListener = phoneListener;
            filter.addAction("android.intent.action.PHONE_STATE");
            filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
            context.registerReceiver(this,filter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void unRegister(Context context){
        try{
            context.unregisterReceiver(this);
            phoneListener = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ECode.isDebug()){
            LogUtil.d(TAG,"action:"+intent.getAction());
            LogUtil.d(TAG,"intent:");
            Bundle bundle = intent.getExtras();
            for(String key:bundle.keySet()){
                LogUtil.d(TAG,key+" : "+bundle.get(key));
            }
        }
        String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephonyManager.getCallState();
        CallState callState = CallState.End;
        switch (state){
            //响铃中
            case TelephonyManager.CALL_STATE_RINGING:
                callState = CallState.Ringing;
                break;
            //通话中
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callState = CallState.Calling;
                break;
            //挂断
            case TelephonyManager.CALL_STATE_IDLE:
                callState = CallState.End;
                break;
        }
        if(phoneListener!=null) phoneListener.onPhoneStateChanged(callState,phoneNumber);
    }

    public interface PhoneListener{
        void onPhoneStateChanged(CallState callState,String number);
    }

    public enum CallState{
        Ringing,
        End,
        Calling

    }
}
