package com.gitzzp.ecode.baselib.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/31 15:49
 * 类描述: Android 设备 基本信息 wifi地址 开机时间
 */
public class DeviceUtil {

    private static final String TAG = DeviceUtil.class.getSimpleName();

    /**
     * 获取 Wifi MAC 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    @Deprecated
    public static String getMacAddress(Context context) {
        return getWifiMacAddress(context);
    }

    /**
     * 获取 Wifi MAC 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getWifiMacAddress(Context context) {
        //wifi mac地址
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        LogUtil.i(TAG, "WIFI MAC：" + mac);
        return mac;
    }

    /**
     * 获取 开机时间
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);
        LogUtil.i(TAG, h + ":" + m);
        return h + ":" + m;
    }

    /**
     * 设备基本信息
     * @return
     */
    public static String getDeviceInfo(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  设备信息  ").append(time).append(" ______________");
        //id
        sb.append("\nID                 :").append(Build.ID);
        //android系统定制商
        sb.append("\nBRAND              :").append(Build.BRAND);
        //版本
        sb.append("\nMODEL              :").append(Build.MODEL);
        //版本字符串
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);

        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        //主板名称
        sb.append("\nBOARD              :").append(Build.BOARD);
        //生产厂商
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        //设备参数
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        //硬件名
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        //
        sb.append("\nHOST               :").append(Build.HOST);
        //描述build的标签
        sb.append("\nTAGS               :").append(Build.TAGS);
        //build的类型
        sb.append("\nTYPE               :").append(Build.TYPE);
        //
        sb.append("\nTIME               :").append(Build.TIME);
        //源码控制版本号
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            //显示屏参数
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {

            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            //硬件厂商
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            //系统引导程序版本号
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            //cpu指令集
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            //内核命令行中的硬件名
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            //当一个版本属性不知道时所设定的值
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            //开发代号
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            //硬件序列号
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        LogUtil.i(TAG, sb.toString());
        return sb.toString();
    }
}
