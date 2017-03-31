package com.gitzzp.ecode.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.gitzzp.ecode.baselib.adapter.BaseRecycleAdapter;
import com.gitzzp.ecode.baselib.adapter.BaseViewHolder;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/31 16:28
 * 类描述: BaseRecycleAdapter例子
 */
public class BaseRecycleAdapterExample extends BaseRecycleAdapter<Object> {


    public BaseRecycleAdapterExample(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseViewHolder createHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolderExample(mContext,parent,viewType);
    }

    class BaseViewHolderExample extends BaseViewHolder<Object>{

        public BaseViewHolderExample(Context context, ViewGroup parent, int layoutRes) {
            super(context, parent, layoutRes);
        }

        @Override
        protected void bindData(Object itemValue, int position, @Nullable OnItemClickListener listener) {
            //to do Something
        }
    }
}
