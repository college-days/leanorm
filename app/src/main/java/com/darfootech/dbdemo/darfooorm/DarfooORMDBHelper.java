package com.darfootech.dbdemo.darfooorm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zjh on 15-4-25.
 */
public class DarfooORMDBHelper extends SQLiteOpenHelper {

    public DarfooORMDBHelper() {
        super(Configuration.context, (String) Configuration.getMetaData(Configuration.DARFOO_DB_NAME), null, (Integer) Configuration.getMetaData(Configuration.DARFOO_DB_VERSION));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new DarfooORMCota().createTable(db);
        //new DarfooORMCota().dropTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
