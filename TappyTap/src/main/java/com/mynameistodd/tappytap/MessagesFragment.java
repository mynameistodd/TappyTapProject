package com.mynameistodd.tappytap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends ListFragment {

    public MessagesFragment() {

    }

    Context context;
    private ArrayAdapter<Message> adapter;
    private List<Message> messages;
    private SQLiteDatabase db;
    private MySQLiteOpenHelper helper;
    private Cursor dbCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        helper = new MySQLiteOpenHelper(context);
        db = helper.getReadableDatabase();
    }

    @Override
    public void onResume() {
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

        adapter = new MessagesAdapter(context, R.layout.list_messages, messages);
        setListAdapter(adapter);
        setEmptyText(context.getString(R.string.nothing_found));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_messages, menu);
    }

}
