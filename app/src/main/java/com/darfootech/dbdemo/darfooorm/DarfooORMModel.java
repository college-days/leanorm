package com.darfootech.dbdemo.darfooorm;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by zjh on 2015/5/24.
 */
public abstract class DarfooORMModel {
    //primary key here
    public long _id;

    public final void save() {
        try {
            SQLiteDatabase db = DarfooORMManager.helper.getWritableDatabase();
            final ContentValues values = new ContentValues();

            Long mId = (Long) DarfooORMDao.getResourceAttr(getClass(), this, "_id");

            for (Field field : getClass().getFields()) {
                Log.d("DARFOO_ORM", getClass().getSimpleName().toLowerCase() + " insert field name -> " + field.getName());
                Class<?> fieldType = field.getType();
                Log.d("DARFOO_ORM", getClass().getSimpleName().toLowerCase() + " insert field type name -> " + fieldType.getSimpleName());

                String fieldName = field.getName();
                if (fieldName.toLowerCase().equals("_id")) {
                    continue;
                }
                field.setAccessible(true);

                Object value = field.get(this);

                if (value == null) {
                    values.putNull(fieldName);
                } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                    values.put(fieldName, (Byte) value);
                } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                    values.put(fieldName, (Short) value);
                } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                    Log.d("DARFOO_ORM", fieldName + " this is a integer value");
                    values.put(fieldName, (Integer) value);
                } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                    values.put(fieldName, (Long) value);
                } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                    values.put(fieldName, (Float) value);
                } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                    values.put(fieldName, (Double) value);
                } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                    values.put(fieldName, (Boolean) value);
                } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                    values.put(fieldName, value.toString());
                } else if (fieldType.equals(String.class)) {
                    Log.d("DARFOO_ORM", fieldName + " this is a string value");
                    values.put(fieldName, value.toString());
                } else if (fieldType.equals(Byte[].class) || fieldType.equals(byte[].class)) {
                    values.put(fieldName, (byte[]) value);
                }
            }

            Log.d("DARFOO_ORM", "start to insert record");
            String tablename = getClass().getSimpleName().toLowerCase();
            if (mId == null || mId == 0) {
                Log.d("DARFOO_ORM", "insert record");
                db.insert(tablename, null, values);
            } else {
                Log.d("DARFOO_ORM", "update record");
                db.update(tablename, values, "_id=" + mId, null);
            }
            Log.d("DARFOO_ORM", "mid -> " + mId);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public final void delete() {
        SQLiteDatabase db = DarfooORMManager.helper.getWritableDatabase();
        Long mId = (Long) DarfooORMDao.getResourceAttr(getClass(), this, "_id");
        String tablename = getClass().getSimpleName().toLowerCase();
        int res = db.delete(tablename, "_id=" + mId, null);
        Log.d("DARFOO_ORM", "delete mid -> " + mId);
    }
}
