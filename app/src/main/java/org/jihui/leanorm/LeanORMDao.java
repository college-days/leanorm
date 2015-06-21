package org.jihui.leanorm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.jihui.leanorm.util.IOUtils;
import org.jihui.leanorm.util.NaturalOrderComparator;
import org.jihui.leanorm.util.SqlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zjh on 2015/5/23.
 */
public class LeanORMDao {
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

    public static <T> List<T> findByField(final Class<T> resource, Tuple condition) {
        Log.d("LEAN_ORM", "start to select all records");
        String tablename = resource.getSimpleName().toLowerCase();
        SQLiteDatabase db = LeanORMManager.helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tablename + " where " + condition.left + "='" + condition.right + "'", null);

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

    public static <T> T findById(final Class<T> resource, long id) {
        try {
            String tablename = resource.getSimpleName().toLowerCase();
            SQLiteDatabase db = LeanORMManager.helper.getReadableDatabase();
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
        Log.d("LEAN_ORM", "start to select all records");
        String tablename = resource.getSimpleName().toLowerCase();
        SQLiteDatabase db = LeanORMManager.helper.getReadableDatabase();
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

    public static boolean executeMigrations(String sqlfilename) {
        android.util.Log.d("LEAN_ORM", "start migration");
        SQLiteDatabase db = LeanORMManager.helper.getWritableDatabase();
        boolean migrationExecuted = false;
        try {
            final List<String> files = Arrays.asList(Configuration.context.getAssets().list(LeanORMDBHelper.MIGRATION_PATH));
            Collections.sort(files, new NaturalOrderComparator());

            db.beginTransaction();
            try {
                for (String file : files) {
                    String filename = file.replace(".sql", "");
                    Log.d("LEAN_ORM", filename);
                    if (filename.equals(sqlfilename)) {
                        executeSqlScript(db, file);
                        migrationExecuted = true;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException e) {
            org.jihui.leanorm.util.Log.e("Failed to execute migrations.", e);
        }

        return migrationExecuted;
    }

    public static void executeSqlScript(SQLiteDatabase db, String file) {
        InputStream stream = null;
        try {
            stream = Configuration.context.getAssets().open(LeanORMDBHelper.MIGRATION_PATH + "/" + file);
            executeLegacySqlScript(db, stream);
        } catch (IOException e) {
            org.jihui.leanorm.util.Log.e("Failed to execute " + file, e);

        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public static void executeDelimitedSqlScript(SQLiteDatabase db, InputStream stream) throws IOException {
        List<String> commands = SqlParser.parse(stream);
        for (String command : commands) {
            db.execSQL(command);
        }
    }

    public static void executeLegacySqlScript(SQLiteDatabase db, InputStream stream) throws IOException {
        InputStreamReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new InputStreamReader(stream);
            buffer = new BufferedReader(reader);
            String line = null;

            while ((line = buffer.readLine()) != null) {
                line = line.replace(";", "").trim();
                Log.d("LEAN_ORM", line);
                if (!TextUtils.isEmpty(line)) {
                    Log.d("LEAN_ORM", line);
                    db.execSQL(line);
                }
            }
        } finally {
            IOUtils.closeQuietly(buffer);
            IOUtils.closeQuietly(reader);
        }
    }
}