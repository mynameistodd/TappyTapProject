package com.mynameistodd.tappytap;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.provider.BaseColumns;
import android.view.Menu;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListMessages extends ListActivity {

    private ArrayAdapter<Message> adapter;
    private List<Message> messages;
    private SQLiteDatabase db;
    private MySQLiteOpenHelper helper;
    private Cursor dbCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_messages);

        helper = new MySQLiteOpenHelper(this);
        db = helper.getReadableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        messages = new ArrayList<Message>();

        dbCursor = db.query(MySQLiteOpenHelper.MESSAGE_TABLE_NAME, null, null,null,null,null,null);
        //dbCursor.moveToFirst();
        while (dbCursor.moveToNext())
        {
            Message msg = new Message();
            msg.setId(dbCursor.getInt(dbCursor.getColumnIndex(BaseColumns._ID)));
            msg.setSender(dbCursor.getString(dbCursor.getColumnIndex(MySQLiteOpenHelper.MESSAGE_COLUMN_SENDER)));
            msg.setMessageText(dbCursor.getString(dbCursor.getColumnIndex(MySQLiteOpenHelper.MESSAGE_COLUMN_MESSAGE_TEXT)));
            msg.setReceived(dbCursor.getString(dbCursor.getColumnIndex(MySQLiteOpenHelper.MESSAGE_COLUMN_RECEIVED)));
            messages.add(msg);
        }

        adapter = new MyArrayAdapter(this, R.layout.list_messages, messages);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_messages, menu);
        return true;
    }
    
}
