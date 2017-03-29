package com.gitzzp.ecode.baselib.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 创建人：gitzzp
 * 创建日期:16/12/19 18:20
 * 类描述:
 */
public abstract class MVPBaseFragment<V,T extends MVPBasePresenter<V>> extends Fragment {

    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mPresenter ==null){
            mPresenter = createPresenter();
        }

        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
