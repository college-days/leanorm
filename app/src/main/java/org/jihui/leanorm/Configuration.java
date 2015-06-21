package org.jihui.leanorm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by zjh on 2015/5/23.
 */
public class Configuration {
    public static final String LEAN_DB_NAME = "LEAN_DB_NAME";
    public static final String LEAN_DB_VERSION = "LEAN_DB_VERSION";
    public final static String LEAN_MODELS = "LEAN_MODELS";
    public final static String LEAN_SQL_PARSER = "LEAN_SQL_PARSER";

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
            Log.d("LEAN_ORM", "Couldn't find meta-data: " + name);
        }

        return null;
    }
}
