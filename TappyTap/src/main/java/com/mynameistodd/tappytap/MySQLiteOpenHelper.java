package com.mynameistodd.tappytap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by todd on 9/27/13.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tappy_tap.db";
    private static final int DATABASE_VERSION = 2;
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

    public static final String SUBSCRIPTION_TABLE_NAME = "subscription";
    public static final String SUBSCRIPTION_COLUMN_NAME = "name";
    private static final String SUBSCRIPTION_TABLE_CREATE =
            "CREATE TABLE " + SUBSCRIPTION_TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                    SUBSCRIPTION_COLUMN_NAME + " TEXT);";

    MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(SUBSCRIPTION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2)
        {
            db.execSQL(SUBSCRIPTION_TABLE_CREATE);
        }
    }

    public static boolean insertSubscription(Context context, String name)
    {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put(SUBSCRIPTION_COLUMN_NAME, name);
        long rows = db.insert(SUBSCRIPTION_TABLE_NAME, null, ct);

        if (rows > 0) { return true; } else { return false; }
    }

    public static boolean deleteSubscription(Context context, int id)
    {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = BaseColumns._ID + " = ?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        int rows = db.delete(SUBSCRIPTION_TABLE_NAME, whereClause, whereArgs);

        if (rows > 0) { return true; } else { return false; }
    }

    public static List<Subscription> getAllSubscriptions(Context context) {
        List<Subscription> subscriptions = new ArrayList<Subscription>();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor dbCursor = db.query(SUBSCRIPTION_TABLE_NAME, null, null,null,null,null,null);
        while (dbCursor.moveToNext())
        {
            Subscription sub = new Subscription();
            sub.setId(dbCursor.getInt(dbCursor.getColumnIndex(BaseColumns._ID)));
            sub.setName(dbCursor.getString(dbCursor.getColumnIndex(SUBSCRIPTION_COLUMN_NAME)));
            subscriptions.add(sub);
        }
        return subscriptions;
    }
}
