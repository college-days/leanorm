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
    public void createTable(SQLiteDatabase db) throws ClassNotFoundException {
        String[] classeNames = ((String) Configuration.getMetaData(Configuration.DARFOO_MODELS)).split(",");
        for (String name : classeNames) {
            Class<? extends DarfooModel> resource = (Class<? extends DarfooModel>) Class.forName(name);
            List<String> statements = new ArrayList<String>();
            for (Field field : resource.getFields()) {
                String fieldName = field.getName();
                Log.d("DARFOO_ORM", "field name -> " + fieldName);
                Class<?> fieldType = field.getType();
                String fieldTypeName = fieldType.getSimpleName().toLowerCase();
                Log.d("DARFOO_ORM", "field type name -> " + fieldType);
                if (fieldTypeName.equals("int") || fieldTypeName.equals("long")) {
                    statements.add(String.format("%s %s", fieldName, TypeClassMapping.typeClassMapping.get(Integer.class)));
                } else {
                    statements.add(String.format("%s %s", fieldName, TypeClassMapping.typeClassMapping.get(fieldType)));
                }
            }
            String createsql = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY, %s)", resource.getSimpleName().toLowerCase(), StringUtils.join(statements, ", "));
            Log.d("DARFOO_ORM", createsql);
        }
    }
}
