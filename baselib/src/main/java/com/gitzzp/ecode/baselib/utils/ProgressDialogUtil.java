package com.gitzzp.ecode.baselib.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.gitzzp.ecode.baselib.R;

import static com.gitzzp.ecode.baselib.R.style.CustomProgressDialog;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/18 16:19
 * 类描述:
 */
public class ProgressDialogUtil extends ProgressDialog {

    private boolean cancelable = false;

    private static ProgressDialog progressDialog;

    /**
     * 默认点击外部不可取消
     * @param context
     */
    public static void showProgressDialog(Context context){
        showProgressDialog(context,false);
    }
    public static void showProgressDialog(Context context,boolean cancelable){
        cancelProgressDialog();
        try {
            progressDialog = new ProgressDialogUtil(context, CustomProgressDialog,cancelable);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelProgressDialog(){
        if(ProgressDialogIsShowing()){
            try{
                progressDialog.cancel();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        progressDialog = null;
    }

    public static boolean ProgressDialogIsShowing(){
        return progressDialog!=null&&progressDialog.isShowing();
    }

    public ProgressDialogUtil(Context context) {
        super(context);
    }

    public ProgressDialogUtil(Context context, int theme) {
        super(context, theme);
    }

    public ProgressDialogUtil(Context context, int theme, boolean cancelable) {
        super(context, theme);
        this.cancelable = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);

        setContentView(R.layout.load_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
}
