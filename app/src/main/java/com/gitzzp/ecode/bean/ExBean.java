package com.gitzzp.ecode.bean;

import com.gitzzp.ecode.fulllib.bean.CoreBean;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/17 17:08
 * 类描述:
 */
@DatabaseTable(tableName = "ex.db")
public class ExBean extends CoreBean {

    @DatabaseField
    private int ex1;

    @DatabaseField
    private String ex2;

    @DatabaseField
    private double ex3;

    @DatabaseField
    private boolean ex4;

    @DatabaseField(columnName = "url")
    private String ex5;
}
