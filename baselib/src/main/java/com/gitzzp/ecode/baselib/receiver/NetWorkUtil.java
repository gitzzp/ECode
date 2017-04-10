package com.gitzzp.ecode.baselib.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gitzzp.ecode.baselib.ECode;
import com.gitzzp.ecode.baselib.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by gitzzp on 2015/12/17.
 * 网络状态监听
 * 需要权限
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class NetWorkUtil extends BroadcastReceiver {

    public enum NetWorkState {
        WIFI,MOBILE,NONE;
    }

    private static final String TAG = "NetWorkUtil";

    private static ArrayList<NetworkCallback> list = new ArrayList<>();
    private NetWorkState netWorkState;

    private static class NetWorkUtilHolder{
         private static final NetWorkUtil NET_WORK_UTIL = new NetWorkUtil();
    }

    private static NetWorkUtil getInstance(){
        return NetWorkUtilHolder.NET_WORK_UTIL;
    }

    public static void initNetWorkUtil(Application application){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        application.registerReceiver(getInstance(),intentFilter);
    }

    public static void destoryNetWorkUtil(Application application){
        application.unregisterReceiver(getInstance());
    }

    private NetWorkUtil(){ }

    /**
     * 判断网络是否连接
     * @return
     */
    public static boolean isNetworkConnected() {
        // 判断网络是否连接
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ECode.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 判断网络的连接状态 wifi mobile 或者无连接
     * @return
     */
    public static NetWorkState checkNetWorkState(){
        try {
            NetworkInfo.State wifiState = null;
            NetworkInfo.State mobileState = null;
            ConnectivityManager cm = (ConnectivityManager)
                    ECode.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            //部分平板不能插卡 获取会为空
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)==null){
                mobileState = null;
            }else {
                mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            }
            if (wifiState != null && mobileState != null
                    && NetworkInfo.State.CONNECTED != wifiState
                    && NetworkInfo.State.CONNECTED == mobileState) {
                // 手机网络连接成功
                return NetWorkState.MOBILE;
            } else if (wifiState != null && mobileState != null
                    && NetworkInfo.State.CONNECTED != wifiState
                    && NetworkInfo.State.CONNECTED != mobileState) {
                // 手机没有任何的网络
                return NetWorkState.NONE;
            } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
                // 无线网络连接成功
                return NetWorkState.WIFI;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NetWorkState.NONE;
    }

    public interface NetworkCallback{
        void onNetworkChanged(NetWorkState netWorkState);
    }

    public static void register(NetworkCallback networkCallback){
        if(list.contains(networkCallback)){
            LogUtil.d(TAG,"已经包含，无需重复注册");
            return;
        }
        list.add(networkCallback);
    }

    public static void unRegister(NetworkCallback networkCallback){
        if(!list.contains(networkCallback)){
            LogUtil.d(TAG,"已经解除，不能重复移除");
            return;
        }
        list.remove(networkCallback);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            try{
                LogUtil.v("静态广播", "网络状态改变");
                notifyChange();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void notifyChange(){
        netWorkState = checkNetWorkState();
        for(NetworkCallback data:list){
            data.onNetworkChanged(netWorkState);
        }
    }
}
