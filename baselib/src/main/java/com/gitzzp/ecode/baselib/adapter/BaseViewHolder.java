package com.gitzzp.ecode.baselib.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/**
 * ViewHolder基类
 *
 * Created by gitzzp on 16-5-27.
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder {

    public BaseViewHolder(Context context, ViewGroup parent, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, parent, false));
        //封装了基础类库 所以butterknife需要子类在构造方法中去写了
        //这里使用了ButterKnife来进行控件的绑定
//        ButterKnife.bind(this, itemView);
    }

    /**
     * 方便其子类进行一些需要Context的操作.
     *
     * @return 调用者的Context
     */
    public Context getContext() {
        return itemView.getContext();
    }

    /**
     * 抽象方法，绑定数据.
     * 让子类自行对数据和view进行绑定
     *
     * @param itemValue Item的数据
     * @param position  当前item的position
     * @param listener  点击事件监听者
     */
    protected abstract void bindData(V itemValue, int position, @Nullable BaseRecycleAdapter.OnItemClickListener listener);

    /**
     * 用于传递数据和信息
     *
     * @param itemValue
     * @param position
     * @param listener
     */
    public void setData(V itemValue, int position, BaseRecycleAdapter.OnItemClickListener listener) {
        bindData(itemValue, position, listener);
    }
}
