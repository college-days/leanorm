package org.jihui.models;

import org.jihui.leanorm.LeanORMModel;

/**
 * Created by zjh on 2015/5/23.
 */
public class DanceMusic extends LeanORMModel {
    public int id;
    public String image_url;
    public String music_url;
    public String title;
    public String authorname;
    public String speedup_url;
    public int update_timestamp;
}
