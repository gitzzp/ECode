package com.gitzzp.ecode.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.gitzzp.ecode.baselib.ECode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 11:57
 * 类描述:
 *
 * {@link #isLandscape(Context)}isLandscape 判断设备当前是否横屏
 * {@link #isAutoRotate(Context)}isAutoRotate 判断当前的手机屏幕是否开启了自动旋转这个选项
 */
public class ScreenUtil {

    private static Context context = ECode.getContext();

    /**
     * 获取屏幕宽度
     */
    public static int getWindowWidth(){
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }



    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
     */
    public static int getDpi(){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     *
     * 获取 虚拟按键的高度
     * @return
     */
    public static int getBottomStatusHeight(){
        int totalHeight = getDpi();

        int contentHeight = getScreenHeight();

        return totalHeight  - contentHeight;
    }

    /**  * 标题栏高度  * @return  */
    public static int getTitleHeight(Activity activity){
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusHeight()
    {

        int statusHeight = -1;
        try  {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得屏幕高度 有虚拟按键的时候 会减去虚拟按键的高度 没有的话就是屏幕的分辨率 等于getDPi方法获得的值
     *
     * @return
     */
    public static int getScreenHeight()
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 获得屏幕高度 有虚拟按键的时候 会减去虚拟按键的高度 没有的话就是屏幕的分辨率 等于getDPi方法获得的值
     *
     * @return
     */
    public static int getContentDisplayHeight()
    {
        return getScreenHeight()-getStatusBarHeight();
    }

    public static int getStatusBarHeight(){
        Class<?> c ;
        Object obj ;
        Field field ;
        int x , statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 像素转换
     */

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 像素转换
     */

    public static int px2dp(float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 判断设备当前是否横屏
     *
     * @return Null表示无法判断（屏幕宽高相同）
     */

    public static Boolean isLandscape(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_UNDEFINED)
        {
            // 未定义
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            if (width < height)
            {
                // 竖屏
                return false;
            }
            else if (width > height)
            {
                // 横屏
                return true;
            }
            else
            {
                // 屏幕宽高相同
                return null;
            }
        }
        else if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // 竖屏
            return false;
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // 横屏
            return true;
        }

        return null;
    }

    /**
     * 判断当前的手机屏幕是否开启了自动旋转这个选项
     */

    public static boolean isAutoRotate(Context context) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION) == 1;
    }

    public static float getFontHeight(Paint paint) {
        return paint.descent() - paint.ascent();
    }

    public static float getLineHeight(Paint paint) {
        return paint.getFontSpacing();
    }
}
