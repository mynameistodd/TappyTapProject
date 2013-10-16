package com.mynameistodd.tappytap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListMessages extends ListFragment {

    public ListMessages() {

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

        adapter = new MyArrayAdapter(context, R.layout.list_messages, messages);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_messages, menu);
    }

}
