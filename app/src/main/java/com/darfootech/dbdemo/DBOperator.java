package com.darfootech.dbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zjh on 2015/5/22.
 */
public class DBOperator extends SQLiteOpenHelper {
    public DBOperator(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //建表语句一定不能写错了 不然会导致崩溃 所以才需要有darfooorm
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists hero_info(" +
                "id integer primary key," +
                "name varchar," +
                "level integer," +
                "desc nvarchar(300)," +
                "message varchar)");
    }

    //versioncode递增以后就可以回调这个函数来更新原有的表结构
    //create里面的建表语句也是要变的和upgrade修改之后的表结构一致新用户是直接调用oncreate的
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "alter table hero_info add message varchar default ''";
        db.execSQL(sql);
    }
}
