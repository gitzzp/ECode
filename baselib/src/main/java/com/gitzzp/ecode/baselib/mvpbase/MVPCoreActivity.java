package com.gitzzp.ecode.baselib.mvpbase;

import android.os.Bundle;

import com.gitzzp.ecode.baselib.core.CoreActivity;

/**
 * 创建人：gitzzp
 * 创建日期:16/12/19 18:11
 * 类描述: 两个泛型参数 第一个是view接口类型(子类中自行指定) 第二个是presenter具体类型
 */
public abstract class MVPCoreActivity<V,T extends MVPBasePresenter<V>> extends CoreActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter(); //创建presenter

        mPresenter.attachView((V) this); //建立关联

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(); //解除关联
    }

    protected abstract T createPresenter();

}
