package com.gitzzp.ecode.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;

import dalvik.system.DexClassLoader;

/**
 * Android系统工具类
 *
 * {@link #setFullScreenMode(Activity,boolean)}               设置全屏模式
 * {@link #setLandscapeMode(Activity)}                        设置横屏模式
 * {@link #setKeepScreenOn(Activity)}                         设置屏幕常亮
 * {@link #setClearKeepScreenOn(Activity)}                    清除屏幕常亮
 * {@link #setBackWindowBlur(Activity)}                       设置背景模糊 例如弹出dialog的时候 虚化后边activity的背景
 * {@link #getWindowBackground(Context)}                      获取当前窗口的背景颜色
 * {@link #getResolution(Activity)}                           获取DisplayMetrics对象
 * {@link #getVersion()}                                      获取手机的版本号 返回类型为int
 * {@link #getAllocatedMemory(Context)}                       返回应用可分配的内存 MB
 * {@link #isAppForeGround(Context)}                          判断当前应用是否在前台运行
 * {@link #isAppRunning(Context)}                             判断本应用程序进程是否还在运行（或被系统杀死）
 * {@link #ShowHomeScreen(Context)}                           模拟home键 显示系统桌面
 * {@link #startApp(Context, String)}                         根据包名启动已安装APK
 * {@link #getUninstalledAPKResources(Context, String)}       获取未安装APK包的资源 返回的是apk安装包的路径
 * {@link #loadUninstalledAPKClass(String, String, String)}   解压未安装的apk文件
 * {@link #getAllApps(Context, boolean)}                      获取当前手机所有的应用 包括未安装的 可以选择是否扫描系统预装app
 * {@link #isSystemApplication(Context)}                      判断当前应用是否为系统应用
 * {@link #getSignInfo(Context)}                              获取当前应用的签名
 * {@link #showUninstallAPKSignatures(String)}                根据安装包的路径来获取安装包的签名信息 多用于升级时的签名信息比对
 * {@link #getServiceIBinder(String)}                         通过name获得系统隐藏服务的ibinder对象
 * {@link #getVersionCode(Context)}                           获取版本号
 * {@link #getVersionName(Context)}                           获取应用版本名称
 * {@link #shareToOtherApp(Context, String, String, String)}  调用系统分享
 *
 */

public final class AndroidUtil {

    /**
     * 设置全屏模式
     * 
     * @param noTitle 去掉标题栏
     */

    public static void setFullScreenMode(Activity a, boolean noTitle) {
        a.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (noTitle)
        {
            a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 设置横屏模式
     */

    public static void setLandscapeMode(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕常亮
     */

    public static void setKeepScreenOn(Activity a) {
        a.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    /**
     * 清楚屏幕常亮
     */

    public static void setClearKeepScreenOn(Activity a) {
        a.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 设置背景窗口模糊
     * 
     * @deprecated
     */

    public static void setBackWindowBlur(Activity a) {
        a.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    /**
     * 获取当前窗口的背景颜色
     */
    public static Drawable getWindowBackground(Context context) {
        int[] attrs = { android.R.attr.windowBackground };

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(attrs[0], outValue, true);

        TypedArray a = context.obtainStyledAttributes(outValue.resourceId, attrs);
        Drawable windowBackground = a.getDrawable(0);
        a.recycle();

        return windowBackground;
    }

    /**
     * 获取DisplayMetrics对象
     */
    public static DisplayMetrics getResolution(Activity a) {
        DisplayMetrics dm = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取手机SDK版本号<br>
     * {@link android.os.Build.VERSION_CODES}<br>
     * 3---1.5<br>
     * 4---1.6<br>
     * 5---2.0<br>
     * 7---2.1<br>
     * 8---2.2<br>
     * 10--2.3.3<br>
     * 11---3.0<br>
     * 12---3.1<br>
     * 13---3.2<br>
     * 14---4.0<br>
     * 15---4.0.3<br>
     * 16---4.1.2<br>
     * 17---4.2.2<br>
     * 18---4.3<br>
     * 19---4.4.2<br>
     */

    public static int getVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取应用程序可分配的内存
     * 
     * @return MB
     */

    public static int getAllocatedMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getMemoryClass();
    }

    /**
     * 判断本程序是否正处于前台运行<br>
     * 需要声明权限<uses-permission android:name="android.permission.GET_TASKS" />
     */

    public static boolean isAppForeGround(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && !list.isEmpty())
        {
            RunningTaskInfo task = list.get(0);
            if (task != null
			&& (task.baseActivity != null
			&&  task.baseActivity.getPackageName().equals(context.getPackageName()))
			|| (task.topActivity != null
			&&  task.topActivity.getPackageName().equals(context.getPackageName())))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断本应用程序进程是否还在运行（或被系统杀死）<br>
     */

    public static boolean isAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list != null && !list.isEmpty())
        {
            for (RunningAppProcessInfo process : list)
            {
                if (process.processName.equals(context.getApplicationInfo().processName))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 模拟home键 显示桌面屏幕
     */

    public static void ShowHomeScreen(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 根据包名启动已安装APK
     */

    public static void startApp(Context context, String packageName) throws Exception {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(packageName, 0);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // context不是activity的时候需要加这一句
        intent.setPackage(pi.packageName);

        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        if (list != null && !list.isEmpty())
        {
            ResolveInfo ri = list.get(0);
            intent.setComponent(new ComponentName(pi.packageName, ri.activityInfo.name));
            context.startActivity(intent);
        }
    }

    /**
     * 获取未安装APK包的资源
     * 
     * @param apkPath 安装包的路径
     */

    public static Resources getUninstalledAPKResources(Context context, String apkPath)
            throws Exception {
        // Resources res = getResources();
        // AssetManager am = new AssetManager();
        // am.addAssetPath(apkPath);
        // Resources apk_res = new Resources(am, res.getDisplayMetrics(),
        // res.getConfiguration());
        Resources res = context.getResources();
        Class<?> amClass = Class.forName("android.content.res.AssetManager");
        Object am = amClass.newInstance();
        amClass.getDeclaredMethod("addAssetPath", String.class).invoke(am, apkPath);
        return Resources.class.getConstructor(amClass, DisplayMetrics.class, Configuration.class)
                .newInstance(am, res.getDisplayMetrics(), res.getConfiguration());
    }
    
    /**
     * 加载未安装APK包的类
     * 
     * @param apkPath 安装包的路径
     * @param outDir 解压目录
     * @param className 欲加载的类名
     */
    
    public static Class<?> loadUninstalledAPKClass(String apkPath, String outDir, String className)
            throws Exception {
        new File(outDir).mkdirs();
        DexClassLoader loader = new DexClassLoader(
                apkPath, 
                outDir, 
                null, 
                ClassLoader.getSystemClassLoader());
        return loader.loadClass(className);
    }

    /**
     * 获取手机所有应用（包括未安装的）
     * 
     * @param includeSystem 是否包含系统预装的应用程序
     */

    public static List<PackageInfo> getAllApps(Context context, boolean includeSystem) {
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES| PackageManager.GET_DISABLED_COMPONENTS);
        PackageInfo pi = null;
        for (ListIterator<PackageInfo> iter = list.listIterator(); iter.hasNext(); pi = iter.next())
        {
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
            ||  (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            {
                // 非系统预装的第三方应用程序
                continue;
            }
            else if (!includeSystem)
            {
                iter.remove();
            }
        }

        return list;
    }

    /**
     * 判断当前应用是否是系统软件
     */

    public static boolean isSystemApplication(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) > 0;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取当前应用的签名
     */
    public static Signature getSignInfo(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            return pi.signatures[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据安装包的路径来获取安装包的签名信息 多用于升级时的签名信息比对
     */
    private String showUninstallAPKSignatures(String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        try {
            // apk包的文件路径
            // 这是一个Package 解释器, 是隐藏的
            // 构造函数的参数只有一个, apk文件的路径
            // PackageParser packageParser = new PackageParser(apkPath);
            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
//            MediaApplication.logD(DownloadApk.class, "pkgParser:" + pkgParser.toString());
            // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            // PackageParser.Package mPkgInfo = packageParser.parsePackage(new
            // File(apkPath), apkPath,
            // metrics, 0);
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage",
                    typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = PackageManager.GET_SIGNATURES;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);

            typeArgs = new Class[2];
            typeArgs[0] = pkgParserPkg.getClass();
            typeArgs[1] = Integer.TYPE;
            Method pkgParser_collectCertificatesMtd = pkgParserCls.getDeclaredMethod("collectCertificates",
                    typeArgs);
            valueArgs = new Object[2];
            valueArgs[0] = pkgParserPkg;
            valueArgs[1] = PackageManager.GET_SIGNATURES;
            pkgParser_collectCertificatesMtd.invoke(pkgParser, valueArgs);
            // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
            Field packageInfoFld = pkgParserPkg.getClass().getDeclaredField("mSignatures");
            Signature[] info = (Signature[]) packageInfoFld.get(pkgParserPkg);
//            MediaApplication.logD(DownloadApk.class, "size:"+info.length);
//            MediaApplication.logD(DownloadApk.class, info[0].toCharsString());
            return info[0].toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过name获得系统隐藏服务的ibinder对象
     */
    public static IBinder getServiceIBinder(String name) throws Exception {
        // 通过反射获取到sdk隐藏的服务
        Method m = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
        IBinder binder = (IBinder) m.invoke(null, name); // 激活服务
        return binder;
    }

    /**
     * 获取应用版本号
     */

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 获取应用版本名称
     */

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 调用系统分享
     */
    public static void shareToOtherApp(Context context,String title,String content, String dialogTitle ) {
        Intent intentItem = new Intent(Intent.ACTION_SEND);
        intentItem.setType("text/plain");
        intentItem.putExtra(Intent.EXTRA_SUBJECT, title);
        intentItem.putExtra(Intent.EXTRA_TEXT, content);
        intentItem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intentItem, dialogTitle));
    }

}