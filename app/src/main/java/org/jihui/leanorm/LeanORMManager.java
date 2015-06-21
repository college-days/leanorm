package org.jihui.leanorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zjh on 2015/5/25.
 */
public class LeanORMManager {
    public static LeanORMDBHelper helper;

    public static void initialize(Context context) {
        Configuration.context = context;
        helper = new LeanORMDBHelper();
    }

    public static synchronized SQLiteDatabase openDatabase() {
        return helper.getWritableDatabase();
    }
}
