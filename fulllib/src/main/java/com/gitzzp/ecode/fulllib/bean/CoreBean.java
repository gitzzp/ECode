package com.gitzzp.ecode.fulllib.bean;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/11 16:18
 * 类描述:
 */
public class CoreBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
