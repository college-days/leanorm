package com.darfootech.dbdemo.darfooorm;

import android.app.Application;

/**
 * Created by zjh on 2015/5/23.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.context = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
