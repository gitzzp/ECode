package com.gitzzp.ecode.dao;

import android.app.Application;
import android.content.Context;

import com.gitzzp.ecode.bean.ExBean;
import com.gitzzp.ecode.fulllib.dao.AbstractDao;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/17 17:07
 * 类描述:
 */
public class ExDao extends AbstractDao<ExBean> {

    public ExDao(){
        super(new Application());
    }

    public ExDao(Context context) {
        super(context);
    }
}
