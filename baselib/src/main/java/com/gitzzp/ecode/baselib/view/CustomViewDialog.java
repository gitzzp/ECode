package com.gitzzp.ecode.baselib.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gitzzp.ecode.baselib.R;


/**
 * 创建人：gitzzp
 * 创建日期:17/3/1 11:46
 * 类描述:
 */
public class CustomViewDialog extends Dialog {

    private CustomViewDialog(Context context) {
        super(context);
    }

    private CustomViewDialog(Context context, int theme) {
        super(context, theme);
    }

    public abstract static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }


        public CustomViewDialog create() {
            CustomViewDialog dialog = new CustomViewDialog(context, R.style.CustomProgressDialog);

            View root = setLayout(dialog);

            dialog.addContentView(root, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setContentView(root);
            return dialog;
        }

        public abstract View setLayout(Dialog dialog);


    }
}
