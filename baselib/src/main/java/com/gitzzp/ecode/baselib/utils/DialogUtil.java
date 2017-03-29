package com.gitzzp.ecode.baselib.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.gitzzp.ecode.baselib.R;
import com.gitzzp.ecode.baselib.view.LoadingDialog;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 12:13
 * 类描述:
 */
public class DialogUtil {

    private static ProgressDialog loadingDialog;

    public static void showLoadingDialog(Context context){
        cancelLoadingDialog();
        try {
            loadingDialog = new LoadingDialog(context, R.style.CustomProgressDialog);
            loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelLoadingDialog(){
        if(LoadingDialogIsShowing()){
            try{
                loadingDialog.cancel();
                loadingDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static boolean LoadingDialogIsShowing(){
        return loadingDialog!=null&&loadingDialog.isShowing();
    }

}
