package com.gitzzp.ecode.fulllib.dao;

import com.gitzzp.ecode.fulllib.bean.CoreBean;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/11 16:35
 * 类描述:
 */
public interface BaseDao<T extends CoreBean> {

    T get();

//    private Context context;
//
//    private Dao<V,Integer> baseDao;
//
//    private DatabaseHelper helper;
//
//    public BaseDao(Context context){
//        this.context = context;
//        try{
//            helper = DatabaseHelper.getHelper(context);
//            baseDao = helper.getDao(helper.getDataClass());
//        }catch (Exception e){
//            e.printStackTrace();
//            CrashReport.postCatchedException(e);
//        }
//    }

}
