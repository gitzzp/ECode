package com.gitzzp.ecode.baselib.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gitzzp.ecode.baselib.ECode;
import com.gitzzp.ecode.baselib.R;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 12:03
 * 类描述:
 */
public class ToastUtil {

    public static void showStringToast(@NotNull String msg){
        Toast.makeText(ECode.getContext(),msg,Toast.LENGTH_SHORT).show();
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
}
