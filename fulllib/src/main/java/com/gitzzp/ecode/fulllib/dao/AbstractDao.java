package com.gitzzp.ecode.fulllib.dao;

import android.content.Context;

import com.gitzzp.ecode.fulllib.bean.CoreBean;
import com.j256.ormlite.dao.Dao;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/17 16:47
 * 类描述:
 */
public abstract class AbstractDao<T extends CoreBean> implements BaseDao<T> {

    private Context context;

    private Class<T> entityClass;

    private Dao<T,Integer> baseDao;

    private DatabaseHelper helper;

    public AbstractDao(Context context){
        super();
        this.context = context;
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
        try{
            helper = DatabaseHelper.getHelper(context);
            baseDao = helper.getDao(entityClass);
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    public void insert(T t){
        try{
            baseDao.create(t);
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    public boolean delete(T t){
        try{
            return baseDao.delete(t)==1;
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        return false;
    }

    public void update(T t){
        try{
            baseDao.update(t);
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    public List<T> queryAll(){
        try{
            return baseDao.queryForAll();
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        return null;
    }

    public List<T> query(String key,String value){
        try{
            return baseDao.queryForEq(key,value);
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        return null;
    }

    @Override
    public T get() {
        try{
            return entityClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        return null;
    }

    public Class getT() {
        try{
            return entityClass;
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        return null;
    }
}
