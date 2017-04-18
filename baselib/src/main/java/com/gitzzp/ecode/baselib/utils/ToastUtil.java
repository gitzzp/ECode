package com.gitzzp.ecode.baselib.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gitzzp.ecode.baselib.ECode;
import com.gitzzp.ecode.baselib.R;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 12:03
 * 类描述:
 */
public class ToastUtil {

    private static Toast mToast;

    public static Toast getSingletonToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(ECode.getContext(), resId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    public static Toast getSingletonToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(ECode.getContext(), text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getSingleLongToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(ECode.getContext(), resId, Toast.LENGTH_LONG);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    public static Toast getSingleLongToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(ECode.getContext(), text, Toast.LENGTH_LONG);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getToast(int resId) {
        return Toast.makeText(ECode.getContext(), resId, Toast.LENGTH_SHORT);
    }

    public static Toast getToast(String text) {
        return Toast.makeText(ECode.getContext(), text, Toast.LENGTH_SHORT);
    }

    public static Toast getLongToast(int resId) {
        return Toast.makeText(ECode.getContext(), resId, Toast.LENGTH_LONG);
    }

    public static Toast getLongToast(String text) {
        return Toast.makeText(ECode.getContext(), text, Toast.LENGTH_LONG);
    }

    public static void showImageToast(int res){
        Toast toast = new Toast(ECode.getContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(ECode.getContext()).inflate(R.layout.layout_custom_image_toast,null);
        ((ImageView)view.findViewById(R.id.image)).setImageResource(res);
        toast.setView(view);
        toast.show();
    }

    public static void showSingletonToast(int resId) {
        getSingletonToast(resId).show();
    }


    public static void showSingletonToast(String text) {
        getSingletonToast(text).show();
    }

    public static void showSingleLongToast(int resId) {
        getSingleLongToast(resId).show();
    }


    public static void showSingleLongToast(String text) {
        getSingleLongToast(text).show();
    }

    public static void showToast(int resId) {
        getToast(resId).show();
    }

    public static void showToast(String text) {
        getToast(text).show();
    }

    public static void showLongToast(int resId) {
        getLongToast(resId).show();
    }

    public static void showLongToast(String text) {
        getLongToast(text).show();
    }

}
