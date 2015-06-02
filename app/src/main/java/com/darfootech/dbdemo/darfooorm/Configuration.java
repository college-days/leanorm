package com.darfootech.dbdemo.darfooorm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by zjh on 2015/5/23.
 */
public class Configuration {
    public static final String DARFOO_DB_NAME = "DARFOO_DB_NAME";
    public static final String DARFOO_DB_VERSION = "DARFOO_DB_VERSION";
    public final static String DARFOO_MODELS = "DARFOO_MODELS";
    public final static String DARFOO_SQL_PARSER = "DARFOO_SQL_PARSER";

    public static Context context;
    public final static String SQL_PARSER_LEGACY = "legacy";
    public final static String SQL_PARSER_DELIMITED = "delimited";

    public static final String DEFAULT_SQL_PARSER = SQL_PARSER_LEGACY;

    @SuppressWarnings("unchecked")
    public static <T> T getMetaData(String name) {
        try {
            final ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            if (ai.metaData != null) {
                return (T) ai.metaData.get(name);
            }
        } catch (Exception e) {
            Log.d("DARFOO_ORM", "Couldn't find meta-data: " + name);
        }

        return null;
    }
}
