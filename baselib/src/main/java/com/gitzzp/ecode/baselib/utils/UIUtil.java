package com.gitzzp.ecode.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * UI工具库
 * 
 */

public final class UIUtil {

    /**
     * 修正列表的高度（与ScrollView不兼容的bug）
     */

    public static void modifyListViewHeight(ListView listView) {
        modifyListViewHeight(listView, 0);
    }

    /**
     * 修正列表的高度（与ScrollView不兼容的bug）
     * 
     * @param h 高度修正值
     */

    public static void modifyListViewHeight(ListView listView, int h) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null)
        {
            return;
        }

        int size = adapter.getCount();
        if (size == 0)
        {
            listView.getLayoutParams().height = 0;
            return;
        }

        int height = 0;
        View listItem;
        for (int i = 0; i < size; i++)
        {
            listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            height += listItem.getMeasuredHeight() + h;
        }

        listView.getLayoutParams().height = height +
                (listView.getDividerHeight() * (size - 1));
    }

    /**
     * 修正文本显示（换行不均匀的bug）
     */

    public static void modifyTextView(TextView tv, String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = text.length(); i < len; i++)
        {
            String s = text.substring(i, i + 1);
            sb.append(s);
            if (s.matches("\\p{Punct}"))
            {
                sb.append(" ");
            }
        }

        tv.setText(sb.toString());
    }

    /**
     * 生成表格行（共三列，第二列空10dp）
     * 
     * @param text 第一列值
     * @param value 第三列值
     */

    public static TableRow createTableRow(Context context, String text, String value) {
        TableRow tr = new TableRow(context);

        TextView tv = new TextView(context);
        tv.setText(text);
        tr.addView(tv);

        tv = new TextView(context);
        tv.setWidth(10);
        tr.addView(tv);

        tv = new TextView(context);
        tv.setText(value);
        tr.addView(tv);

        return tr;
    }

    /**
     * 获取该Activity所有view
     */

    public static List<View> getAllViews(Activity a) {
        List<View> list = new LinkedList<View>();
        addAllViews(a.getWindow().getDecorView(), list);
        return list;
    }

    private static void addAllViews(View view, List<View> list) {
        list.add(view);
        if (view instanceof ViewGroup)
        {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++)
            {
                addAllViews(group.getChildAt(i), list);
            }
        }
    }

    /**
     * 替换控件（控件ID将会保持）
     * 
     * @return 替换后的控件
     */

    public static View replace(View src, int resId) {
        final ViewParent viewParent = src.getParent();
        if (viewParent instanceof ViewGroup)
        {
            final ViewGroup parent = (ViewGroup) viewParent;
            final View view = LayoutInflater.from(src.getContext()).inflate(resId, parent, false);
            view.setId(src.getId());

            final int index = parent.indexOfChild(src);
            parent.removeViewInLayout(src);
            parent.addView(view, index);

            return view;
        }

        return null;
    }

    /**
     * 替换控件
     */

    public static boolean replace(View src, View des, LayoutParams params) {
        final ViewParent viewParent = src.getParent();
        if (viewParent instanceof ViewGroup)
        {
            final ViewGroup parent = (ViewGroup) viewParent;
            final View view = des;
            view.setId(src.getId());

            final int index = parent.indexOfChild(src);
            parent.removeViewInLayout(src);

            if (params != null)
            {
                parent.addView(view, index, params);
            }
            else
            {
                parent.addView(view, index);
            }

            return true;
        }

        return false;
    }

    /**
     * 设置软键盘状态
     * 
     * @param state -1:隐藏,0:取反,1:显示,其它:延迟显示的时间（毫秒）
     */

    public static void setSoftInputState(View view, int state) {
        final InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (state) {
            case -1:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case 0:
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                break;
            case 1:
                imm.showSoftInput(view, 0);
                break;

            default:
                view.getHandler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, state);
                break;
        }
    }
}