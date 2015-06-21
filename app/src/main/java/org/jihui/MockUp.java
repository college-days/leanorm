package org.jihui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jihui.leanorm.LeanORMDao;
import org.jihui.leanorm.Tuple;
import org.jihui.models.DanceVideo;

import java.util.List;

/**
 * Created by zjh on 2015/6/1.
 */
public class MockUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*showVideos();
        deleteVideos();
        showVideos();*/
        //insertVideos();
        //showVideosByField();
        //addColumnToTable();
        insertVideos();
        showVideos();
    }

    public void addColumnToTable() {
        Log.d("LEAN_ORM", LeanORMDao.executeMigrations("add_star_column_to_dancevideo") + "");
    }

    public void insertVideos() {
        for (int i = 0; i < 10; i++) {
            DanceVideo video = new DanceVideo();
            video.title = "hehe" + i;
            video.id = 3;
            video.star = i;
            video.starname = "star" + i;
            video.save();
        }
    }

    public void showVideos() {
        List<DanceVideo> videos = LeanORMDao.findAll(DanceVideo.class);
        Log.d("LEAN_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("LEAN_ORM", v.toString());
        }
    }

    public void showVideosByField() {
        List<DanceVideo> videos = LeanORMDao.findByField(DanceVideo.class, new Tuple("title", "hehe3"));
        Log.d("LEAN_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("LEAN_ORM", v.toString());
        }
    }

    public void updateVideos() {
        List<DanceVideo> videos = LeanORMDao.findAll(DanceVideo.class);
        Log.d("LEAN_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("LEAN_ORM", v.title);
            v.title = "meme";
            v.priority = "memeda";
            v.dancemusic = "dancemusic";
            v.hot += 1;
            v.save();
        }
    }

    public void deleteVideos() {
        List<DanceVideo> videos = LeanORMDao.findAll(DanceVideo.class);
        Log.d("LEAN_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("LEAN_ORM", v.title);
            v.title = "meme";
            v.delete();
        }
    }

    public void singleVideo() {
        DanceVideo video = LeanORMDao.findById(DanceVideo.class, 3);
        Log.d("LEAN_ORM", video.toString());
        video.title = "cleantha";
        video.save();
        video = LeanORMDao.findById(DanceVideo.class, 3);
        Log.d("LEAN_ORM", video.toString());
    }
}
