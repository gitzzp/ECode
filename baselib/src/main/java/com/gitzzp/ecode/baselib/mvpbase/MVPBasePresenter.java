package com.gitzzp.ecode.baselib.mvpbase;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 创建人：gitzzp
 * 创建日期:16/12/19 18:02
 * 类描述: 为避免因presenter中的耗时操作 而导致view不能及时被回收操作的内存泄漏
 *
 * view通过泛型传递进来 presenter对其持有弱引用
 */
public abstract class MVPBasePresenter<T> {

    //view接口类型的弱引用
    protected Reference<T> mViewRef;

    //建立关联
    public void attachView(T view){
        mViewRef = new WeakReference<>(view);
    }

    //获取view
    protected T getView(){
        return mViewRef.get();
    }

    //判断是否与view建立了关联
    public boolean isViewAttached(){
        return mViewRef !=null && mViewRef.get() !=null;
    }

    //解除关联
    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
