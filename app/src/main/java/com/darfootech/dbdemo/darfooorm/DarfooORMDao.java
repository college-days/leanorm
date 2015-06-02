package com.darfootech.dbdemo.darfooorm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjh on 2015/5/23.
 */
public class DarfooORMDao {
    public static Object getResourceAttr(Class resource, Object object, String fieldname) {
        try {
            Field field = resource.getField(fieldname);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T findById(final Class<T> resource, long id) {
        try {
            String tablename = resource.getSimpleName().toLowerCase();
            SQLiteDatabase db = DarfooORMManager.helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + tablename + " where " + "_id" + "='" + id + "'", null);
            Object result = resource.newInstance();

            cursor.moveToFirst();

            for (Field field : resource.getFields()) {
                Class<?> fieldType = field.getType();
                String fieldName = field.getName().toLowerCase();
                field.setAccessible(true);

                int fieldIndex = cursor.getColumnIndex(fieldName);

                if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                    field.setInt(result, cursor.getInt(fieldIndex));
                } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                    field.setLong(result, cursor.getLong(fieldIndex));
                } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                    field.setFloat(result, cursor.getFloat(fieldIndex));
                } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                    field.setDouble(result, cursor.getDouble(fieldIndex));
                } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                    field.setBoolean(result, (cursor.getInt(fieldIndex) == 1) ? true : false);
                } else if (fieldType.equals(String.class)) {
                    field.set(result, cursor.getString(fieldIndex));
                }
            }

            return (T) result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> findAll(final Class<T> resource) {
        Log.d("DARFOO_ORM", "start to select all records");
        String tablename = resource.getSimpleName().toLowerCase();
        SQLiteDatabase db = DarfooORMManager.helper.getReadableDatabase();
        Cursor cursor = db.query(tablename, null, null, null, null, null, "_id asc");

        List<T> results = new ArrayList<T>();

        try {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                Object result = resource.newInstance();
                for (Field field : resource.getFields()) {
                    Class<?> fieldType = field.getType();
                    String fieldName = field.getName().toLowerCase();
                    field.setAccessible(true);

                    int fieldIndex = cursor.getColumnIndex(fieldName);

                    if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                        field.setInt(result, cursor.getInt(fieldIndex));
                    } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                        field.setLong(result, cursor.getLong(fieldIndex));
                    } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                        field.setFloat(result, cursor.getFloat(fieldIndex));
                    } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                        field.setDouble(result, cursor.getDouble(fieldIndex));
                    } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                        field.setBoolean(result, (cursor.getInt(fieldIndex) == 1) ? true : false);
                    } else if (fieldType.equals(String.class)) {
                        field.set(result, cursor.getString(fieldIndex));
                    }
                }
                results.add((T) result);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        cursor.close();
        return results;
    }
}