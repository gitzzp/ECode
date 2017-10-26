package com.gitzzp.ecode.fulllib.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 创建人：gitzzp
 * 创建日期:17/4/11 16:06
 * 类描述:
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String DB_NAME = "song.db";

    private static DatabaseHelper instance;

    private static Map<String,Class> data = null;

    private DatabaseHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    public static void initDatabaseHelper(String dbName,HashMap<String,Class> dataMap){
        DB_NAME = dbName;
        data = dataMap;
    }

    public static synchronized DatabaseHelper getHelper(Context context){
        context = context.getApplicationContext();
        if(data == null || data.size()==0)
            throw new RuntimeException("data is null,you should init this before used");
        if(instance == null){
            synchronized (DatabaseHelper.class) {
                if(instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            if(data != null){
                Iterator iterator = data.keySet().iterator();
                while (iterator.hasNext()){
                    Class c = data.get(iterator.next());
                    TableUtils.createTable(connectionSource,c);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(data != null){
                Iterator iterator = data.keySet().iterator();
                while (iterator.hasNext()){
                    Class c = data.get(iterator.next());
                    TableUtils.dropTable(connectionSource,c,true);
                }
            }
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
