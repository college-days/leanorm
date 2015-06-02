package com.darfootech.dbdemo.models;

import com.darfootech.dbdemo.darfooorm.DarfooORMModel;

/**
 * Created by zjh on 2015/5/23.
 */
public class DanceVideo extends DarfooORMModel {
    public int id;
    public String title;
    public String authorname;
    public String image_url;
    public String video_url;
    public String speedup_url;
    public String priority;
    public String dancegroup;
    public String dancemusic;
    public int type;
    public int update_timestamp;

    @Override
    public String toString() {
        return "DanceVideo{" +
                "_id=" + this._id +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", authorname='" + authorname + '\'' +
                ", image_url='" + image_url + '\'' +
                ", video_url='" + video_url + '\'' +
                ", speedup_url='" + speedup_url + '\'' +
                ", priority='" + priority + '\'' +
                ", dancegroup='" + dancegroup + '\'' +
                ", dancemusic='" + dancemusic + '\'' +
                ", type=" + type +
                ", update_timestamp=" + update_timestamp +
                '}';
    }
}
