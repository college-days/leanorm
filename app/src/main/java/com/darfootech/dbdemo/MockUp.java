package com.darfootech.dbdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.darfootech.dbdemo.darfooorm.Configuration;
import com.darfootech.dbdemo.darfooorm.DarfooORMCota;
import com.darfootech.dbdemo.darfooorm.DarfooORMDao;
import com.darfootech.dbdemo.darfooorm.DarfooORMManager;
import com.darfootech.dbdemo.models.DanceVideo;

import java.util.List;

/**
 * Created by zjh on 2015/6/1.
 */
public class MockUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DARFOO_ORM", (String) Configuration.getMetaData(Configuration.DARFOO_DB_NAME));
        Log.d("DARFOO_ORM", (Integer) Configuration.getMetaData(Configuration.DARFOO_DB_VERSION) + "");
        Log.d("DARFOO_ORM", (String) Configuration.getMetaData(Configuration.DARFOO_MODELS));

        //new DarfooORMCota().dropTable(DarfooORMManager.helper.getWritableDatabase());

        /*DanceVideo video = new DanceVideo();
        video.title = "hehe";
        video.id = 3;
        video.save();*/

        List<DanceVideo> videos = DarfooORMDao.selectAll(DanceVideo.class);
        Log.d("DARFOO_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("DARFOO_ORM", v.title);
            v.title = "meme";
            //v.save();
            v.delete();
        }

        videos = DarfooORMDao.selectAll(DanceVideo.class);
        Log.d("DARFOO_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("DARFOO_ORM", v.toString());
        }

        /*DanceVideo video = DarfooORMDao.findById(DanceVideo.class, 3);
        Log.d("DARFOO_ORM", video.toString());
        video.title = "cleantha";
        video.save();
        video = DarfooORMDao.findById(DanceVideo.class, 3);
        Log.d("DARFOO_ORM", video.toString());*/
    }
}
