package com.darfootech.dbdemo.darfooorm;

import android.content.Context;
import android.content.pm.ActivityInfo;
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

    public static Context context;

    @SuppressWarnings("unchecked")
    public static <T> T getMetaData(String name) {
        try {
            final ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            if (ai.metaData != null) {
                return (T) ai.metaData.get(name);
            }
        }
        catch (Exception e) {
            Log.d("DARFOO_ORM", "Couldn't find meta-data: " + name);
        }

        return null;
    }
}
