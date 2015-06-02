package com.darfootech.dbdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by zjh on 2015/5/22.
 */
public class SqliteActivity extends Activity {
    SQLiteDatabase db;
    public String DB_NAME = "sql.db";
    public String DB_TABLE = "num";
    public int DB_VERSION = 1;
    final DBHelper helper = new DBHelper(this, DB_NAME, null, DB_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleantha);
        db = helper.getWritableDatabase();
        Button insert = (Button) findViewById(R.id.insert);
        Button delete = (Button) findViewById(R.id.delete);
        Button update = (Button) findViewById(R.id.update);
        Button query = (Button) findViewById(R.id.query);

        final ContentValues values = new ContentValues();
        updatelistview();
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_name = (EditText) findViewById(R.id.name);
                EditText et_phone = (EditText) findViewById(R.id.phone);
                values.put("name", et_name.getText().toString());
                values.put("phone", et_phone.getText().toString());

                long res = db.insert("addressbook", null, values);
                if (res == -1) {
                    Toast.makeText(SqliteActivity.this, "insert failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SqliteActivity.this, "insert success", Toast.LENGTH_SHORT).show();
                }
                updatelistview();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = db.delete("addressbook", "name='biggod'", null);
                if (res == 0) {
                    Toast.makeText(SqliteActivity.this, "delete failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SqliteActivity.this, "delete total " + res + " records", Toast.LENGTH_SHORT).show();
                }
                updatelistview();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.put("name", "biggod");
                values.put("phone", "18967097609");
                int res = db.update("addressbook", values, "name='hehe'", null);
                Toast.makeText(SqliteActivity.this, "update total " + res + " records", Toast.LENGTH_SHORT).show();

                updatelistview();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("addressbook", null, null, null, null, null, null);
                Toast.makeText(SqliteActivity.this, "there is total " + cursor.getCount() + " records", Toast.LENGTH_SHORT).show();
                updatelistview();
            }
        });
    }

    public void updatelistview() {
        ListView listView = (ListView) findViewById(R.id.lv);

        final Cursor cursor = db.query("addressbook", null, null, null, null, null, null);
        String[] ColumnNames = cursor.getColumnNames();

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout, cursor, ColumnNames, new int[]{R.id.tv1, R.id.tv2, R.id.tv3});
        listView.setAdapter(adapter);
    }
}
