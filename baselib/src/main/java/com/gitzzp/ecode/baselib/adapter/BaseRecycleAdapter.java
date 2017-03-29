package com.gitzzp.ecode.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * 创建人：gitzzp
 * 创建日期:17/2/28 12:10
 * 类描述:
 */
public abstract class BaseRecycleAdapter<V> extends RecyclerView.Adapter<BaseViewHolder<V>> {

    protected Context mContext;

    protected ArrayList<V> list;

    public BaseRecycleAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setList(ArrayList<V> list){
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent,viewType);
    }

    public abstract BaseViewHolder createHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //BaseViewHolder是我抽象出来的RecyclerView.ViewHolder的基类，下面会有详细讲解
        holder.setData(list.get(position), position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public interface OnItemClickListener<V>{
        void itemClickListener(int index, V item);
    }

    private OnItemClickListener<V> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<V> onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }



}
