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

public class SubscriptionsFragment extends ListFragment {

    public SubscriptionsFragment() {

    }

    Context context;
    private ArrayAdapter<Subscription> adapter;
    private List<Subscription> subscriptions;
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

        subscriptions = new ArrayList<Subscription>();

        dbCursor = db.query(MySQLiteOpenHelper.SUBSCRIPTION_TABLE_NAME, null, null,null,null,null,null);
        while (dbCursor.moveToNext())
        {
            Subscription sub = new Subscription();
            sub.setId(dbCursor.getInt(dbCursor.getColumnIndex(BaseColumns._ID)));
            sub.setName(dbCursor.getString(dbCursor.getColumnIndex(MySQLiteOpenHelper.SUBSCRIPTION_COLUMN_NAME)));
            subscriptions.add(sub);
        }

        adapter = new SubscriptionsAdapter(context, R.layout.list_subscription_item, subscriptions);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_subscriptions, menu);
    }

}
