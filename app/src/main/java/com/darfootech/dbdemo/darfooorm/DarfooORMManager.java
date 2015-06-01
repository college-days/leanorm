package com.darfootech.dbdemo.darfooorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zjh on 2015/5/25.
 */
public class DarfooORMManager {
    public static DarfooORMDBHelper helper;

    public static void initialize(Context context) {
        Configuration.context = context;
        helper = new DarfooORMDBHelper();
    }

    public static synchronized SQLiteDatabase openDatabase() {
        return helper.getWritableDatabase();
    }
}
