package com.gitzzp.ecode.fulllib.dao;

import android.content.Context;

import com.gitzzp.ecode.fulllib.bean.CoreBean;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/17 16:43
 * 类描述:
 */
public abstract class MyDao extends AbstractDao<CoreBean> {

    public MyDao(Context context) {
        super(context);
    }
}
