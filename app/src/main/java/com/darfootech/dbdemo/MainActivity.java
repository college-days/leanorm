package com.darfootech.dbdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.darfootech.dbdemo.darfooorm.Configuration;
import com.darfootech.dbdemo.darfooorm.DarfooORMDao;
import com.darfootech.dbdemo.models.DanceVideo;

import java.util.List;


public class MainActivity extends Activity {
    DBOperator operator;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        operator = new DBOperator(this, "my.db", null, 3);
        insertAndUpdateData(operator);
        String result = queryData(operator);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20.0f);
        textView.setText("名字\t等级\t" + result);

        Log.d("jihui", (String) Configuration.getMetaData(Configuration.DARFOO_DB_NAME));
        Log.d("jihui", (Integer) Configuration.getMetaData(Configuration.DARFOO_DB_VERSION) + "");
        Log.d("jihui", (String) Configuration.getMetaData(Configuration.DARFOO_MODELS));

        DanceVideo video = new DanceVideo();
        video.title = "cleantha";
        video.id = 3;
        video.save();

        List<DanceVideo> videos = DarfooORMDao.findAll(DanceVideo.class);
        Log.d("DARFOO_ORM", videos.size() + "");
        for (DanceVideo v : videos) {
            Log.d("DARFOO_ORM", "hehe");
            Log.d("DARFOO_ORM", v.title);
            v.title = "meme";
            //v.save(operator);
            v.delete();
        }
    }

    public void insertAndUpdateData(DBOperator operator) {
        SQLiteDatabase db = operator.getWritableDatabase();
        db.execSQL("insert into hero_info(name, level) values ('bb', 0)");
        ContentValues values = new ContentValues();
        values.put("name", "xh");
        values.put("level", 5);
        values.put("desc", "just a description");
        values.put("message", "just a message");
        db.insert("hero_info", "id", values);
        values.clear();
        values.put("name", "xh");
        values.put("level", 10);
        values.put("desc", "yet another a description");
        values.put("message", "yet another a message");
        db.update("hero_info", values, "level = 5", null);
        db.close();
    }

    public String queryData(DBOperator operator) {
        String result = "";
        SQLiteDatabase db = operator.getReadableDatabase();
        Cursor cursor = db.query("hero_info", null, null, null, null, null, "id asc");
        int nameIndex = cursor.getColumnIndex("name");
        int levelIndex = cursor.getColumnIndex("level");
        int descIndex = cursor.getColumnIndex("desc");
        int messageIndex = cursor.getColumnIndex("message");
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            result = result + cursor.getString(nameIndex) + "\t\t";
            result = result + cursor.getString(descIndex) + "\t\t";
            result = result + cursor.getString(messageIndex) + "\t\t";
            result = result + cursor.getInt(levelIndex) + "\n";
        }
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
