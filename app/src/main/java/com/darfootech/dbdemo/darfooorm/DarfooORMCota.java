package com.darfootech.dbdemo.darfooorm;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjh on 2015/5/23.
 */
public class DarfooORMCota {
    public void dropTable(SQLiteDatabase db) {
        try {
            String[] classeNames = ((String) Configuration.getMetaData(Configuration.DARFOO_MODELS)).split(",");
            for (String name : classeNames) {
                Class<? extends DarfooORMModel> resource = null;
                resource = (Class<? extends DarfooORMModel>) Class.forName(name);
                String dropsql = String.format("DROP TABLE IF EXISTS %s", resource.getSimpleName().toLowerCase());
                Log.d("DARFOO_ORM", dropsql);
                db.execSQL(dropsql);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTable(SQLiteDatabase db) {
        try {
            String[] classeNames = ((String) Configuration.getMetaData(Configuration.DARFOO_MODELS)).split(",");
            for (String name : classeNames) {
                Class<? extends DarfooORMModel> resource = (Class<? extends DarfooORMModel>) Class.forName(name);
                List<String> statements = new ArrayList<String>();
                for (Field field : resource.getFields()) {
                    String fieldName = field.getName();
                    if (fieldName.toLowerCase().equals("_id")) {
                        continue;
                    }
                    Log.d("DARFOO_ORM", "field name -> " + fieldName);
                    Class<?> fieldType = field.getType();
                    Log.d("DARFOO_ORM", "field type name -> " + fieldType);
                    statements.add(String.format("%s %s", fieldName, TypeClassMapping.typeClassMapping.get(fieldType)));
                }
                //default use _id as the primary key for specific table
                String createsql = String.format("CREATE TABLE IF NOT EXISTS %s (_id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, %s)", resource.getSimpleName().toLowerCase(), StringUtils.join(statements, ", "));
                Log.d("DARFOO_ORM", createsql);
                db.execSQL(createsql);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
