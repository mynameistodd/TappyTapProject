package com.mynameistodd.tappytap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by todd on 9/27/13.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tappy_tap.db";
    private static final int DATABASE_VERSION = 1;
    public static final String MESSAGE_TABLE_NAME = "message";
    public static final String MESSAGE_COLUMN_SENDER = "sender";
    public static final String MESSAGE_COLUMN_MESSAGE_TEXT = "messageText";
    public static final String MESSAGE_COLUMN_RECEIVED = "received";
    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                    MESSAGE_COLUMN_SENDER + " TEXT, " +
                    MESSAGE_COLUMN_MESSAGE_TEXT + " TEXT, " +
                    MESSAGE_COLUMN_RECEIVED + " TEXT);";

    MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
