package com.darfootech.dbdemo.darfooorm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.darfootech.dbdemo.darfooorm.util.IOUtils;
import com.darfootech.dbdemo.darfooorm.util.Log;
import com.darfootech.dbdemo.darfooorm.util.NaturalOrderComparator;
import com.darfootech.dbdemo.darfooorm.util.SqlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zjh on 15-4-25.
 */
public class DarfooORMDBHelper extends SQLiteOpenHelper {

    private final String mSqlParser;
    public final static String MIGRATION_PATH = "migrations";

    public DarfooORMDBHelper() {
        //version不需要从外部传入
        super(Configuration.context, (String) Configuration.getMetaData(Configuration.DARFOO_DB_NAME), null, (Integer) Configuration.getMetaData(Configuration.DARFOO_DB_VERSION));
        final String mode = Configuration.getMetaData(Configuration.DARFOO_SQL_PARSER);
        if (mode == null) {
            mSqlParser = Configuration.DEFAULT_SQL_PARSER;
        } else {
            mSqlParser = mode;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new DarfooORMCota().createTable(db);
        //new DarfooORMCota().dropTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean executeMigrations(SQLiteDatabase db, int oldVersion, int newVersion) {
        boolean migrationExecuted = false;
        try {
            final List<String> files = Arrays.asList(Configuration.context.getAssets().list(MIGRATION_PATH));
            Collections.sort(files, new NaturalOrderComparator());

            db.beginTransaction();
            try {
                for (String file : files) {
                    try {
                        final int version = Integer.valueOf(file.replace(".sql", ""));

                        if (version > oldVersion && version <= newVersion) {
                            executeSqlScript(db, file);
                            migrationExecuted = true;

                            Log.i(file + " executed succesfully.");
                        }
                    }
                    catch (NumberFormatException e) {
                        Log.w("Skipping invalidly named file: " + file, e);
                    }
                }
                db.setTransactionSuccessful();
            }
            finally {
                db.endTransaction();
            }
        }
        catch (IOException e) {
            Log.e("Failed to execute migrations.", e);
        }

        return migrationExecuted;
    }

    private void executeSqlScript(SQLiteDatabase db, String file) {

        InputStream stream = null;

        try {
            stream = Configuration.context.getAssets().open(MIGRATION_PATH + "/" + file);

            if (Configuration.SQL_PARSER_DELIMITED.equalsIgnoreCase(mSqlParser)) {
                executeDelimitedSqlScript(db, stream);

            } else {
                executeLegacySqlScript(db, stream);

            }

        } catch (IOException e) {
            Log.e("Failed to execute " + file, e);

        } finally {
            IOUtils.closeQuietly(stream);

        }
    }

    private void executeDelimitedSqlScript(SQLiteDatabase db, InputStream stream) throws IOException {

        List<String> commands = SqlParser.parse(stream);

        for(String command : commands) {
            db.execSQL(command);
        }
    }

    private void executeLegacySqlScript(SQLiteDatabase db, InputStream stream) throws IOException {

        InputStreamReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new InputStreamReader(stream);
            buffer = new BufferedReader(reader);
            String line = null;

            while ((line = buffer.readLine()) != null) {
                line = line.replace(";", "").trim();
                if (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                }
            }

        } finally {
            IOUtils.closeQuietly(buffer);
            IOUtils.closeQuietly(reader);

        }
    }

}
